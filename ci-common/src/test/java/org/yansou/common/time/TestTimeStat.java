package org.yansou.common.time;

import org.junit.Test;
import org.yansou.ci.common.time.TimeStat;
import org.yansou.ci.common.utils.SleepUtils;

public class TestTimeStat {
	@Test
	public void testGetDiff() throws Exception {
		TimeStat ts = new TimeStat();
		SleepUtils.sleep(123);
		ts.burie();
		SleepUtils.sleep(5);
		ts.burie();
		SleepUtils.sleep(1);
		ts.burie();
		ts.getDiffStream().forEach(System.out::println);

	}
}
