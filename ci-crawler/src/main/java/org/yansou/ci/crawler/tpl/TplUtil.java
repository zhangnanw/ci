package org.yansou.ci.crawler.tpl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.BaseStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yansou.ci.crawler.utils.URLUtils;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 模版需要的工具方法，都定义在这个对象中
 * 
 * @author zhangnan1
 *
 */
public class TplUtil {
	private TplUtil() {
	}

	private static TplUtil tplUtil = new TplUtil();

	public static TplUtil getTplUtil() {
		return tplUtil;
	}

	public String list2wan(Object... args) {
		if (null == args) {
			return "";
		}
		List<Object> list;
		if (args.length == 1 && null != args[0] && args[0] instanceof Collection) {
			list = new ArrayList<Object>((Collection<?>) args[0]);
		} else {
			list = Arrays.asList(args);
		}
		StringBuffer sb = new StringBuffer();
		list.forEach(line -> sb.append(line).append('卐'));
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public String str2wanByDouhao(String arr) {
		if (StringUtils.isBlank(arr)) {
			return "";
		}
		return arr.replaceAll("[,]", "卐");
	}

	public String[] split(String str, String c) {
		return StringUtils.split(str, c);
	}

	/**
	 * 获取域名
	 * 
	 * @param obj
	 * @return
	 */
	public String getHost(Object obj) {
		String url = null;
		if (obj instanceof Page) {
			url = ((Page) obj).getUrl().get();
		}
		url = obj.toString();
		return URLUtils.getHost(url);
	}

	/**
	 * 
	 * 描述信息：判断某内容块是否包含某字符串
	 * 
	 * @data: 2015年5月5日
	 */
	public boolean isContainStr(String content, String... str) {
		boolean is = false;
		if (StringUtils.isBlank(content)) {
			return is;
		}
		for (String s : str) {
			if (content.contains(s)) {
				is = true;
				break;
			}
		}
		return is;
	}

	/**
	 * 
	 * 描述信息：判断某内容块是否包含某字符串,包含返回content，不包含返回空
	 * 
	 * @data: 2015年5月5日
	 */
	public String isContainStrAndReturn(String content, String... str) {
		String result = "";
		if (StringUtils.isBlank(content)) {
			return result;
		}
		for (String s : str) {
			if (content.contains(s)) {
				result = content;
				break;
			}
		}
		return result;
	}

	/**
	 * 
	 * 描述信息：多xpath返回结果
	 * 
	 * @data: 2015年5月5日
	 */
	public Selectable xpathList(Selectable selectable, String... xpath) {
		Selectable sel = null;
		for (String s : xpath) {
			sel = selectable.xpath(s);
			if (sel.match()) {
				return sel;
			}
		}
		return sel;
	}

	public Selectable getText(Page page) {
		return new PlainText(page.getRawText());
	}

	public Request toPostRequest(String url, String postString) {
		Request req = new Request();
		req.setUrl(url);
		req.setMethod("POST");
		req.putExtra("nameValuePair", string2post(postString));
		return req;
	}

	private NameValuePair[] string2post(String postString) {
		List<NameValuePair> list = Lists.newArrayList();
		Arrays.asList(StringUtils.split(postString, '&')).forEach(pair -> {
			if (StringUtils.isBlank(pair))
				return;
			int index = StringUtils.indexOf(pair, '=');
			if (index > 0) {
				String name = StringUtils.substring(pair, 0, index);
				String value = StringUtils.substring(pair, index + 1, pair.length());
				list.add(new BasicNameValuePair(name, value));
			}
		});
		return list.toArray(new NameValuePair[list.size()]);
	}

	public String numberAddflag(String string) {
		StringBuffer sb = new StringBuffer();
		java.util.regex.Pattern p = java.util.regex.Pattern.compile("[0-9]");
		java.util.regex.Matcher m = p.matcher(string);
		int lastEnd = 0;
		while (m.find()) {
			sb.append(string.substring(lastEnd, m.start()));
			sb.append('(');
			sb.append(m.group());
			sb.append(')');
			lastEnd = m.end();
		}
		sb.append(string.substring(lastEnd, string.length()));
		java.lang.System.out.println(sb);
		return sb.toString().replaceAll("[*]", "");
	}


	SimpleDateFormat OUTSDF = new SimpleDateFormat("yyyy-MM-dd");

	public String toDateStr(String pattern, Object value) {
		if (null == value) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			Date date = sdf.parse(value.toString());
			return OUTSDF.format(date);
		} catch (ParseException e) {
			Logger.getAnonymousLogger().info(e.getMessage());
		}
		return value.toString();

	}

