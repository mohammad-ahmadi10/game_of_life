package com.ahmadi.command;

import com.ahmadi.states.CellState;
import com.ahmadi.states.EditorState;
import com.ahmadi.utils.CursorPosition;

public class EditorBoardCommand implements EditorCommand{
	
	private final CursorPosition pos;
	private final CellState cellState;
	
	public EditorBoardCommand(CursorPosition pos, CellState cellState) {
		this.pos = pos;
		this.cellState = cellState;
	}
	
	
	@Override
	public void execute(EditorState state) {
		state.getEditBoardProperty().getValue().setState(pos , cellState);
	}
}