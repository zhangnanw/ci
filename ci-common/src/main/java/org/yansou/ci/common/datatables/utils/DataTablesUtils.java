package org.yansou.ci.common.datatables.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yansou.ci.common.datatables.mapping.Column;
import org.yansou.ci.common.datatables.mapping.DataTablesInput;
import org.yansou.ci.common.datatables.mapping.DataTablesOutput;
import org.yansou.ci.common.datatables.mapping.Order;
import org.yansou.ci.common.datatables.mapping.Search;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liutiejun
 * @create 2017-05-16 9:49
 */
public class DataTablesUtils {

	private static final Logger LOG = LogManager.getLogger(DataTablesUtils.class);

	/**
	 * 解析分页参数
	 *
	 * @param request
	 *
	 * @return
	 */
	public static DataTablesInput parseRequest(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();

		Integer draw = RequestUtils.getIntParameter(parameterMap, "draw");
		Integer start = RequestUtils.getIntParameter(parameterMap, "start");
		Integer length = RequestUtils.getIntParameter(parameterMap, "length");

		Column[] columns = parseColumn(parameterMap);
		Order[] orders = parseOrder(parameterMap);
		Search search = parseSearch(parameterMap);

		DataTablesInput dataTablesInput = new DataTablesInput();
		dataTablesInput.setDraw(draw);
		dataTablesInput.setStart(start);
		dataTablesInput.setLength(length);
		dataTablesInput.setColumns(columns);
		dataTablesInput.setOrder(orders);
		dataTablesInput.setSearch(search);

		return dataTablesInput;
	}

	/**
	 * 更新查询条件
	 *
	 * @param pageCriteria
	 * @param propertyName
	 * @param value
	 * @param valueType
	 * @param searchOp
	 */
	public static void updateSearchInfo(PageCriteria pageCriteria, String propertyName, Object value, String
			valueType, SearchInfo.SearchOp searchOp) {
		SearchInfo searchInfo = new SearchInfo();
		searchInfo.setPropertyName(propertyName);
		searchInfo.setValue(value);
		searchInfo.setValueType(valueType);
		searchInfo.setSearchOp(searchOp);

		updateColumnInfo(pageCriteria, propertyName, true, null, searchInfo, null);
	}

	public static void updateColumnInfo(PageCriteria pageCriteria, String propertyName, Boolean searchable, Boolean
			orderable, SearchInfo searchInfo, OrderInfo orderInfo) {
		if (pageCriteria == null || StringUtils.isBlank(propertyName)) {
			return;
		}

		List<ColumnInfo> columnInfoList = pageCriteria.getColumnInfoList();
		if (columnInfoList == null) {
			columnInfoList = new ArrayList<>();

			pageCriteria.setColumnInfoList(columnInfoList);
		}

		List<ColumnInfo> columnInfoList1 = columnInfoList.stream().filter(columnInfo -> columnInfo.getPropertyName()
				.equals(propertyName)).collect(Collectors.toList());

		ColumnInfo columnInfo = null;
		if (CollectionUtils.isEmpty(columnInfoList1)) {// 没有找到
			columnInfo = new ColumnInfo();

			columnInfoList.add(columnInfo);
		} else {
			columnInfo = columnInfoList1.get(0);
		}

		columnInfo.setPropertyName(propertyName);

		if (searchable != null) {
			columnInfo.setSearchable(searchable);
		}

		if (orderable != null) {
			columnInfo.setOrderable(orderable);
		}

		if (searchInfo != null) {
			SearchInfo searchInfo1 = columnInfo.getSearchInfo();

			if (searchInfo1 != null) {// 更新查询值
				searchInfo.setValue(searchInfo1.getValue());
			}

			if (searchInfo.getValue() != null) {
				columnInfo.setSearchInfo(searchInfo);
			}

		}

		if (orderInfo != null) {
			columnInfo.setOrderInfo(orderInfo);
		}

	}

