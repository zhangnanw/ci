package wang.biaoshu.analyzer.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

class HtmlTable {
	final static String COLSPAN = "colspan";
	final static String ROWSPAN = "rowspan";

	static String[][] toArr2(Element tableElement) {
		List<List<Element>> td2List = readTd(readTr(tableElement));
		List<List<String>> resList = Lists.newArrayList();
		for (int a = 0; a < td2List.size(); a++) {
			resList.add(Lists.newArrayList());
		}
		Preconditions.checkArgument(td2List.size() == resList.size());
		for (int rowIdx = 0; rowIdx < td2List.size(); rowIdx++) {
			int colIdx = 0;
			for (Element element : td2List.get(rowIdx)) {
				String val = element.text();
				String colStr = StringUtils.trim(element.attr(COLSPAN));
				String rowStr = StringUtils.trim(element.attr(ROWSPAN));
				int col = 1;
				int row = 1;
				if (StringUtils.isNumeric(colStr))
					col = Integer.parseInt(colStr);
				if (StringUtils.isNumeric(rowStr))
					row = Integer.parseInt(rowStr);
				List<List<String>> list2 = Lists.newArrayList();
				list2.add(resList.get(rowIdx));
					if (row > 1) {
						for (int i = 1; i < row; i++) {
							if (resList.size() > rowIdx + i) {
								list2.add(resList.get(rowIdx + i));
							}
						}
					}
					for (int i = 0; i < col; i++) {
						int idx = colIdx + i;
						list2.forEach(list -> {
							insVal(idx, val, list);
						});
					}
				colIdx += col;
			}
		}
		String[][] arr2 = new String[resList.size()][];
		for (int i = 0; i < arr2.length; i++) {
			arr2[i] = resList.get(i).toArray(new String[resList.get(i).size()]);
		}
		return arr2;
	}

	/**
	 * 在某下标后添加元素。
	 * 
	 * @param idx
	 * @param val
	 * @param list
	 */
	static void addVal(int idx, String val, List<String> list) {
		for (int i = idx; i < list.size(); i++) {
			if (null == list.get(i)) {
				list.set(i, val);
				return;
			}
		}
		list.add(val);
	}

	/**
	 * 插入元素
	 * 
	 * @param idx
	 * @param val
	 * @param list
	 */
	static void insVal(int idx, String val, List<String> list) {
		while (idx > list.size()) {
			list.add(null);
		}
		addVal(idx, val, list);
	}

	static List<List<Element>> readTd(List<Element> trElementList) {
		List<List<Element>> resList = Lists.newArrayList();
		for (Element trElement : trElementList) {
			resList.add(trElement.children());
		}
		return resList;
	}

	static List<Element> readTr(Element tableElement) {
		List<Element> resElementList = Lists.newArrayList(tableElement);
		too: while (true) {
			List<Element> thisElementList = Lists.newArrayList();
			if (resElementList.isEmpty()) {
				break;
			}
			for (Element element : resElementList) {
				if ("tr".equals(element.tagName().toLowerCase())) {
					break too;
				}
				thisElementList.addAll(element.children());
			}
			resElementList = thisElementList;
		}
		return resElementList;
	}

	final static private List<String> filterTags = Arrays.asList("tr", "td", "th", "table");

	final static Element tagFilter(Element e) {
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
				if (filterTags.contains(element.tagName().toLowerCase())) {
					continue;
				}
				node = tagFilter(element);
			}
			returnValue.appendChild(node);
		}
		return returnValue;
	}
}
