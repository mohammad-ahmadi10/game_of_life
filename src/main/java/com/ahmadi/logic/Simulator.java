package com.ahmadi.logic;

import com.ahmadi.events.EventSimulator;
import com.ahmadi.model.Simulation;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.SimulationState;
import com.ahmadi.states.SimulatorState;
import com.ahmadi.utils.Property;
import com.ahmadi.utils.eventbus.Event;

public class Simulator {
	
	// properties
	private final Property<Board> simBoardProperty;
	private final Property<SimulatorState> simStateProperty;
	private final Simulation simulation;
	
	
	
	// Getters
	public Property<Board> getSimBoardProperty() {
		return simBoardProperty;
	}
	public Property<SimulatorState> getSimStateProperty() {
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
