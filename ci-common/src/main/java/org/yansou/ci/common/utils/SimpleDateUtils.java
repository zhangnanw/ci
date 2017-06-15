package org.yansou.ci.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author liutiejun
 * @create 2017-06-15 15:10
 */
public class SimpleDateUtils {

	/**
	 * 使用给定的年、月、日、时、分、秒等数据生成对应的日期
	 *
	 * @param year
	 * @param month
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 *
	 * @return
	 */
	public static Date getADate(int year, int month, int date, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month - 1, date, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * 获得当前的Date，并且时、分、秒设置为0
	 *
	 * @return
	 */
	public static Date getCurrDate() {
		return setZeroForHMS(new Date());
	}

	/**
	 * 设置时、分、秒、毫秒为0
	 *
	 * @param date
	 *
	 * @return
	 */
	public static Date setZeroForHMS(Date date) {
		return updateDate(date, 0, 0, 0, 0);
	}

	/**
	 * 更新日期
	 *
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 *
	 * @return
	 */
	public static Date updateDate(Date date, int hour, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (hour >= 0) {
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		}

		if (minute >= 0) {
			calendar.set(Calendar.MINUTE, minute);
		}

		if (second >= 0) {
			calendar.set(Calendar.SECOND, second);
		}

		if (millisecond >= 0) {
			calendar.set(Calendar.MILLISECOND, millisecond);
		}

		return calendar.getTime();
	}

}
