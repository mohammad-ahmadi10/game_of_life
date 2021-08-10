package com.ahmadi.states;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.utils.Property;

public class SimulationState implements State{
	private final Property<Board> board;
	
	public SimulationState(Board board) {
		this.board = new Property<>();
		this.board.setValue(board);
	}
	
	public Property<Board> getBoardPro() {
		return board;
	}
}
