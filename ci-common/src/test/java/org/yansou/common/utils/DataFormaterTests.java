package org.yansou.common.utils;

import org.junit.Test;
import org.yansou.ci.common.utils.DateFormater;

import java.util.Arrays;
import java.util.Date;

/**
 * @author liutiejun
 * @create 2017-06-11 15:03
 */
public class DataFormaterTests {

	@Test
	public void testParse() {
		String[] strs = new String[]{"2017-04-01", "2017-04-04 15:08:12"};

		Arrays.stream(strs).forEach(str -> {
			Date date = DateFormater.parse(str);

			System.out.println(DateFormater.format(date));
		});

	}

}
