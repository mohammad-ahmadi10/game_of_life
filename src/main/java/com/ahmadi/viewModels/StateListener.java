package com.ahmadi.viewModels;



public interface StateListener<T> {
	void handle(T value) throws InterruptedException;
}
