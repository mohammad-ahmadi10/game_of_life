package com.ahmadi.model.interfaces;



public interface StateListener<T> {
	String name = null;
	void handle(T value) throws InterruptedException;
}
