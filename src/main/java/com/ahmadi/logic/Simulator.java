package com.ahmadi.logic;

import com.ahmadi.events.EventSimulator;
import com.ahmadi.model.Simulation;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.SimulatorEventType;
import com.ahmadi.utils.Property;
import com.ahmadi.utils.eventbus.Event;

public class Simulator {
	
	// properties
	private final Property<Board> simBoardProperty;
	private final Property<SimulatorEventType> simStateProperty;
	private final Simulation simulation;
	
	
	
	// Getters
	public Property<Board> getSimBoardProperty() {
		return simBoardProperty;
	}
	public Property<SimulatorEventType> getSimStateProperty() {
		return simStateProperty;
	}
	
	// Constructor
	public Simulator(Simulation simulation) {
		this.simulation = simulation;
		this.simStateProperty = new Property<>();
		this.simBoardProperty = new Property<>();
	}
	
	
	
	public void handleEvent(Event event) {
		EventSimulator e = (EventSimulator) event;
		simStateProperty.setValue(e.getState());
	}
	
	
	public Simulation getSimulation() {
		return simulation;
	}
	
	
}