	/**
	 * 进行数据格式的转换
	 *
	 * @param dataTablesInput
	 *
	 * @return
	 */
	public static PageCriteria convert(DataTablesInput dataTablesInput) {
		Integer draw = dataTablesInput.getDraw();
		Integer start = dataTablesInput.getStart();
		Integer pageSize = dataTablesInput.getLength();

		Integer currentPageNo = PaginationUtils.calCurrentPageNo(start, pageSize);

		List<ColumnInfo> columnInfoList = convertColumnInfo(dataTablesInput.getColumns(), dataTablesInput.getOrder(),
				dataTablesInput.getSearch());

		PageCriteria pageCriteria = new PageCriteria();
		pageCriteria.setDraw(draw);
		pageCriteria.setStart(start);
		pageCriteria.setPageSize(pageSize);
		pageCriteria.setCurrentPageNo(currentPageNo);
		pageCriteria.setColumnInfoList(columnInfoList);

		return pageCriteria;
	}

	/**
	 * 进行数据格式的转换
	 *
	 * @param column
	 * @param search 全局查询条件
	 *
	 * @return
	 */
	private static SearchInfo convertSearchInfo(Column column, Search search) {
		if (!column.getSearchable()) {
			return null;
		}

		String value = column.getSearch().getValue();
		if (StringUtils.isEmpty(value)) {
			value = search.getValue();
		}

		if (StringUtils.isBlank(value)) {
			return null;
		}

		SearchInfo searchInfo = new SearchInfo();

		searchInfo.setPropertyName(column.getData());
		searchInfo.setValue(value);
		searchInfo.setValueType(String.class.getTypeName());
		searchInfo.setSearchOp(SearchInfo.SearchOp.LIKE);

		return searchInfo;
	}

	/**
	 * 进行数据格式的转换
	 *
	 * @param column
	 * @param order
	 *
	 * @return
	 */
	private static OrderInfo convertOrderInfo(Column column, Order order) {
		if (!column.getOrderable()) {
			return null;
		}

		OrderInfo orderInfo = new OrderInfo();

		orderInfo.setPropertyName(column.getData());

		if ("asc".equalsIgnoreCase(order.getDir())) {
			orderInfo.setOrderOp(OrderInfo.OrderOp.ASC);
		} else if ("desc".equalsIgnoreCase(order.getDir())) {
			orderInfo.setOrderOp(OrderInfo.OrderOp.DESC);
		}

		return orderInfo;
	}

	/**
	 * 进行数据格式的转换
	 *
	 * @param column
	 * @param search
	 *
	 * @return
	 */
	private static ColumnInfo convertColumnInfo(Column column, Search search) {
		ColumnInfo columnInfo = new ColumnInfo();

		columnInfo.setPropertyName(column.getData());
		columnInfo.setSearchable(column.getSearchable());
		columnInfo.setOrderable(column.getOrderable());

		columnInfo.setSearchInfo(convertSearchInfo(column, search));

		return columnInfo;
	}

