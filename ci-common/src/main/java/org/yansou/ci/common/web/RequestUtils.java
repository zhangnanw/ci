package org.yansou.ci.common.web;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-16 10:05
 */
public class RequestUtils {

	private static final Logger LOG = LogManager.getLogger(RequestUtils.class);

	public static String getStringParameter(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);

		return strValue;
	}

	public static String getStringParameter(Map<String, String[]> parameterMap, String name) {
		if (MapUtils.isEmpty(parameterMap) || StringUtils.isBlank(name)) {
			return null;
		}

		String[] values = parameterMap.get(name);
		if (ArrayUtils.isEmpty(values)) {
			return null;
		}

		return values[0];
	}

	/**
	 * 根据参数名称的前缀获取相应的参数值
	 *
	 * @param parameterMap
	 * @param namePrefix
	 *            参数名称的前缀
	 * @param length
	 *            参数的个数
	 *
	 * @return
	 */
	public static String[] getStringArrayParameter(Map<String, String[]> parameterMap, String namePrefix, int length) {
		if (MapUtils.isEmpty(parameterMap) || StringUtils.isBlank(namePrefix) || length <= 0) {
			return null;
		}

		String[] result = new String[length];

		int[] count = { 0 };

		parameterMap.forEach((name, values) -> {
			if (name.startsWith(namePrefix)) {
				int index = Integer.parseInt(name.split("_", 2)[1]);

				result[index] = values[0];
				count[0]++;
			}
		});

		if (count[0] == length) {
			return result;
		}

		return null;
	}

	public static long getLongParameter(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);

		if (StringUtils.isBlank(strValue)) {
			return -1L;
		}

		try {
			return Long.parseLong(strValue);
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage(), e);
		}

		return -1L;
	}

	public static int getIntParameter(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);

		if (StringUtils.isBlank(strValue)) {
			return -1;
		}

		try {
			return Integer.parseInt(strValue);
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage(), e);
		}

		return -1;
	}

	public static int getIntParameter(Map<String, String[]> parameterMap, String name) {
		if (MapUtils.isEmpty(parameterMap) || StringUtils.isBlank(name)) {
			return -1;
		}

		String[] values = parameterMap.get(name);
		if (ArrayUtils.isEmpty(values)) {
			return -1;
		}

		try {
			return Integer.parseInt(values[0]);
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage(), e);
		}

		return -1;
	}

	/**
	 * 根据参数名称的前缀获取相应的参数值
	 *
	 * @param parameterMap
	 * @param namePrefix
	 *            参数名称的前缀
	 * @param length
	 *            参数的个数
	 *
	 * @return
	 */
	public static int[] getIntArrayParameter(Map<String, String[]> parameterMap, String namePrefix, int length) {
		if (MapUtils.isEmpty(parameterMap) || StringUtils.isBlank(namePrefix) || length <= 0) {
			return null;
		}

		int[] result = new int[length];

		int[] count = { 0 };

		parameterMap.forEach((name, values) -> {
			if (name.startsWith(namePrefix)) {
				int index = Integer.parseInt(name.split("_", 2)[1]);

				result[index] = Integer.parseInt(values[0]);
				count[0]++;
			}
		});

		if (count[0] == length) {
			return result;
		}

		return null;
	}

	public static boolean getBooleanParameter(HttpServletRequest request, String name) {
		String strValue = request.getParameter(name);

		return Boolean.parseBoolean(strValue);
	}

	public static boolean getBooleanParameter(Map<String, String[]> parameterMap, String name) {
		if (MapUtils.isEmpty(parameterMap) || StringUtils.isBlank(name)) {
			return false;
		}

		String[] values = parameterMap.get(name);
		if (ArrayUtils.isEmpty(values)) {
			return false;
		}

		return Boolean.parseBoolean(values[0]);
	}

	/**
	 * 根据参数名称的前缀获取相应的参数值
	 *
	 * @param parameterMap
	 * @param namePrefix
	 *            参数名称的前缀
	 * @param length
	 *            参数的个数
	 *
	 * @return
	 */
	public static boolean[] getBooleanArrayParameter(Map<String, String[]> parameterMap, String namePrefix,
			int length) {
		if (MapUtils.isEmpty(parameterMap) || StringUtils.isBlank(namePrefix) || length <= 0) {
			return null;
		}

		boolean[] result = new boolean[length];

		int[] count = { 0 };

		parameterMap.forEach((name, values) -> {
			if (name.startsWith(namePrefix)) {
				int index = Integer.parseInt(name.split("_", 2)[1]);

				result[index] = Boolean.parseBoolean(values[0]);
				count[0]++;
			}
		});

		if (count[0] == length) {
			return result;
		}

		return null;
	}

	/**
	 * 获得本机ip
	 *
	 * @return
	 */
	public static String getLocalIp() {
		try {
			InetAddress addr = InetAddress.getLocalHost();

			return addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			LOG.error(e.getMessage(), e);

			return null;
		}
	}

	/**
	 * 获得远程ip
	 *
	 * @param request
	 *
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}

			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}

			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}

			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}

			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}

		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");

			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];

				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}

		return ip;
	}

	public static String getRealIp() {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP

		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}

		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP

		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();

			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {//
					// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
						&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}

		if (netip != null && !"".equals(netip)) {
			return netip;
		} else {
			return localip;
		}
	}

}
