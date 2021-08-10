package com.ahmadi.events;

import com.ahmadi.states.ApplicationState;
import com.ahmadi.utils.eventbus.Event;

public class ApplicationEvent extends Event {
	
	ApplicationState state;
	
	public ApplicationEvent(ApplicationState state) {
		this.state = state;
	}
	
	public ApplicationState getState() {
		return state;
	}
}
