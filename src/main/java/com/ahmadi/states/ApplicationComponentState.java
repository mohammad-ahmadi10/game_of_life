package com.ahmadi.states;

import com.ahmadi.events.ApplicationEvent;
import com.ahmadi.states.ApplicationStateType;
import com.ahmadi.states.State;
import com.ahmadi.utils.Property;
import com.ahmadi.utils.eventbus.Event;

public class ApplicationComponentState implements State {
	private final Property<ApplicationStateType> appStateProperty;
	
	
	// Getters
	public Property<ApplicationStateType> getAppStateProperty() {
		return appStateProperty;
	}
	
	// constructor
	public ApplicationComponentState() {
		appStateProperty = new Property<>(ApplicationStateType.EDITING);
	}
	
	
	public void handleEvent(Event event) {
		ApplicationEvent appEvent = (ApplicationEvent) event;
		appStateProperty.setValue(appEvent.getState());
	}
}
