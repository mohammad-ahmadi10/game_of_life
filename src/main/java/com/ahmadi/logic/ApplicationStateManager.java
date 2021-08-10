package com.ahmadi.logic;

import com.ahmadi.events.ApplicationEvent;
import com.ahmadi.states.ApplicationState;
import com.ahmadi.utils.Property;
import com.ahmadi.utils.eventbus.Event;

public class ApplicationStateManager {
	private final Property<ApplicationState> appStateProperty;
	
	
	// Getters
	public Property<ApplicationState> getAppStateProperty() {
		return appStateProperty;
	}
	
	// constructor
	public ApplicationStateManager() {
		appStateProperty = new Property<>(ApplicationState.EDITING);
	}
	
	
	public void handleEvent(Event event) {
		ApplicationEvent appEvent = (ApplicationEvent) event;
		appStateProperty.setValue(appEvent.getState());
	}
}
