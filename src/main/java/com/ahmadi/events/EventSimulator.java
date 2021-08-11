package com.ahmadi.events;

import com.ahmadi.states.SimulatorEventType;
import com.ahmadi.utils.eventbus.Event;

public class EventSimulator extends Event {
	
	private final SimulatorEventType state;
	
	public EventSimulator(SimulatorEventType state) {
		this.state = state;
	}
	
	public SimulatorEventType getState() {
		return state;
	}
}
