package com.ahmadi;

import com.ahmadi.interfaces.Board;
import com.ahmadi.model.CellState;
import com.ahmadi.viewModels.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class Canvas extends javafx.scene.canvas.Canvas {
	
	
	public boolean isEditing = true;
	
	private final BoardViewModel boardViewModel;
	private final SimulationViewModel simulationViewModel;
	
	private final InfoBar infobar;
	private final Affine affTrans;
	private boolean isMouseExitOfCanvas = true;
	private double cursorX, cursorY;
	
	ApplicationViewModel applicationViewModel;
	private final EditorViewModel editorViewModel;
	private CellState cellState = CellState.ALIVE;
	
	
	public Canvas(BoardViewModel boardViewModel,
	              InfoBar infobar,
	              ApplicationViewModel applicationViewModel,
	              EditorViewModel editorViewModel,
	              SimulationViewModel simulationViewModel) {
		
		this.boardViewModel = boardViewModel;
		this.simulationViewModel = simulationViewModel;
		
		this.boardViewModel.addListener(this::handleBoardChange);
		
		this.infobar = infobar;
		this.applicationViewModel = applicationViewModel;
		this.editorViewModel = editorViewModel;
		
		this.applicationViewModel.addListenerToList(this::handleAppStateChange);
		
		
		
		
		affTrans = new Affine();
		affTrans.appendScale(Math.round(SharedVariable.CANVAS_WIDTH.getValue() /  SharedVariable.BORD_WIDTH.getValue()) ,Math.round(SharedVariable.CANVAS_HEIGHT.getValue() / SharedVariable.BORD_HEIGHT.getValue()));
		
		
		this.setOnMouseClicked(this::handleMouseClick);
		this.setOnMouseMoved(this::handleMouseMoved);
		this.setOnMouseDragged(this::handleDragged);
		this.setOnMouseExited(this::handleMouseExit);
		this.setOnMouseEntered(this::handleMouseEntered);
		
		SharedVariable.CANVAS_WIDTH.addListener((observableValue, number, t1) -> {
			this.setWidth(SharedVariable.CANVAS_WIDTH.getValue());
			boardViewModel.setBoard(editorViewModel.getEditBoard());
		});
		SharedVariable.CANVAS_HEIGHT.addListener((observableValue, number, t1) -> {
			this.setHeight(SharedVariable.CANVAS_HEIGHT.getValue());
			boardViewModel.setBoard(editorViewModel.getEditBoard());
		});
		
		
	}
	
	
	
	private void handleBoardChange(Board board) {
		draw(board);
	}
	
	
	private void handleAppStateChange(ApplicationState applicationState) {
		if(applicationState == ApplicationState.EDITING){
			isEditing = true;
		}
		else if(applicationState == ApplicationState.SIMULATING){
			isEditing = false;
		}
	}
	
	
	private void handleMouseEntered(MouseEvent event) {
		isMouseExitOfCanvas = false;
	}
	
	private void handleMouseExit(MouseEvent event) {
		isMouseExitOfCanvas = true;
		
		
		if(isEditing){
			infobar.setCursorPosToLbl(new Point2D(0 ,0));
			boardViewModel.setBoard(editorViewModel.getEditBoard());
		}
		else boardViewModel.setBoard(simulationViewModel.getSimBoard());
	}
	
	private void handleDragged(MouseEvent event) {
			handleMouseEvent(event);
			if(isEditing){
				editorViewModel.getEditBoard().setState( (int) cursorX,(int) cursorY , cellState);
				editorViewModel.setEditBoard(editorViewModel.getEditBoard());
				simulationViewModel.setIsAllCellDead(editorViewModel.getEditBoard().isGridEmpty());
			}
		
	}
	
	private void handleMouseEvent(MouseEvent event){
		int x = (int) (Math.ceil(event.getX()/ SharedVariable.RESOLUTION.getValue()));
		int y = (int) (Math.ceil(event.getY()/ SharedVariable.RESOLUTION.getValue()));
		cursorX = x == 0 ? x : x-1;
		cursorY = y == 0 ? y : y-1;
		
		
		if(isEditing){
			infobar.setCursorPosToLbl(new Point2D(cursorX, cursorY));
			editorViewModel.setEditBoard(editorViewModel.getEditBoard());
		}
		
		
	}
	
	
	private void handleMouseMoved(MouseEvent event) {
		handleMouseEvent(event);
	}
	
	
	
	// Handle click on StandardBoard to set state to 1
	private void handleMouseClick(MouseEvent event) {
		int x = (int) (Math.ceil(event.getX()/ SharedVariable.RESOLUTION.getValue())) -1 ;
		int y = (int) (Math.ceil(event.getY()/ SharedVariable.RESOLUTION.getValue())) -1 ;
		
		if(isEditing){
			editorViewModel.getEditBoard().setState(x, y , cellState);
			editorViewModel.setEditBoard(editorViewModel.getEditBoard());
			simulationViewModel.setIsAllCellDead(editorViewModel.getEditBoard().isGridEmpty());
		}
	}
	
	
	
	// draw on the StandardBoard
	public void draw(Board board){
		GraphicsContext gc =  this.getGraphicsContext2D();
		gc.setTransform(affTrans);
		
		// drawing Background
		gc.setFill(Color.LIGHTGREY);
		gc.fillRect(0,0, SharedVariable.CANVAS_WIDTH.getValue(), SharedVariable.CANVAS_HEIGHT.getValue());
		
		
		// drawing if is State = 1
		gc.setFill(Color.BLACK);
		for (int i = 0; i < board.getWidth() -1 ; i++) {
			for (int j = 0; j < board.getHeight() -1; j++) {
				if(board.getGrid()[i][j] == CellState.ALIVE)
					gc.fillRect(i, j , 1,1);
			}
		}
		// ende drawing if is State = 1
		
		
		
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
		
		
		
		// Cursor hover draw
		if(!isMouseExitOfCanvas && isEditing){
			gc.setLineWidth(0.07f);
			gc.setStroke(Color.SADDLEBROWN);
		}
		else{
			gc.setStroke(null);
		}
		gc.strokeRect( cursorX, cursorY , 1,1);
		// Ende Cursor hover draw
	}
	
	
	
	// handling newCellState
	public void handleNewCellState(CellState cellState) {
		this.cellState = cellState;
	}
}
