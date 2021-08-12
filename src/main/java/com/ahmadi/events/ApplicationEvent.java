package com.ahmadi.events;

import com.ahmadi.states.ApplicationStateType;
import com.ahmadi.utils.eventbus.Event;

public class ApplicationEvent extends Event {
	
	ApplicationStateType state;
	
	public ApplicationEvent(ApplicationStateType state) {
		this.state = state;
	}
	
	public ApplicationStateType getState() {
		return state;
	}
}
