﻿package wang.biaoshu.analyzer.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.IntStream.Builder;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 表格扫描。 <BR>
 * 将表格内容变为键值对文本
 * 
 * @author zhang
 *
 */
public class TableScan {
	final static double DEFAULT_PROP = 0.65;
	final static String COLSPAN = "colspan";
	final static String ROWSPAN = "rowspan";

	// final static String[] valueTypeWordRegex = {};

	public static String filter(String html) {
		html = StringUtils.replace(html, "&nbsp;", " ");
		Document document = Jsoup.parse(html);
		Elements tabs = document.getElementsByTag("table");
		StringWriter sw = new StringWriter();
		try (PrintWriter out = new PrintWriter(sw)) {
			for (Element tab : tabs) {
				String[][] arr2 = HtmlTable.toArr2(tab);
				arr2format(arr2);
				// 默认扫描x轴
				boolean defaultScanX = true;
				if (arr2.length > 0 && arr2.length > arr2[0].length) {
					defaultScanX = false;
				}

				boolean scanX = defaultScanX;
				for (;;) {
					int[] z = scan(arr2, scanX);
					if (z.length > 0) {
						String txt = eval(arr2, z, scanX);
						out.println(txt);
					}
					if (scanX != defaultScanX) {
						break;
					}
					scanX = !defaultScanX;
				}
			}
		}
		return sw.toString();
	}

	/**
	 * 数组里的字符串规范化。
	 * 
	 * @param arr2
	 */
	private static void arr2format(String[][] arr2) {
		Function<String, String> fun = (txt) -> {
			if (StringUtils.isNotBlank(txt)) {
				txt = txt.replace((char) 63, ' ');
				txt = txt.replace((char) 160, ' ');
				txt = txt.replaceAll("[\\s* ]", "");
			}
			return txt;
		};
		for (int x = 0; x < arr2.length; x++) {
			String[] arr = arr2[x];
			for (int y = 0; y < arr.length; y++) {
				arr[y] = fun.apply(arr[y]);
			}
		}
	}

	static class ZB {
		int x;
		int y;
		int row;
		int col;
	}

	final static Element tagFilter(Element e, java.util.function.Predicate<Element> filter) {
		Element returnValue = e.clone();
		List<Node> es = returnValue.childNodesCopy();
		returnValue = new Element(e.tag(), e.baseUri());
		if (e.tag().getName().toLowerCase().matches("^t[dh]$")) {
			returnValue.attr(COLSPAN, e.attr(COLSPAN));
			returnValue.attr(ROWSPAN, e.attr(ROWSPAN));
		}
		for (Node node : es) {
			if (node instanceof Element) {
				Element element = (Element) node;
				if (!filter.test(element)) {
					continue;
				}
				node = tagFilter(element, filter);
			}
			returnValue.appendChild(node);
		}

		return returnValue;
	}

	/**
	 * 打印二维数组
	 * 
	 * @param arr2
	 *            要打印的数组
	 * @param keyIdxArr
	 *            键列的下标
	 * @param isX
	 *            按照X轴打印
	 * @return
	 */
	static final String eval(String[][] arr2, int[] keyIdxArr, boolean isX) {
		if (!isX) {
			arr2 = rcUpside(arr2);
		}
		List<String[][]> arr2List = Lists.newArrayList();
		// 如果表格有多键值结构则继续处理
		if (keyIdxArr.length > 0) {
			int rowLen = arr2.length;
			int colLen = arr2[0].length;
			int keyLen = keyIdxArr.length;
			// int diff = 0;
			for (int i = 0; i < keyLen; i++) {
				int keyIdx = keyIdxArr[i];
				int end;
				if (i + 1 >= keyLen) {
					end = rowLen;
				} else {
					end = keyIdxArr[i + 1];
				}
				String[][] nArr = new String[end - keyIdx][];
				for (int row = 0; row < nArr.length; row++) {
					nArr[row] = new String[colLen];
					for (int col = 0; col < colLen; col++) {
						String val = idx(arr2, row + keyIdx, col);
						set(nArr, val, row, col);
					}
				}
				arr2List.add(nArr);
			}
		} else {
			arr2List.add(arr2);
		}
		StringWriter sw = new StringWriter();
		try (PrintWriter out = new PrintWriter(sw)) {
			for (String[][] a2 : arr2List) {
				out.println(parseKeyValue(a2));
			}
		}
		return sw.toString();
	}

	/**
	 * 将表格变为kv结构
	 * 
	 * @param arr2
	 * @return
	 */
	private final static String parseKeyValue(String[][] arr2) {
		if (arr2.length < 2) {
			return StringUtils.EMPTY;
		}
		StringWriter sw = new StringWriter();
		try (PrintWriter out = new PrintWriter(sw)) {
			int xLen = arr2.length;
			int yLen = arr2[0].length;
			for (int x = 1; x < xLen; x++) {
				for (int y = 0; y < yLen; y++) {
					String key = idx(arr2, 0, y);
					String val = idx(arr2, x, y);
					out.println(key + ":" + val);
				}
			}
		}
		return sw.toString();
	}

