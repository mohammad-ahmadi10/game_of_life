package com.ahmadi.command;

import com.ahmadi.states.CellState;
import com.ahmadi.states.EditorComponentState;
import com.ahmadi.utils.CursorPosition;

public class EditorBoardCommand implements UndoableCommand{
	
	private final CursorPosition pos;
	private final CellState CurrentCellState;
	private final CellState prevCellState;
	
	public EditorBoardCommand(CursorPosition pos, CellState CurrentCellState , CellState prevCellState) {
		this.pos = pos;
		this.CurrentCellState = CurrentCellState;
		this.prevCellState = prevCellState;
	}
	
	@Override
	public void undo(EditorComponentState state) {
		state.getEditBoardProperty().getValue().setState(pos , prevCellState);
	}
	
	@Override
	public void execute(EditorComponentState state) {
		state.getEditBoardProperty().getValue().setState(pos , CurrentCellState);
	}
	
	
}
