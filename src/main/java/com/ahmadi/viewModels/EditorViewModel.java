package com.ahmadi.viewModels;

import com.ahmadi.interfaces.Board;
import com.ahmadi.model.CellState;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {
	
	private Board editBoard;
	private final SimpleBooleanProperty isGridEmpty  = new SimpleBooleanProperty(true);
	
	private final List<StateListener<Board>> editBoardListeners;
	private final List<StateListener<CellState>> cellStateListeners;
	private CellState cellState = CellState.ALIVE;
	
	
	public boolean isIsGridEmpty() {
		return isGridEmpty.get();
	}
	
	public SimpleBooleanProperty isGridEmptyProperty() {
		return isGridEmpty;
	}
	
	public EditorViewModel(Board board) {
		this.editBoard = board;
		this.editBoardListeners = new LinkedList<>();
		this.cellStateListeners = new LinkedList<>();
	}
	
	public void checkGridEmpty(){
		int countAllDeadState = 0;
		for (int i = 0; i < this.getEditBoard().getWidth(); i++) {
			for (int j = 0; j < this.getEditBoard().getHeight(); j++) {
				if(this.getEditBoard().getGrid()[i][j] == CellState.DEAD)
					countAllDeadState++;
			}
		}
		isGridEmpty.set(countAllDeadState == (getEditBoard().getWidth() * getEditBoard().getHeight()));
	}
	
	
	// editBoardListeners handling
	public void subscribeToEditBoardList(StateListener<Board> listener){
		editBoardListeners.add(listener);
	}
	public void setEditBoard(Board newBoard) {
		this.editBoard = newBoard;
		notifyAllEditBoardListeners();
		checkGridEmpty();
	}
	
	private void notifyAllEditBoardListeners() {
		editBoardListeners.forEach(listener -> {
			try {
				listener.handle(this.editBoard);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	public Board getEditBoard() {
		return editBoard;
	}
	// end editBoardListeners handling
	
	
	
	
	
	// editBoardListeners handling
	public void subscribeToCellStateList(StateListener<CellState> listener){
		cellStateListeners.add(listener);
	}
	
	public void changeCellState(CellState newState){
		if(newState == this.cellState)
			return;
		
		this.cellState = newState;
		notifyAllCellStateListeners();
	}
	private void notifyAllCellStateListeners() {
		cellStateListeners.forEach(listener -> {
			try {
				listener.handle(this.cellState);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	// end editBoardListeners handling
	
	
}
