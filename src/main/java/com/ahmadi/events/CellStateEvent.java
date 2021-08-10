package com.ahmadi.events;

import com.ahmadi.states.CellState;
import com.ahmadi.utils.eventbus.Event;

public class CellStateEvent extends Event {
	private final CellState cellState;
	
	public CellStateEvent(CellState cellState) {
		this.cellState = cellState;
	}
	
	public CellState getCellState() {
		return cellState;
	}
}
