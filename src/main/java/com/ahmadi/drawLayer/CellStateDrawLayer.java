package com.ahmadi.drawLayer;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.BoardComponentState;
import com.ahmadi.states.CellState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CellStateDrawLayer extends DrawLayer{
	
	
	BoardComponentState state;
	
	public CellStateDrawLayer(BoardComponentState state) {
		this.state = state;
		state.getBoardProperty().subscribe(Board -> this.invalidate());
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		
		Board board = state.getBoardProperty().getValue();
		// drawing if is State = 1
		gc.setFill(Color.BLACK);
		for (int i = 0; i < board.getWidth() ; i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if(board.getGrid()[i][j] == CellState.ALIVE)
					gc.fillRect(i, j , 1,1);
			}
		}
		// ende drawing if is State = 1
	}
	
	@Override
	public int getLayerIndex() {
		return 0;
	}
}
