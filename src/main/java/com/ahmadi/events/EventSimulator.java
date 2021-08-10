package com.ahmadi.events;

import com.ahmadi.states.SimulationState;
import com.ahmadi.utils.eventbus.Event;

public class EventSimulator extends Event {
	
	private final SimulationState state;
	
	public EventSimulator(SimulationState state) {
		this.state = state;
	}
	
	public SimulationState getState() {
		return state;
	}
}
