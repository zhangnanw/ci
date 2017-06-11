package org.yansou.ci.crawler.utils;

public final class SleepUtil {

	@SuppressWarnings("static-access")
	public final static void sleep(long millis) {
		if (millis > 0) {
			try {
				Thread.currentThread().sleep(millis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
