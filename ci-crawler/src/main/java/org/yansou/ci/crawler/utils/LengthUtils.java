package org.yansou.ci.crawler.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class LengthUtils {

	private static final long G = 1 << 30;
	private static final long M = 1 << 20;
	private static final long K = 1 << 10;

	public static String toString(long length) {
		AtomicInteger g = new AtomicInteger();
		AtomicInteger m = new AtomicInteger();
		AtomicInteger k = new AtomicInteger();
		length = ah0(length, G, g);
		length = ah0(length, M, m);
		length = ah0(length, K, k);

		if (g.get() > 0) {
			double num = 0;
			num += g.get();
			num += m.get() / 1000d;
			num += k.get() / (1000d * 1000d);
			return num + " GB";
		}
		if (m.get() > 0) {
			double num = 0;
			num += m.get();
			num += k.get() / 1000d;
			return num + " MB";
		}
		if (k.get() > 0) {
			double num = 0;
			num = k.get();
			return num + " KB";
		}
		return "0";
	}

	private final static long ah0(long length, long baseNum,
			AtomicInteger counter) {
		for (;;) {
			if (length < baseNum) {
				break;
			}
			length -= baseNum;
			counter.incrementAndGet();
		}

		return length;
	}
}
