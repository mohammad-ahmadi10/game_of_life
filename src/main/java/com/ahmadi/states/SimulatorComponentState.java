package com.ahmadi.states;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.utils.Property;

public class SimulatorComponentState implements State{
	// properties
	private final Property<Board> simBoardProperty;
	private final Property<SimulatorEventType> simStateProperty;
	
	
	
	public SimulatorComponentState(Board board) {
		simBoardProperty = new Property<>(board);
		simStateProperty = new Property<>(SimulatorEventType.STOP);
	}
	
	public Property<Board> getSimBoardProperty() {
		return simBoardProperty;
	}
	
	public Property<SimulatorEventType> getSimStateProperty() {
		return simStateProperty;
	}
}
