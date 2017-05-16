package com.yansou.ci.common.datatables;

import com.yansou.ci.common.page.ColumnInfo;
import com.yansou.ci.common.page.PageCriteria;
import com.yansou.ci.common.page.PaginationUtils;
import com.yansou.ci.common.page.SearchInfo;
import com.yansou.ci.common.utils.RegexUtils;
import com.yansou.ci.common.web.RequestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-16 9:49
 */
public class DataTableUtils {

	/**
	 * 解析分页参数
	 *
	 * @param request
	 *
	 * @return
	 */
	public static PageCriteria getRequestInfo(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();

		Integer draw = RequestUtils.getIntParameter(parameterMap, "draw");
		Integer start = RequestUtils.getIntParameter(parameterMap, "start");
		Integer pageSize = RequestUtils.getIntParameter(parameterMap, "length");

		Integer currentPageNo = PaginationUtils.calCurrentPageNo(start, pageSize);

		ColumnInfo[] columnInfos = parseColumnInfo(parameterMap);

		return null;

	}

	private static List<String[]> parseItem(Map<String, String[]> parameterMap, String regex) {
		List<String[]> itemList = new ArrayList<>();

		parameterMap.forEach((name, values) -> {
			if (name.matches(regex)) {
				List<String[]> allValue = RegexUtils.getAllValue(name, regex, 1, 2);

				if (CollectionUtils.isNotEmpty(allValue)) {
					String[] item = new String[3];
					item[0] = allValue.get(0)[0].trim();
					item[1] = allValue.get(0)[1].trim();
					item[2] = values[0].trim();

					itemList.add(item);
				}

			}
		});

		return itemList;
	}

	/**
	 * 解析ColumnInfo，数据格式如下：
	 * <ul>
	 * <li>columns[0][data]:first_name</li>
	 * <li>columns[0][name]:</li>
	 * <li>columns[0][searchable]:true</li>
	 * <li>columns[0][orderable]:true</li>
	 * <li>columns[0][search][value]:</li>
	 * <li>columns[0][search][regex]:false</li>
	 * </ul>
	 *
	 * @param parameterMap
	 *
	 * @return
	 */
	private static ColumnInfo[] parseColumnInfo(Map<String, String[]> parameterMap) {
		if (MapUtils.isEmpty(parameterMap)) {
			return null;
		}

		String columnReg = "^columns\\[(.*?)\\](.*?)$";

		List<String[]> itemList = parseItem(parameterMap, columnReg);

		Map<Integer, ColumnInfo> columnInfoMap = new HashMap<>();

		itemList.forEach(columnItem -> parseColumnInfo(columnInfoMap, columnItem));

		int columns = columnInfoMap.keySet().stream().mapToInt(Integer::intValue).max().getAsInt() + 1;// 一共有多少列

		ColumnInfo[] columnInfos = new ColumnInfo[columns];
		columnInfoMap.forEach((index, columnInfo) -> columnInfos[index] = columnInfo);

		return columnInfos;
	}

	private static void parseColumnInfo(Map<Integer, ColumnInfo> columnInfoMap, String[] columnItem) {
		Integer index = Integer.parseInt(columnItem[0]);
		String tag = columnItem[1];
		String value = columnItem[2];

		if (index == null || index < 0) {
			return;
		}

		ColumnInfo columnInfo = columnInfoMap.get(index);
		if (columnInfo == null) {
			columnInfo = new ColumnInfo();

			columnInfoMap.put(index, columnInfo);
		}

		switch (tag) {
			case "[data]":
				columnInfo.setData(value);
				break;
			case "[name]":
				columnInfo.setName(value);
				break;
			case "[searchable]":
				columnInfo.setSearchable(Boolean.parseBoolean(value));
				break;
			case "[orderable]":
				columnInfo.setOrderable(Boolean.parseBoolean(value));
				break;
			case "[search][value]":
				SearchInfo searchInfo1 = columnInfo.getSearchInfo();
				if (searchInfo1 == null) {
					searchInfo1 = new SearchInfo();
					columnInfo.setSearchInfo(searchInfo1);
				}

				searchInfo1.setValue(value);
				break;
			case "[search][regex]":
				SearchInfo searchInfo2 = columnInfo.getSearchInfo();
				if (searchInfo2 == null) {
					searchInfo2 = new SearchInfo();
					columnInfo.setSearchInfo(searchInfo2);
				}

				searchInfo2.setRegex(Boolean.parseBoolean(value));
				break;
		}

	}

}
