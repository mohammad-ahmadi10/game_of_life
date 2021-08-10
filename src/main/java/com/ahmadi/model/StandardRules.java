package com.ahmadi.model;

import com.ahmadi.states.CellState;
import com.ahmadi.utils.SharedVariable;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.model.interfaces.RuleSets;

public class StandardRules implements RuleSets {
	
	@Override
	public int countNeighbors(CellState[][] locBoard, int x, int y) {
		int neighbors = 0;
		int col , row ;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2 ; j++) {
				
				col = (y + i + SharedVariable.BORD_HEIGHT) % SharedVariable.BORD_HEIGHT;
				row = (x + j + SharedVariable.BORD_WIDTH) % SharedVariable.BORD_WIDTH;
				
				if(locBoard[row][col] == CellState.ALIVE)
					neighbors +=1;
			}
		}
		if(locBoard[x][y] == CellState.ALIVE)
			neighbors -= 1;
		return neighbors;
	}
	
	@Override
	public CellState calcNextGen(int x, int y, Board board){
		
			int neighbors = countNeighbors(board.getGrid(), x ,  y );
			
			if(board.getGrid()[x][y] == CellState.ALIVE ){
				if(neighbors < 2 || neighbors > 3)
					return CellState.DEAD;
				else return CellState.ALIVE;
			}
			else if(board.getGrid()[x][y] == CellState.DEAD){
				if(neighbors == 3)
					return CellState.ALIVE;
				else	return CellState.DEAD;
			}
		return CellState.DEAD;
		
	}
	
}
