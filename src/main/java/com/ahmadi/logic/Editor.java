package com.ahmadi.logic;

import com.ahmadi.events.CellStateEvent;
import com.ahmadi.events.EditorMouseEvent;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.CellState;
import com.ahmadi.states.EditorMouseState;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.Property;
import com.ahmadi.utils.eventbus.Event;

public class Editor {
	
	
	// properties
	private final Property<Board> editBoardProperty;
	private final Property<CellState> cellStateProperty;
	private final Property<CursorPosition> cursorProperty;
	
	
	// Getters
	public Property<Board> getEditBoardProperty() {
		return editBoardProperty;
	}
	public Property<CellState> getCellStateProperty() {
		return cellStateProperty;
	}
	public Property<CursorPosition> getCursorProperty() {
		return cursorProperty;
	}
	
	// constructor
	public Editor(Board board) {
		
		editBoardProperty = new Property<>(board);
		cellStateProperty = new Property<>(CellState.ALIVE);
		cursorProperty = new Property<>(new CursorPosition(0 , 0));
	}
	
	
	public void handleCursorEvent(Event event) {
		EditorMouseEvent cursorEvent = (EditorMouseEvent) event;
		var x = cursorEvent.getCursorPosition().getX();
		var y = cursorEvent.getCursorPosition().getY();
		EditorMouseState state = cursorEvent.getState();
		switch (state){
			case CLICK:
			case DRAGGED:
				editBoardProperty.getValue().setState(x, y , cellStateProperty.getValue());
				break;
			case EXIT:
				cursorProperty.setValue(new CursorPosition(0, 0));
				break;
		}
		
		if(x == -1) x = 0;
		if(y == -1) y = 0;
		cursorProperty.setValue(new CursorPosition(x, y));
		this.getEditBoardProperty().setValue(this.getEditBoardProperty().getValue());
	}
	
	public void handleNewCellState(Event event) {
		CellStateEvent cellState = (CellStateEvent) event;
		cellStateProperty.setValue(cellState.getCellState());
	}
	
	
}
