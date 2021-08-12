package com.ahmadi.command;

import com.ahmadi.states.CellState;
import com.ahmadi.states.EditorComponentState;

public class CellStateCommand  implements EditorCommand{
	
	private final CellState cellState;
	
	
	public CellStateCommand(CellState cellState) {
		this.cellState = cellState;
	}
	
	@Override
	public void execute(EditorComponentState state) {
		state.getCellStateProperty().setValue(cellState);
	}
	
}
