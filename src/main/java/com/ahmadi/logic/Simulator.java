package com.ahmadi.logic;

import com.ahmadi.events.EventSimulator;
import com.ahmadi.states.SimulatorComponentState;
import com.ahmadi.utils.eventbus.Event;

public class Simulator {
	
	private final SimulatorComponentState state;
	
	// Constructor
	public Simulator(SimulatorComponentState state) {
		this.state = state;
	}
	
	
	
	public void handleEvent(Event event) {
		EventSimulator e = (EventSimulator) event;
		state.getSimStateProperty().setValue(e.getState());
	}
	
	public SimulatorComponentState getState() {
		return state;
	}
}