	/**
	 * 进行数据格式的转换
	 *
	 * @param columns
	 * @param orders
	 * @param search
	 *
	 * @return
	 */
	private static List<ColumnInfo> convertColumnInfo(Column[] columns, Order[] orders, Search search) {
		List<ColumnInfo> columnInfoList = new ArrayList<>();

		Arrays.stream(columns).forEach(column -> columnInfoList.add(convertColumnInfo(column, search)));

		Arrays.stream(orders).forEach(order -> {
			Integer columnIndex = order.getColumn();

			OrderInfo orderInfo = convertOrderInfo(columns[columnIndex], order);

			columnInfoList.get(columnIndex).setOrderInfo(orderInfo);
		});

		return columnInfoList;
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
	public static <T> DataTablesOutput<T> parseResponse(Pagination<T> pagination, Integer draw, String error) {
		if (pagination == null) {
			return null;
		}

		DataTablesOutput<T> dataTablesOutput = new DataTablesOutput<T>();
		dataTablesOutput.setDraw(draw);
		dataTablesOutput.setRecordsTotal(pagination.getTotalCount());
		dataTablesOutput.setRecordsFiltered(pagination.getTotalCount());
		dataTablesOutput.setData(pagination.getDataArray());
		dataTablesOutput.setError(error);

		return dataTablesOutput;
	}

	/**
	 * 解析Search，数据格式如下：
	 * <ul>
	 * <li>search[value]:</li>
	 * <li>search[regex]:false</li>
	 * </ul>
	 *
	 * @param parameterMap
	 *
	 * @return
	 */
	private static Search parseSearch(Map<String, String[]> parameterMap) {
		String value = RequestUtils.getStringParameter(parameterMap, "search[value]");
		Boolean regex = RequestUtils.getBooleanParameter(parameterMap, "search[regex]");

		Search search = new Search();
		search.setValue(value);
		search.setRegex(regex);

		return search;
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
	private static Order[] parseOrder(Map<String, String[]> parameterMap) {
		if (MapUtils.isEmpty(parameterMap)) {
			return null;
		}

		String columnReg = "^order\\[(.*?)\\](.*?)$";

		List<String[]> itemList = parseItem(parameterMap, columnReg);

		Map<Integer, Order> orderMap = new HashMap<>();

		itemList.forEach(item -> parseOrder(orderMap, item));

		// 一共有多少列
		int columnLen = 0;

		if (MapUtils.isNotEmpty(orderMap)) {
			columnLen = orderMap.keySet().stream().mapToInt(Integer::intValue).max().getAsInt() + 1;
		}

		Order[] orders = new Order[columnLen];

		if (MapUtils.isNotEmpty(orderMap)) {
			orderMap.forEach((index, order) -> orders[index] = order);
		}

		return orders;
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
	private static Column[] parseColumn(Map<String, String[]> parameterMap) {
		if (MapUtils.isEmpty(parameterMap)) {
			return null;
		}

		String columnReg = "^columns\\[(.*?)\\](.*?)$";

		List<String[]> itemList = parseItem(parameterMap, columnReg);

		Map<Integer, Column> columnMap = new HashMap<>();

		itemList.forEach(item -> parseColumn(columnMap, item));

		// 一共有多少列
		int columnLen = columnMap.keySet().stream().mapToInt(Integer::intValue).max().getAsInt() + 1;

		Column[] columns = new Column[columnLen];
		columnMap.forEach((index, column) -> columns[index] = column);

		return columns;
	}

	private static void parseColumn(Map<Integer, Column> columnMap, String[] item) {
		Integer index = Integer.parseInt(item[0]);
		String tag = item[1];
		String value = item[2];

		if (index == null || index < 0) {
			return;
		}

		Column column = columnMap.get(index);
		if (column == null) {
			column = new Column();

			columnMap.put(index, column);
		}

		switch (tag) {
			case "[data]":
				column.setData(value);
				break;
			case "[name]":
				column.setName(value);
				break;
			case "[searchable]":
				column.setSearchable(Boolean.parseBoolean(value));
				break;
			case "[orderable]":
				column.setOrderable(Boolean.parseBoolean(value));
				break;
			case "[search][value]":
				Search search1 = column.getSearch();
				if (search1 == null) {
					search1 = new Search();
					column.setSearch(search1);
				}

				search1.setValue(value);
				break;
			case "[search][regex]":
				Search search2 = column.getSearch();
				if (search2 == null) {
					search2 = new Search();
					column.setSearch(search2);
				}

				search2.setRegex(Boolean.parseBoolean(value));
				break;
		}

	}

	private static void parseOrder(Map<Integer, Order> orderMap, String[] item) {
		Integer index = Integer.parseInt(item[0]);
		String tag = item[1];
		String value = item[2];

		if (index == null || index < 0) {
			return;
		}

		Order order = orderMap.get(index);
		if (order == null) {
			order = new Order();

			orderMap.put(index, order);
		}

		switch (tag) {
			case "[column]":
				order.setColumn(Integer.parseInt(value));
				break;
			case "[dir]":
				order.setDir(value);
				break;
		}

	}

}
