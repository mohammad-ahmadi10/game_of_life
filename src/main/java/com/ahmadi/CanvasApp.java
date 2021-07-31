package com.ahmadi;

import com.ahmadi.interfaces.Board;
import com.ahmadi.interfaces.RuleSets;
import com.ahmadi.model.CellState;
import com.ahmadi.model.StandardBoard;
import com.ahmadi.model.StandardRules;
import com.ahmadi.viewModels.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class CanvasApp extends BorderPane {
	
	
	
	public Canvas canvas;
	public EditorViewModel editorViewModel;
	public ToolBar toolbar;
	
	public CanvasApp() {
		
		Board board = new StandardBoard(SharedVariable.BORD_WIDTH.getValue() , SharedVariable.BORD_HEIGHT.getValue() , "initBoard");
		RuleSets standardRules = new StandardRules();
		
		ApplicationViewModel applicationViewModel = new ApplicationViewModel(ApplicationState.EDITING);
		editorViewModel = new EditorViewModel(board);
		SimulationViewModel simulationViewModel = new SimulationViewModel();
		
		BoardViewModel boardViewModel = new BoardViewModel();
		editorViewModel.subscribeToEditBoardList(boardViewModel::setBoard);
		
		simulationViewModel.registerToSimBoardList(boardViewModel::setBoard);
		simulationViewModel.registerToSimBoardList(editorViewModel::setEditBoard);
		simulationViewModel.setSimBoard(editorViewModel.getEditBoard());
		
		Simulation simulation = new Simulation(standardRules , simulationViewModel, applicationViewModel);
		simulationViewModel.registerToSimtstate(simulation::handleNewSimState);
		
		
		InfoBar infoBar =  new InfoBar();
		applicationViewModel.addListenerToList(infoBar::setAppMode);
		editorViewModel.subscribeToCellStateList(infoBar::setDrawMode);
		canvas = new Canvas( boardViewModel , infoBar , applicationViewModel, editorViewModel, simulationViewModel);
		editorViewModel.subscribeToCellStateList(canvas::handleNewCellState);
		
		toolbar  = new ToolBar(applicationViewModel, simulationViewModel, editorViewModel);
		
		
		this.widthProperty().addListener((observableValue, number, t1) -> SharedVariable.CANVAS_WIDTH.set((int) Math.round(t1.doubleValue() / 1.1)));
		this.heightProperty().addListener((observableValue, number, t1) -> SharedVariable.CANVAS_HEIGHT.set((int) Math.round(t1.doubleValue() / 1.05)));
		
		
		// only changes the Canvas_Width .... Canvas_height got problem
		SharedVariable.BORD_HEIGHT.bind(SharedVariable.CANVAS_WIDTH.divide(SharedVariable.RESOLUTION));
		SharedVariable.BORD_WIDTH.bind(SharedVariable.CANVAS_WIDTH.divide(SharedVariable.RESOLUTION));
		
		
		SharedVariable.BORD_WIDTH.addListener((observableValue, number, t1) ->
				board.resize((int)t1, (int)t1));
		
		
		
		this.setOnKeyPressed(this::handleKeyPressed);
		
		
		Pane pane = new Pane(new Rectangle(100 , 100 , Color.RED));
		pane.setMinSize(100 , 100);
		pane.setMaxSize(100 , 100);
		pane.setPrefSize(100 , 100);
		
		this.setTop(infoBar);
		this.setCenter(canvas);
		this.setLeft(toolbar);
		this.setRight(pane);
		
		
	}
	
	
	private void handleKeyPressed(KeyEvent event) {
		if(event.isShiftDown()){
			if(event.getCode() == KeyCode.D){
				toolbar.getSwitchToggle().switchOnProperty().set(true);
				editorViewModel.changeCellState(CellState.ALIVE);
			}
			else if(event.getCode() == KeyCode.E){
				toolbar.getSwitchToggle().switchOnProperty().set(false);
				editorViewModel.changeCellState(CellState.DEAD);
				
			}
		}
		
		
	}
	
	
}
