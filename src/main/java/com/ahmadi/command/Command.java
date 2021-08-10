package com.ahmadi.command;

public interface Command<T> {
	void execute(T t);
	Class<T> getStateClass();
	
}
