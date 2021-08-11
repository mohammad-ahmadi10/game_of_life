package com.ahmadi.states;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.utils.Property;

public class SimulationComponentState implements State{
	private final Property<Board> board;
	
	public SimulationComponentState(Board board) {
		this.board = new Property<>();
		this.board.setValue(board);
	}
	
	public Property<Board> getBoardPro() {
		return board;
	}
}
