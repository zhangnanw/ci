package org.yansou.ci.core.rest.report;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-06-20 20:16
 */
public class ReportUtils {

	public static ReportRo convert(List<Map<String, Object>> dataList, String xKey, String yKey, String[] serieKeys) {
		return convert(dataList, null, null, xKey, yKey, serieKeys);
	}

	public static ReportRo convert(List<Map<String, Object>> dataList, Map<Integer, String> xAxisDataMap, Map<Integer,
			String> yAxisDataMap, String xKey, String yKey, String[] serieKeys) {
		if (CollectionUtils.isEmpty(dataList)) {
			return null;
		}

		String[] xData = new String[dataList.size()];
		String[] yData = new String[dataList.size()];

		Axis[] xAxis = new Axis[]{new Axis(xData)};// X轴
		Axis[] yAxis = new Axis[]{new Axis(yData)};// Y轴

		Serie[] series = new Serie[serieKeys.length];// 图表上显示的值

		for (int i = 0; i < serieKeys.length; i++) {
			String name = serieKeys[i];
			double[] data = new double[dataList.size()];

			series[i] = new Serie(name, data);
		}

		for (int i = 0; i < dataList.size(); i++) {
			Map<String, Object> dataMap = dataList.get(i);

			xData[i] = getAxisData(dataMap, xAxisDataMap, xKey);
			yData[i] = getAxisData(dataMap, yAxisDataMap, yKey);

			for (int j = 0; j < serieKeys.length; j++) {
				double[] data = series[j].getData();

				data[i] = getDoubleValue(dataMap, serieKeys[j]);
			}

		}

		ReportRo reportRo = new ReportRo(xAxis, yAxis, series);

		return reportRo;
	}

	private static String getAxisData(Map<String, Object> dataMap, Map<Integer, String> axisDataMap, String key) {
		if (MapUtils.isEmpty(axisDataMap)) {
			return getStringValue(dataMap, key);
		}

		return axisDataMap.get(getIntValue(dataMap, key));
	}

	private static String getStringValue(Map<String, Object> map, String key) {
		Object value = map.get(key);

		if (value != null) {
			return value.toString();
		}

		return "";
	}

	private static double getDoubleValue(Map<String, Object> map, String key) {
		Object value = map.get(key);

		if (value != null) {
			return Double.parseDouble(value.toString());
		}

		return Double.MIN_VALUE;
	}

	private static int getIntValue(Map<String, Object> map, String key) {
		Object value = map.get(key);

		if (value != null) {
			return Integer.parseInt(value.toString());
		}

		return Integer.MIN_VALUE;
	}

}
