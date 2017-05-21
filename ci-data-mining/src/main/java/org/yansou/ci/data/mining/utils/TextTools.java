package org.yansou.ci.data.mining.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.startsWith;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.trim;

public class TextTools {
	/**
	 * html去标签，规范化基本规则。
	 * 
	 * @param text
	 * @return
	 */
	static String baseNormalize(String text) {
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
	static String extendNormalize(String text) {
		text = text.replaceAll("联\\s*系\\s*人", "联系人");
		text = text.replaceAll("电\\s+话", "电话");
		return text;
	}

	static final Pattern script_p = Pattern.compile("(?is)<script[^>]*>.*?<\\s*/\\s*script\\s*>");

	static String scriptEdit(String text) {
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
	static public String normalize(String text) {
		text = baseNormalize(text);
		text = extendNormalize(text);
		return text;
	}
}
