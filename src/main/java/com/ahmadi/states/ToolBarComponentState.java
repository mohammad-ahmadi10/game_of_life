package com.ahmadi.states;

import com.ahmadi.events.CellStateEvent;
import com.ahmadi.utils.Property;
import com.ahmadi.utils.eventbus.Event;

public class ToolBarComponentState implements State{
	
	private final Property<CellState> CellState;
	
	public ToolBarComponentState() {
		CellState = new Property<>();
	}
	
	public Property<com.ahmadi.states.CellState> getCellState() {
		return CellState;
	}
	
	public void handleNewCellState(Event event){
		CellStateEvent e = (CellStateEvent) event;
		this.CellState.setValue(e.getCellState());
	}
	
}
