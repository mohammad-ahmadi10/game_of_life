package com.ahmadi.interfaces;

import com.ahmadi.model.CellState;

public interface RuleSets {
	
	int countNeighbors(CellState[][] locBoard, int x, int y);
	CellState calcNextGen(int x, int y ,Board board);
}
