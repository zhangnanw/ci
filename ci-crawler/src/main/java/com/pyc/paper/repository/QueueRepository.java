package com.pyc.paper.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class QueueRepository implements Repository {

	private Queue<String> lowqueue = new ConcurrentLinkedQueue<String>();
	private Queue<String> heighqueue = new ConcurrentLinkedQueue<String>();
	
	public String poll() {
		String url = this.heighqueue.poll();
		if(url==null){
			return this.lowqueue.poll();
		}
		return url;
	}

	public void add(String nextUrl) {
		this.lowqueue.add(nextUrl);
	}

	public void addHeight(String nextUrl) {
		this.heighqueue.add(nextUrl);
	}

}
