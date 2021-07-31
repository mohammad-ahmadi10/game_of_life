package com.ahmadi.viewModels;

import com.ahmadi.interfaces.Board;

import java.util.LinkedList;
import java.util.List;


public class BoardViewModel {
	
	private Board simBoard;
	private final List<StateListener<Board>> boardStateListener;
	
	
	
	public BoardViewModel() {
		this.boardStateListener = new LinkedList<>();
	}
	
	public void addListener(StateListener<Board> listener){
		boardStateListener.add(listener);
	}
	
	public void setBoard(Board newBoard) {
	
		this.simBoard = newBoard;
		notifyAllListener();
	}
	
	private void notifyAllListener() {
		boardStateListener.forEach(listener -> {
			try {
				listener.handle(this.simBoard);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	public Board getSimBoard() {
		return simBoard;
	}
	
	
	
}