	/**
	 * 颠倒行列
	 * 
	 * @param arr2
	 * @return
	 */
	final static String[][] rcUpside(String[][] arr2) {
		if (arr2.length == 0) {
			return arr2;
		}
		int rowLen = arr2.length;
		int colLen = arr2[0].length;
		String[][] resArr2 = new String[colLen][rowLen];
		for (int x = 0; x < rowLen; x++) {
			for (int y = 0; y < colLen; y++) {
				String val = idx(arr2, x, y);
				set(resArr2, val, y, x);
			}
		}
		return resArr2;
	}

	final static String printXY(String[][] arr2) {
		StringWriter sw = new StringWriter();
		try (PrintWriter out = new PrintWriter(sw)) {
			for (int i = 0; i < arr2.length; i++) {
				out.println();
				String[] arr = arr2[i];
				for (int y = 0; y < arr.length; y++) {
					out.print(y);
					out.print('\t');
				}

			}
			out.println();
		}

		return sw.toString();
	}

	final static String printXYValue(String[][] arr2) {
		StringWriter sw = new StringWriter();
		try (PrintWriter out = new PrintWriter(sw)) {
			for (int i = 0; i < arr2.length; i++) {
				out.println();
				String[] arr = arr2[i];
				for (int y = 0; y < arr.length; y++) {
					out.print(arr[y]);
					out.print('\t');
				}

			}
			out.println();
		}

		return sw.toString();
	}

	/**
	 * 扫描二维数组
	 * 
	 * @param arr2
	 *            被扫描的二维数组
	 * @param scanRow
	 *            true 按X轴扫描，false按Y轴扫描。
	 * @return
	 */
	static int[] scan(String[][] arr2, boolean scanRow) {
		if (!scanRow) {
			arr2 = rcUpside(arr2);
		}
		if (arr2.length == 0) {
			return new int[0];
		}
		int rowLen = arr2.length;
		int colLen = arr2[0].length;
		List<Integer> list = Lists.newArrayList();

		for (int row = 0; row < rowLen; row++) {
			Set<String> sets = Sets.newHashSet();
			List<Boolean> boolList = Lists.newArrayList();
			for (int col = 0; col < colLen; col++) {
				String word = idx(arr2, row, col);
				if (!sets.contains(word)) {
					boolList.add(checkWordInRegexArr(TableKeys.keyTypeWordRegex, word));
					sets.add(word);
				}
			}
			// 如果整个列只有同一个词，不管是否匹配，都抛弃。
			if (colLen > 1 && boolList.size() == 1) {
				continue;
			}
			double proportion = (double) boolList.stream().filter(a -> a).count() / (double) boolList.size();
			if (proportion > DEFAULT_PROP) {
				list.add(row);
			}
		}
		return list.stream().mapToInt(i -> i).toArray();
	}

	/**
	 * 检查词汇是否满足正则。
	 * 
	 * @param regexArr
	 * @param word
	 * @return
	 */
	final static boolean checkWordInRegexArr(Collection<String> regexArr, String word) {
		for (String regex : regexArr) {
			if (StringUtils.length(word) < 18 && StringUtils.isNotBlank(RegexUtils.regex(regex, word, 0))) {
				return true;
			}
		}
		return false;
	}

	final static int rowLen(String[][] arr2) {
		return arr2.length;
	}

	final static int colLen(String[][] arr2) {
		Builder builder = IntStream.builder();
		for (String[] arr : arr2) {
			builder.add(arr.length);
		}
		return builder.build().max().getAsInt();
	}

	@Deprecated
	final static String[][] checkArr22(String[][] arr2) {
		int len = -1;
		for (String[] arr : arr2) {
			if (len != -1) {
				if (len != arr.length) {
					throw new IllegalArgumentException("这不是一个矩阵数组");
				}
			}
			len = arr.length;
		}
		return arr2;
	}

	/**
	 * 获得指定坐标的值，但不会报下标越界异常。 <br>
	 * 越界的时候，返回空字符串。
	 * 
	 * @param arr2
	 * @param row
	 * @param col
	 * @return
	 */
	final static String idx(String[][] arr2, int row, int col) {
		if (arr2.length > row) {
			String[] arr = arr2[row];
			if (arr.length > col) {
				return arr[col];
			}
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 设置指定坐标的值，但不会报出异常。
	 * 
	 * @param arr2
	 * @param value
	 * @param row
	 * @param col
	 * @return
	 */
	final static boolean set(String[][] arr2, String value, int row, int col) {
		if (arr2.length > row) {
			String[] arr = arr2[row];
			if (arr.length > col) {
				arr[col] = value;
				return true;
			}
		}
		return false;
	}
}
