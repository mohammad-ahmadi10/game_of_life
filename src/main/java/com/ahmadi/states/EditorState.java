package com.ahmadi.states;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.Property;

public class EditorState implements State{
	// properties
	private final Property<Board> editBoardProperty;
	private final Property<CellState> cellStateProperty;
	private final Property<CursorPosition> cursorProperty;
	
	
	public EditorState(Board board) {
		editBoardProperty = new Property<>(board);
		cellStateProperty = new Property<>(CellState.ALIVE);
		cursorProperty = new Property<>(new CursorPosition(0 , 0));
	}
	
	public Property<Board> getEditBoardProperty() {
		return editBoardProperty;
	}
	
	public Property<CellState> getCellStateProperty() {
		return cellStateProperty;
	}
	
	public Property<CursorPosition> getCursorProperty() {
		return cursorProperty;
	}
}
