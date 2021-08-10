package com.ahmadi.events;

import com.ahmadi.states.SimulationState;
import com.ahmadi.states.SimulatorState;
import com.ahmadi.utils.eventbus.Event;

public class EventSimulator extends Event {
	
	private final SimulatorState state;
	
	public EventSimulator(SimulatorState state) {
		this.state = state;
	}
	
	public SimulatorState getState() {
		return state;
	}
}
