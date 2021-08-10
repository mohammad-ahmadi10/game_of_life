package com.ahmadi.utils;

import com.ahmadi.model.interfaces.StateListener;

import java.util.LinkedList;
import java.util.List;

public class Property<T> {
	private T value ;
	
	private final List<StateListener<T>> listenerList;
	
	public Property(T value) {
		this.value = value;
		listenerList = new LinkedList<>();
	}
	
	public Property() {
		this(null);
	}
	
	
	private void notifyAllSimStateListeners(T newState) {
		this.listenerList.forEach(listener -> {
			try {
				
				listener.handle(newState);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	public void setValue(T newState) {
		this.value = newState;
		notifyAllSimStateListeners(newState);
	}
	
	public void subscribe(StateListener<T> listener){
		this.listenerList.add(listener);
	}
	
	public T getValue() {
		return value;
	}
	
}
