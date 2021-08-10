package com.ahmadi.states;

import java.util.HashMap;
import java.util.Map;

public class RegistryState {
	private final Map<Class<?> , State> stateList = new HashMap<>();
	
	public <T extends State> void register(Class<T> stateClass , State state){
		stateList.put(stateClass , state);
	}
	
	public <T> T getState(Class<T> stateClass) {
		return (T) stateList.get(stateClass);
	}
	
}
