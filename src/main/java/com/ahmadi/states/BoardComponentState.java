package com.ahmadi.states;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.utils.Property;

public class BoardComponentState implements State{
	private final Property<Board> boardProperty;
	
	public BoardComponentState(Board board) {
		boardProperty = new Property<>(board);
	}
	
	public Property<Board> getBoardProperty() {
		return boardProperty;
	}
}
