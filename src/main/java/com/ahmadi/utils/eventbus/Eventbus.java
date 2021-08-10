package com.ahmadi.utils.eventbus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Eventbus {
	private Map<Class<? extends Event> , List<EventListener<? extends Event>>> listenerList;
	
	public Eventbus() {
		listenerList = new HashMap<>();
	}
	
	
	public void emitEvent(Event event){
		Class<? extends Event> eventClass = event.getClass();
		List<EventListener<? extends Event>> eventListeners = listenerList.get(eventClass);
		if(eventListeners != null)
			eventListeners.forEach(listener -> listener.handle(event));
	}
	
	
	
	public <T extends Event> void subscribeTo(Class<T> eventClass , EventListener<T> listener){
		if(!listenerList.containsKey(eventClass)){
			listenerList.put(eventClass , new LinkedList<>());
		}
		listenerList.get(eventClass).add(listener);
	}
	
	
	
	
}
