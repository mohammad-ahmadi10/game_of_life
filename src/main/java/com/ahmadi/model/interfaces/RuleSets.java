package com.ahmadi.model.interfaces;

import com.ahmadi.states.CellState;
import com.ahmadi.model.abstracts.Board;

public interface RuleSets {
	
	int countNeighbors(CellState[][] locBoard, int x, int y);
	CellState calcNextGen(int x, int y , Board board);
}
