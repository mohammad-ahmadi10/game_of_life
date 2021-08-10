package com.ahmadi.utils.eventbus;

public interface EventListener<T extends Event>{
	void handle(Event value);
}