	public String getTitle(Object obj) {
		if (null == obj) {
			return null;
		}
		String htmls = null;
		if (obj instanceof Html) {
			htmls = ((Html) obj).get();
		} else if (obj instanceof Page) {
			htmls = ((Page) obj).getHtml().get();
		} else {
			htmls = obj.toString();
		}
//		TitleExtractorImpl title = new TitleExtractorImpl();
//		title.setHtmls(htmls);
//		title.setExtension("html");
//		return title.getTitle();
		return null;
	}

	public Request toRequest(String url, String anchorText) {
		if (StringUtils.startsWith(url, "http")) {
			Request request = new Request(url);
			request.putExtra("anchorText", anchorText);
			return request;
		}
		return null;
	}

	public Request toRequest(Object obj) {
		if (obj instanceof Element) {
			Element node = (Element) obj;
			Request req = new Request();
			String url = node.attr("href");
			if (!StringUtils.startsWith(url, "http") && StringUtils.isNotBlank(node.baseUri())) {
				url = URLUtils.resolveString(node.baseUri(), url);
			}
			req.setUrl(url);
			req.putExtra("anchorText", node.text());
			return req;
		} else if (obj instanceof Elements) {
			Elements node = (Elements) obj;
			Request req = new Request();
			req.setUrl(node.attr("href"));
			req.putExtra("anchorText", node.text());
			return req;
		} else {
			return null;
		}
	}

	public List<String> asList(Object arg) {
		Objects.requireNonNull(arg, "toList(arg) arg is null.");
		List<String> list = new ArrayList<String>();
		if (arg.getClass().isArray()) {
			int length = Array.getLength(arg);
			for (int i = 0; i < length; i++) {
				Object e = Array.get(arg, i);
				if (null != e) {
					list.add(e.toString());
				} else {
					list.add(null);
				}
			}
		} else {
			Iterator<?> iterator;
			if (arg instanceof Iterator) {
				iterator = (Iterator<?>) arg;
			} else if (arg instanceof Iterable) {
				iterator = ((Iterable<?>) arg).iterator();
			} else if (arg instanceof BaseStream) {
				iterator = ((BaseStream<?, ?>) arg).iterator();
			} else {
				throw new IllegalArgumentException("toList(arg) 不知道怎么迭代这个类型 : " + arg.getClass());
			}
			iterator.forEachRemaining((e) -> {
				if (null != e) {
					list.add(e.toString());
				} else {
					list.add(null);
				}

			});
		}
		return list;
	}

	public HtmlPage getHtmlUnitPage(String url) {
		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		try {
			return client.getPage(url);
		} catch (FailingHttpStatusCodeException | IOException e) {
			throw new IllegalStateException(e);
		} finally {
			client.close();
		}
	}

	private static Map<String, ViaEnt0> viaMap = new HashMap<>();
	private long viaCheckTime = 0;

	private void _checkViaMap() {
		if (viaCheckTime > System.currentTimeMillis()) {
			return;
		}
		String[] keys = viaMap.entrySet().stream().filter(ent -> null == ent.getValue() || ent.getValue().isLose())
				.map(ent -> ent.getKey()).toArray(x -> new String[x]);
		for (int i = 0; i < keys.length; i++) {
			viaMap.remove(keys[i]);
		}
		viaCheckTime = System.currentTimeMillis() + 60000;
	}

	public void via(long time) {
		via(null, time);
	}

	/**
	 * 按key睡眠
	 * 
	 * @param key
	 * @param time
	 */
	public void via(String key, long time) {
		ViaEnt0 ent = viaMap.get(key);
		if (null == ent) {
			ent = new ViaEnt0();
			viaMap.put(key, ent);
		}
		ent.via(time);
		_checkViaMap();
	}

	private static class ViaEnt0 {
		private AtomicLong times = new AtomicLong();
		Object lock = new Object();

		public void via(long time) {
			// time必须小于1分钟
			Preconditions.checkArgument(time < 60000);
			for (;;) {
				synchronized (lock) {
					if (times.get() > System.currentTimeMillis()) {
						try {
							lock.wait(10);
						} catch (InterruptedException e) {
							throw new IllegalStateException(e);
						}
					} else {
						times.set(System.currentTimeMillis() + time);
						break;
					}
				}
			}
		}

		/**
		 * 是否失效
		 * 
		 * @return
		 */
		public boolean isLose() {
			return times.get() + 60000 < System.currentTimeMillis();
		}
	}
}
