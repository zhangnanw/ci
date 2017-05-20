package org.yansou.ci.common.utils;

public class SleepUtils {
	final static public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}
