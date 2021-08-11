package com.ahmadi.drawLayer;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.BoardComponentState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridDrawLayer extends DrawLayer{
	
	BoardComponentState state;
	
	public GridDrawLayer(BoardComponentState state) {
		this.state = state;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		
		Board board = state.getBoardProperty().getValue();
		// drawing lines
		gc.setStroke(Color.GREY);
		gc.setLineWidth(0.05f);
		
		for (int i = 0; i <= board.getWidth(); i++) {
			gc.strokeLine( i , 0 ,  i  , board.getHeight());
		}
		for (int i = 0; i <= board.getHeight(); i++) {
			gc.strokeLine( 0 , i , board.getWidth() , i);
		}
		// Ende drawing lines
	}
	
	@Override
	public int getLayerIndex() {
		return 10;
	}
}
