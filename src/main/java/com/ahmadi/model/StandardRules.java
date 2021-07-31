package com.ahmadi.model;

import com.ahmadi.SharedVariable;
import com.ahmadi.interfaces.Board;
import com.ahmadi.interfaces.RuleSets;

public class StandardRules implements RuleSets {
	
	@Override
	public int countNeighbors(CellState[][] locBoard, int x, int y) {
		int neighbors = 0;
		
		
		
		int col , row ;
		
		System.out.println("width: " +SharedVariable.BORD_WIDTH);
		System.out.println( "height: " + SharedVariable.BORD_HEIGHT);
		
		
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2 ; j++) {
				
				col = (x + i + SharedVariable.BORD_HEIGHT.getValue()) % SharedVariable.BORD_HEIGHT.getValue();
				row = (y + j + SharedVariable.BORD_WIDTH.getValue()) % SharedVariable.BORD_WIDTH.getValue();
				System.out.println(col);
				System.out.println(row);
				if(locBoard[col][row] == CellState.ALIVE)
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
