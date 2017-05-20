package org.yansou.ci.data.mining.nlpir.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.trim;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.nlpcn.commons.lang.finger.pojo.MyFingerprint;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.MD5;
import org.nlpcn.commons.lang.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yansou.ci.data.mining.Analyzer;
import org.yansou.ci.data.mining.iio.OSSIIOAdapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 指纹MD5<br>
 * 此类极其重要，最后修改日期为2017年2月。如果发现日期不对，请再三核对后使用。 Fingerprint MD5
 *
 * @author zhang
 *
 */
public class Fmd5Analyzer implements Analyzer {

	private static final Logger LOG = LoggerFactory.getLogger(Fmd5Analyzer.class);
	private static Forest forest = null;
	// 是否强制更新fmd5
	private static boolean ForcedUpdateFmd5;

	static {
		try {
			OSSIIOAdapter iooAdpter = new OSSIIOAdapter("http://yansoudic.oss-cn-qingdao.aliyuncs.com/fmd5/");
			forest = Library.makeForest(iooAdpter.open("fmd5.dic"));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}

		ForcedUpdateFmd5 = Boolean.parseBoolean(System.getProperty("forced.update.fmd5"));
	}

	@Override
	public boolean match(JSONObject obj) {
		return true;
	}

	/**
	 * 根据一个 文章的正文.计算文章的指纹. 最后修改日期为2017年2月21日。
	 *
	 * @param content
	 * @return
	 */
	String fingerprint(String content) {

		content = StringUtil.rmHtmlTag(content);

		GetWord word = new GetWord(forest, content);

		String temp = null;

		int middleLength = content.length() / 2;

		double factory;

		HashMap<String, MyFingerprint> hm = new HashMap<String, MyFingerprint>();

		MyFingerprint myFingerprint = null;
		while ((temp = word.getFrontWords()) != null) {
			if (temp != null && temp.length() == 0) {
				continue;
			}
			temp = temp.toLowerCase();
			factory = 1 - (Math.abs(middleLength - word.offe) / (double) middleLength);
			if ((myFingerprint = hm.get(temp)) != null) {
				myFingerprint.updateScore(factory);
			} else {
				hm.put(temp, new MyFingerprint(temp, Double.parseDouble(word.getParam(1)), factory));
			}
		}

		Set<MyFingerprint> set = new TreeSet<MyFingerprint>();
		set.addAll(hm.values());

		int size = Math.min(set.size() / 10, 4) + 1;

		Iterator<MyFingerprint> iterator = set.iterator();
		int j = 0;
		Set<String> hs = new TreeSet<String>();
		for (; j < size && iterator.hasNext(); j++) {
			myFingerprint = iterator.next();
			hs.add(myFingerprint.getName() + " ");
		}
		String finger = MD5.code(hs.toString());

		return finger;
	}

	@Override
	public JSONObject analy(JSONObject obj) {
		JSONObject res = new JSONObject();
		String text = obj.getString("context");
		if (StringUtils.isBlank(text)) {
			text = "";
		}
		text = normalize(text);
		String md5 = fingerprint(text);
		if (StringUtils.isBlank(obj.getString("fmd5"))) {
			res.put("_updateBiddFmd5_", true);
		} else if (!StringUtils.equals(obj.getString("fmd5"), md5)) {
			if (ForcedUpdateFmd5) {
				res.put("_updateBiddFmd5_", true);
			} else {
				LOG.info("两表MD5值不符:bidd:{},bidd_marge:{}", obj.getString("fmd5"), md5);
			}
		}
		res.put("fmd5", md5);
		return res;
	}

	/**
	 * html去标签，规范化基本规则。历史遗留方法。因为需要保证稳定，不再进行任何修改。
	 *
	 * @param text
	 * @return
	 */
	private static String baseNormalize(String text) {
		// 处理script标签及里面的内容
		text = scriptEdit(text);
		// nbsp代表一个不换行的空格，编码还原后，会显示成一个问号。先将nbsp全部替换成普通空格。
		text = text.replace("&nbsp;", " ");
		text = text.replace((char) 12288, ' ');
		text = text.replace("\n", "");
		// 将实体编码全部还原。 &amp; &lt;之类的东西，还原成字符。
		text = StringEscapeUtils.unescapeHtml4(text);

		// XXX 从这开始，对影响格式的标签进行处理。
		// 去除所有注释
		text = text.replaceAll("(?s)<!--.*?-->", "");
		// 去除所有style标签及里面的内容
		text = text.replaceAll("(?is)<style[^>]*>.*?</style>", "");
		// 将br标签替换成换行
		text = text.replaceAll("(?i)</?\\s*br\\s*/?\\s*>", "\n");
		// XXX 段落标签前后加入空格。
		// 后来又查了一遍文档，span不会在页面上生成空格，暂时去掉。保留p标签的规则。
		text = text.replaceAll("(?is)(?=<p)", "\n");
		text = text.replaceAll("(?is)(?<=p>)", "\n");
		// 标签已经处理完毕，这个过程的最后去除所有标签。
		text = text.replaceAll("<[^>]*>", "");

		// 这行规则是为了去除一个词汇中间的空格。
		// 正则为 单独存在的1-2个汉字之间的1-2个空格。
		// TODO 上方注释的规则，写在这个位置。还没想好怎么写呢。
		// 多个空格以及制表符换成一个空格
		text = text.replaceAll("[\t ]+", " ");
		// 去除所有空行,并去除行首尾的空字符
		try {
			text = IOUtils.readLines(new StringReader(text)).stream().filter(line -> isNotBlank(line))
					.map(line -> trim(line)).reduce((a, b) -> a + '\n' + b).orElse("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 中文全角冒号、分号换成英文冒号、分号
		text = text.replace('：', ':');
		text = text.replace('；', ';');
		// 大写逗号替换成小写逗号
		text = text.replace('，', ',');
		// 去除标点左右的空格和制表符
		text = text.replaceAll("(?<=\\pP)[ \t]+", "");
		text = text.replaceAll("[ \t]+(?=\\pP)", "");

		return text;
	}

	/**
	 * 扩展规范化规则。
	 *
	 * @param text
	 * @return
	 */
	private static String extendNormalize(String text) {
		text = text.replaceAll("联\\s*系\\s*人", "联系人");
		text = text.replaceAll("电\\s+话", "电话");
		return text;
	}

	private static final Pattern script_p = Pattern.compile("(?is)<script[^>]*>.*?<\\s*/\\s*script\\s*>");

	private static String scriptEdit(String text) {
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		Matcher match = script_p.matcher(text);
		int last = 0;
		while (match.find()) {
			out.append(substring(text, last, match.start()));
			String scr = match.group();
			try {
				IOUtils.readLines(new StringReader(scr)).stream().filter(line -> !startsWith(trim(line), "//"))
						.forEach(out::println);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
			last = match.end();
		}
		out.append(substring(text, last));
		return sw.toString();
	}

	/**
	 * 文本规范化。
	 *
	 * @param text
	 * @return
	 */
	private static String normalize(String text) {
		text = baseNormalize(text);
		text = extendNormalize(text);
		return text;
	}

}
