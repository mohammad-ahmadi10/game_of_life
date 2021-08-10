package com.ahmadi.model;

import com.ahmadi.model.abstracts.Board;

public class StandardBoard extends Board {
	
	public StandardBoard(int width, int height , String boardName) {
		super(width, height , boardName);
	}
	
	public StandardBoard(int width, int height) {
		super(width, height , "initialBoard");
	}
	
}
