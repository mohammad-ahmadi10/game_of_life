package com.ahmadi.view;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.ApplicationState;
import com.ahmadi.states.CellState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GridPane extends Pane {
	private final Canvas canvas;
	private boolean isAlive = true;
	
	public GridPane(Canvas canvas) {
		this.canvas = canvas;
		this.getChildren().add(canvas);
	}
	
	
	public void handleNewBoard(Board board) {
		draw(board);
	}
	
	public void handleAppStateChange(ApplicationState applicationState) {
		if(applicationState == ApplicationState.EDITING){
			canvas.setEditing(true);
		}
		else if(applicationState == ApplicationState.SIMULATING){
			canvas.setEditing(false);
		}
	}
	
	
	
	
	// draw on the StandardBoard
	public void draw(Board board){
		GraphicsContext gc =  canvas.getGraphicsContext2D();
		gc.setTransform(canvas.getAffine());
		
		
		// drawing Background
		gc.setFill(Color.LIGHTGREY);
		gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
		
		
		// drawing if is State = 1
		gc.setFill(Color.BLACK);
		for (int i = 0; i < board.getWidth() ; i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if(board.getGrid()[i][j] == CellState.ALIVE)
					gc.fillRect(i, j , 1,1);
			}
		}
		// ende drawing if is State = 1
		
		
		
		// Cursor hover draw
		if(!canvas.isMouseOutOfCanvas() && canvas.isEditing()){
			if(isAlive)
				gc.setStroke(Color.GREEN);
			else gc.setStroke(Color.SADDLEBROWN);
			gc.setLineWidth(0.07f);
		}
		else{
			gc.setStroke(Color.LIGHTGREY);
		}
		
		if(canvas.getCursorPosition() != null)
			gc.strokeRect( canvas.getCursorPosition().getX(), canvas.getCursorPosition().getY() , 1,1);
		// Ende Cursor hover draw
		
		
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
	
	public void handleNewCellState(CellState cellState) {
		isAlive = cellState.equals(CellState.ALIVE);
	}
	
}
