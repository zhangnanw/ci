package com.pyc.paper.repository;

public interface Repository {

	String poll();

	void add(String nextUrl);

	void addHeight(String nextUrl);

}
