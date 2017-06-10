package org.yansou.ci.crawler.tpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.yansou.ci.crawler.utils.URLUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.utils.UrlUtils;

public final class URLParser {

	public static final Request toRequest(String requestString) {
		return toRequest(requestString, null);
	}

	@SuppressWarnings("unchecked")
	public static final Request toRequest(String requestString, Page page) {
		if (StringUtils.isBlank(requestString) || requestString.equals("#")
				|| requestString.startsWith("javascript:")) {
			return null;
		}
		Request request = null;
		if (isCurl(requestString)) {
			request = new CurlParser(requestString).getRequest();
		} else {
			request = new Request();
			if (null != page) {
				requestString = UrlUtils.canonicalizeUrl(requestString, page.getUrl().toString());
			}
			request.setUrl(requestString);
		}
		Map<String, String> headers;
		headers = (Map<String, String>) request.getExtra("headers");
		if (null == headers) {
			headers = Maps.newLinkedHashMap();
		}
		if (!headers.containsKey("Accept")) {
			headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		}
		if (!headers.containsKey("Accept-Encoding")) {
			headers.put("Accept-Encoding", "deflate");
		}
		if (!headers.containsKey("Accept-Language")) {
			headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		}
		if (!headers.containsKey("Connection")) {
			headers.put("Connection", "Keep-Alive");
		}
		if (!headers.containsKey("Referer") && null != page) {
			headers.put("Referer", page.getUrl().toString());
		}
		if (!headers.containsKey("Host")) {
			headers.put("Host", URLUtils.getHost(request.getUrl()));
		}
		if (!headers.containsKey("User-Agent")) {
			headers.put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
		}

		return request;
	}

	private static boolean isCurl(String requestString) {
		return StringUtils.startsWithIgnoreCase(requestString, "curl ");
	}

	public static void addRequests(Iterable<?> requests, Page page) {
		requests.forEach((arg) -> {
			if (arg instanceof Request) {
				page.addTargetRequest((Request) arg);
			} else {
				addRequest(arg.toString(), page);
			}
		});
	}

	public static void addRequest(String requestString, Page page) {
		Request request = toRequest(requestString, page);
		if (null != request) {
			page.addTargetRequest(request);
		}
	}

	static String[] cmdToArgs(String command) {
		ArrayList<String> list = Lists.newArrayList();
		StringBuilder sb = new StringBuilder();
		Runnable write = () -> {
			if (sb.length() > 0) {
				list.add(sb.toString());
				sb.setLength(0);
			}
		};
		int length = command.length();
		// 判定是否在引号内。为1表示在单引号内，为2表示在双引号内。
		int status = 0;
		for (int i = 0; i < length; i++) {
			char c = command.charAt(i);
			switch (status) {
			case 0:
				if (' ' == c || '\t' == c || '\n' == c || '\r' == c) {
					write.run();
					continue;
				} else if ('\'' == c) {
					status = 1;
				} else if ('"' == c) {
					status = 2;
				}
				break;
			case 1:
				if ('\'' == c) {
					status = 0;
				}
				break;
			case 2:
				if ('"' == c) {
					status = 0;
				}
				break;
			default:
				break;
			}
			sb.append(c);
		}
		write.run();
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 是否单引号
	 * 
	 * @param str
	 * @return
	 */
	static boolean isDyh(String str) {
		return (StringUtils.startsWith(str, "'") && StringUtils.endsWith(str, "'"));

	}

	/**
	 * 是否双引号
	 * 
	 * @param str
	 * @return
	 */
	static boolean isSyh(String str) {
		return (StringUtils.startsWith(str, "\"") && StringUtils.endsWith(str, "\""));
	}

	static boolean isYh(String str) {
		return isSyh(str) || isDyh(str);
	}

	private static String removeYh(String str) {
		if (isYh(str)) {
			return str.substring(1, str.length() - 1);
		}
		return str;
	}

	static class CurlParser {
		private HashMap<String, Consumer<String>> lines = Maps.newHashMap();
		{
			lines.put("-d", this::argData);
			lines.put("--data", this::argData);
			lines.put("-x", this::argProxy);
			lines.put("--proxy", this::argProxy);
			lines.put("--charset", this::argCharset);
			lines.put("--anchorText", this::argAnchor);
		}

		private Request request;
		private Consumer<String> argReader;
		private Set<Consumer<String>> readerSet = Sets.newHashSet();

		public CurlParser(Request request, String command) {
			this.request = request;
			run(command);
			argReader = null;
		}

		public CurlParser(String command) {
			this(new Request(), command);
		}

		private void run(String command) {
			Arrays.asList(cmdToArgs(command)).forEach(this::readArg);
		}

		private int argNum = 0;

		private void readArg(String arg) {
			try {
				if (argNum == 0 && StringUtils.equals("curl", arg)) {
					return;
				}
				if (null != argReader) {
					argReader.accept(arg);
					argReader = null;
					return;
				}
				if (StringUtils.startsWith(arg, "-")) {
					argReader = lines.get(arg);
					if (null != argReader) {
						if (readerSet.contains(argReader)) {
							throw new IllegalArgumentException("参数 " + arg + " 重复");
						}
						readerSet.add(argReader);
						return;
					} else {
						throw new IllegalArgumentException("不支持参数" + arg);
					}
				}
				if (StringUtils.isBlank(request.getUrl())) {
					request.setUrl(arg);
				} else {
					throw new IllegalArgumentException("已有URL." + arg);
				}
				return;
			} finally {
				argNum++;
			}
		}

		void argCharset(String str) {

		}

		void argProxy(String proxyString) {
			String[] tmp = proxyString.split(":");
			request.putExtra("_PROXY_", new HttpHost(tmp[0], Integer.parseInt(tmp[1])));
		}

		void argCookie(String cookie) {

		}

		void argData(String postString) {
			postString = removeYh(postString);
			List<NameValuePair> list = Lists.newArrayList();
			Arrays.asList(StringUtils.split(postString, '&')).forEach(q -> {
				int index = q.indexOf('=');
				String name = null, value = null;
				if (index > 0) {
					name = q.substring(0, index);
					value = q.substring(index + 1);
				} else {
					name = q;
				}
				list.add(new BasicNameValuePair(name, value));
			});
			request.putExtra("nameValuePair", list.toArray(new NameValuePair[list.size()]));
			request.setMethod("POST");
		}

		void argAnchor(String anchorText) {
			request.putExtra("anchorText", anchorText);
		}

		public Request getRequest() {
			return request;
		}
	}
}
