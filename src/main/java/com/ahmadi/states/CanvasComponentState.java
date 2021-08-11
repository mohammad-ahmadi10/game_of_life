package com.ahmadi.states;

import com.ahmadi.events.CellStateEvent;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.Property;
import com.ahmadi.utils.eventbus.Event;

public class CanvasComponentState implements State{
	private final Property<Boolean> isEditing;
	private final Property<Boolean> isMouseOutOfCanvas;
	private final Property<Boolean> isAlive;
	private final Property<CursorPosition> cursorPositionProperty;
	
	public CanvasComponentState() {
		isEditing = new Property<>(true);
		isMouseOutOfCanvas = new Property<>(true);
		isAlive = new Property<>(true);
		cursorPositionProperty = new Property<>();
	}
	
	public Property<CursorPosition> getCursorPositionProperty() {
		return cursorPositionProperty;
	}
	
	public Property<Boolean> getIsAlive() {
		return isAlive;
	}
	
	public Property<Boolean> getIsEditing() {
		return isEditing;
	}
	
	public Property<Boolean> getIsMouseOutOfCanvas() {
		return isMouseOutOfCanvas;
	}
	
	
	public void handleNewCellState(Event event) {
		
		CellStateEvent cellState = (CellStateEvent) event;
		isAlive.setValue(cellState.getCellState() == CellState.ALIVE);
		
	}
}
