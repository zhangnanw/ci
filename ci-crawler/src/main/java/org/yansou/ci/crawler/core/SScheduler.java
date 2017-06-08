package org.yansou.ci.crawler.core;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;

public class SScheduler extends DuplicateRemovedScheduler implements
		MonitorableScheduler {
	private static AtomicInteger allNumber = new AtomicInteger(0);
	private AtomicInteger number = new AtomicInteger(0);
	private LinkedList<String> queue = new LinkedList<String>();
	private LinkedList<Request> extraQueue = new LinkedList<>();

	@Override
	protected void pushWhenNoDuplicate(Request request, Task task) {
		if (request.getExtras() != null) {
			extraQueue.offerFirst(request);
		} else {
			queue.offerFirst(request.getUrl());
		}
		allNumber.incrementAndGet();
		number.incrementAndGet();
	}

	@Override
	public synchronized Request poll(Task task) {
		Request res = extraQueue.pollFirst();
		if (null != res) {
			allNumber.decrementAndGet();
			return res;
		}
		String url = queue.pollFirst();
		if (null != url) {
			allNumber.decrementAndGet();
			return new Request(url);
		}
		return res;
	}

	@Override
	public int getLeftRequestsCount(Task task) {
		return queue.size() + extraQueue.size();
	}

	@Override
	public int getTotalRequestsCount(Task task) {
		return number.get();
	}

	public static int allCount() {
		return allNumber.get();
	}

	static {
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("SScheduler allCount:" + allCount());
			}
		}, 1000, 5000);
	}
}
