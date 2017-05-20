package org.yansou.ci.common.datatables;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.yansou.ci.common.page.ColumnInfo;
import org.yansou.ci.common.page.OrderInfo;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.common.page.PaginationUtils;
import org.yansou.ci.common.page.SearchInfo;
import org.yansou.ci.common.utils.RegexUtils;
import org.yansou.ci.common.web.RequestUtils;

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
	public static PageCriteria parseRequest(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();

		Integer draw = RequestUtils.getIntParameter(parameterMap, "draw");
		Integer start = RequestUtils.getIntParameter(parameterMap, "start");
		Integer pageSize = RequestUtils.getIntParameter(parameterMap, "length");

		Integer currentPageNo = PaginationUtils.calCurrentPageNo(start, pageSize);

		ColumnInfo[] columnInfos = parseColumnInfo(parameterMap);
		OrderInfo[] orderInfos = parseOrderInfo(parameterMap);
		SearchInfo searchInfo = parseSearchInfo(parameterMap);

		PageCriteria pageCriteria = new PageCriteria();
		pageCriteria.setDraw(draw);
		pageCriteria.setStart(start);
		pageCriteria.setPageSize(pageSize);
		pageCriteria.setCurrentPageNo(currentPageNo);
		pageCriteria.setColumnInfos(columnInfos);
		pageCriteria.setOrderInfos(orderInfos);
		pageCriteria.setSearchInfo(searchInfo);

		return pageCriteria;
	}

	/**
	 * 解析返回值
	 *
	 * @param pagination
	 * @param draw
	 * @param error
	 * @param <T>
	 *
	 * @return
	 */
	public static <T> DataTableVo<T> parseResponse(Pagination<T> pagination, Integer draw, String error) {
		if (pagination == null) {
			return null;
		}

		DataTableVo<T> dataTableVo = new DataTableVo<T>();
		dataTableVo.setDraw(draw);
		dataTableVo.setRecordsTotal(pagination.getTotalCount());
		dataTableVo.setRecordsFiltered(pagination.getTotalCount());
		dataTableVo.setData(pagination.getDataArray());
		dataTableVo.setError(error);

		return dataTableVo;
	}

	/**
	 * 解析SearchInfo，数据格式如下：
	 * <ul>
	 * <li>search[value]:</li>
	 * <li>search[regex]:false</li>
	 * </ul>
	 *
	 * @param parameterMap
	 *
	 * @return
	 */
	private static SearchInfo parseSearchInfo(Map<String, String[]> parameterMap) {
		String value = RequestUtils.getStringParameter(parameterMap, "search[value]");
		Boolean regex = RequestUtils.getBooleanParameter(parameterMap, "search[regex]");

		SearchInfo searchInfo = new SearchInfo();
		searchInfo.setValue(value);
		searchInfo.setRegex(regex);

		return searchInfo;
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
	 * 解析OrderInfo，数据格式如下：
	 * <ul>
	 * <li>order[0][column]:0</li>
	 * <li>order[0][dir]:asc</li>
	 * </ul>
	 *
	 * @param parameterMap
	 *
	 * @return
	 */
	private static OrderInfo[] parseOrderInfo(Map<String, String[]> parameterMap) {
		if (MapUtils.isEmpty(parameterMap)) {
			return null;
		}

		String columnReg = "^order\\[(.*?)\\](.*?)$";

		List<String[]> itemList = parseItem(parameterMap, columnReg);

		Map<Integer, OrderInfo> orderInfoMap = new HashMap<>();

		itemList.forEach(item -> parseOrderInfo(orderInfoMap, item));

		// 一共有多少列
		int columns = orderInfoMap.keySet().stream().mapToInt(Integer::intValue).max().getAsInt() + 1;

		OrderInfo[] orderInfos = new OrderInfo[columns];
		orderInfoMap.forEach((index, orderInfo) -> orderInfos[index] = orderInfo);

		return orderInfos;
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

		itemList.forEach(item -> parseColumnInfo(columnInfoMap, item));

		// 一共有多少列
		int columns = columnInfoMap.keySet().stream().mapToInt(Integer::intValue).max().getAsInt() + 1;

		ColumnInfo[] columnInfos = new ColumnInfo[columns];
		columnInfoMap.forEach((index, columnInfo) -> columnInfos[index] = columnInfo);

		return columnInfos;
	}

	private static void parseColumnInfo(Map<Integer, ColumnInfo> columnInfoMap, String[] item) {
		Integer index = Integer.parseInt(item[0]);
		String tag = item[1];
		String value = item[2];

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

	private static void parseOrderInfo(Map<Integer, OrderInfo> orderInfoMap, String[] item) {
		Integer index = Integer.parseInt(item[0]);
		String tag = item[1];
		String value = item[2];

		if (index == null || index < 0) {
			return;
		}

		OrderInfo orderInfo = orderInfoMap.get(index);
		if (orderInfo == null) {
			orderInfo = new OrderInfo();

			orderInfoMap.put(index, orderInfo);
		}

		switch (tag) {
			case "[column]":
				orderInfo.setColumn(Integer.parseInt(value));
				break;
			case "[dir]":
				orderInfo.setDir(value);
				break;
		}

	}

}
