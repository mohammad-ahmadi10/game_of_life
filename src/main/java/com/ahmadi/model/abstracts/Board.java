package com.ahmadi.model.abstracts;

import com.ahmadi.states.CellState;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Board {
	protected SimpleIntegerProperty width;
	protected SimpleIntegerProperty height;
	
	protected CellState[][] grid;
	public String boardName;
	
	public CellState[][] getGrid() {
		return grid;
	}
	
	
	public int getWidth() {
		return width.get();
	}
	
	public SimpleIntegerProperty widthProperty() {
		return width;
	}
	
	public int getHeight() {
		return height.get();
	}
	
	public SimpleIntegerProperty heightProperty() {
		return height;
	}
	
	public Board(int width, int height , String boardName) {
		this.width = new SimpleIntegerProperty(width);
		this.height = new SimpleIntegerProperty(height);
		this.boardName = boardName;
		this.grid = new CellState[width][height];
		resetCellState(this.grid);
	}
	
	public boolean isSameBoard(Board board){
		int countAllSame = 0;
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (board.getGrid()[i][j] == this.getGrid()[i][j])
					countAllSame++;
			}
		}
		return countAllSame == getWidth() * getHeight();
	}
	
	public boolean isGridEmpty(){
		int countDeadCell = 0;
		for (int i = 0; i < width.getValue() - 1; i++) {
			for (int j = 0; j < height.getValue() - 1; j++) {
				if(grid[i][j] == CellState.DEAD)
					countDeadCell++;
			}
		}
		
		return countDeadCell == (width.getValue() * height.getValue());
	}
	
	
	public void resetCellState(CellState[][] board){
		for (int i = 0; i < width.getValue(); i++) {
			for (int j = 0; j < height.getValue(); j++) {
				board[i][j] = CellState.DEAD;
			}
		}
	}
	
	public void setState(int x, int y, CellState state){
		if( (x >= 0 && x <= this.getWidth() -1 ) &&
			(y >= 0 && y <= this.getHeight() -1) )
		grid[x][y] = state;
	}
	
	public static void  copyBoard(Board mainBoard , Board prevBoard) {
		

		for (int i = 0; i < mainBoard.width.getValue() - 1; i++) {
			System.arraycopy(mainBoard.grid[i], 0, prevBoard.grid[i], 0, mainBoard.height.getValue() - 1);
		}
	}
	
	
	
	public void printBoard(){
		for (int i = 0; i < width.getValue() -1; i++) {
			for (int j = 0; j < height.getValue() -1; j++) {
				System.out.println(" ( " + i + " , " + j + " ): " + grid[i][j]);
			}
		}
	}
	
	public void resize(int width, int height) {
		this.widthProperty().set(width);
		this.heightProperty().set(height);
		
		CellState[][] newGrid = new CellState[width][height];
		int length = Math.min(this.grid.length, width);
		
		for (int i = 0; i < length; i++) {
			System.arraycopy(this.grid[i], 0, newGrid[i], 0, length);
		}
		this.grid = newGrid;
	}
}
