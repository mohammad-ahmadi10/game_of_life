package com.ahmadi.view;

import com.ahmadi.command.CommandExecutor;
import com.ahmadi.drawLayer.CellStateDrawLayer;
import com.ahmadi.drawLayer.CursorDrawLayer;
import com.ahmadi.drawLayer.GridDrawLayer;
import com.ahmadi.events.ApplicationEvent;
import com.ahmadi.events.CellStateEvent;
import com.ahmadi.events.EditorMouseEvent;
import com.ahmadi.events.EventSimulator;
import com.ahmadi.logic.*;
import com.ahmadi.model.Simulation;
import com.ahmadi.model.StandardBoard;
import com.ahmadi.model.StandardRules;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.model.interfaces.RuleSets;
import com.ahmadi.states.*;
import com.ahmadi.utils.SharedVariable;
import com.ahmadi.utils.eventbus.Eventbus;
import com.ahmadi.viewModels.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {

		// utils
		Eventbus eventbus = new Eventbus();
		RegistryState registryState = new RegistryState();
		CommandExecutor executor = new CommandExecutor(registryState);
		
		
		
		// models
		Board board = new StandardBoard(SharedVariable.BORD_WIDTH , SharedVariable.BORD_HEIGHT);
		RuleSets standardRules = new StandardRules();
		
		// states
		EditorComponentState editorComponentState = new EditorComponentState(board);
		SimulationComponentState simulationComponentState = new SimulationComponentState(board);
		BoardComponentState boardComponentState = new BoardComponentState(board);
		InfoBarComponentState infoBarComponentState = new InfoBarComponentState();
		CanvasComponentState canvasComponentState = new CanvasComponentState();
		
		registryState.register(EditorComponentState.class , editorComponentState);
		registryState.register(SimulationComponentState.class , simulationComponentState);
		registryState.register(BoardComponentState.class , boardComponentState);
		registryState.register(CanvasComponentState.class , canvasComponentState);
		
		
		Simulation simulation = new Simulation(standardRules , eventbus , simulationComponentState, executor);
		
		// logic
		ApplicationStateManager applicationStateManager = new ApplicationStateManager();
		Editor editor = new Editor(editorComponentState, executor);
		Simulator simulator = new Simulator(simulation);
		
		// viewModels
		BoardViewModel boardViewModel = new BoardViewModel(editor, simulator, boardComponentState);
		
		
		// views
		Canvas canvas = new Canvas(eventbus , canvasComponentState);
		InfoBar infoBar =  new InfoBar(infoBarComponentState);
		GridPane gridPane = new GridPane(canvas);
		ToolBar toolbar  = new ToolBar(eventbus);
		CanvasApp canvasApp = new CanvasApp(eventbus);
		
		canvasApp.setTop(infoBar);
		canvasApp.setCenter(gridPane);
		canvasApp.setLeft(toolbar);
		
		// DrawLayers
		CellStateDrawLayer cellStateDrawLayer = new CellStateDrawLayer(boardComponentState);
		GridDrawLayer gridDrawLayer = new GridDrawLayer(boardComponentState);
		CursorDrawLayer cursorDrawLayer = new CursorDrawLayer(canvasComponentState);
		gridPane.addDrawLayer(cellStateDrawLayer);
		gridPane.addDrawLayer(gridDrawLayer);
		gridPane.addDrawLayer(cursorDrawLayer);
		
		
		// setting init values
		simulator.getSimBoardProperty().setValue(editorComponentState.getEditBoardProperty().getValue());
		
		
		// events fire
		eventbus.subscribeTo(EventSimulator.class , simulator::handleEvent);
		eventbus.subscribeTo(ApplicationEvent.class, applicationStateManager::handleEvent);
		eventbus.subscribeTo(EditorMouseEvent.class , editor::handleCursorEvent);
		eventbus.subscribeTo(CellStateEvent.class , editor::handleNewCellState);
		eventbus.subscribeTo(CellStateEvent.class , toolbar::handleNewCellState);
		eventbus.subscribeTo(CellStateEvent.class , canvasComponentState::handleNewCellState);
		
		// subscriptions infoBar
		applicationStateManager.getAppStateProperty().subscribe(infoBarComponentState.getAppState()::setValue);
		editorComponentState.getCellStateProperty().subscribe(infoBarComponentState.getCellProperty()::setValue);
		editorComponentState.getCursorProperty().subscribe(infoBarComponentState.getCursorProperty()::setValue);
		
		
		// subscriptions boardViewModel and Editor && simulation && simulator
		editorComponentState.getEditBoardProperty().subscribe(boardViewModel.getState().getBoardProperty()::setValue);
		simulator.getSimBoardProperty().subscribe(boardViewModel.getState().getBoardProperty()::setValue);
		simulator.getSimBoardProperty().subscribe(editorComponentState.getEditBoardProperty()::setValue);
		simulationComponentState.getBoardPro().subscribe(simulator.getSimBoardProperty()::setValue);
		simulator.getSimStateProperty().subscribe(simulation::handleNewSimState);
		
		
		
		// subscriptions gridPane && boardViewModel
		boardViewModel.getState().getBoardProperty().subscribe(gridPane::handleNewBoard);
		boardViewModel.getState().getBoardProperty().setValue(board);
		
		applicationStateManager.getAppStateProperty().subscribe(state ->{
			canvasComponentState.getIsEditing().setValue(state == ApplicationState.EDITING);
		});
		
		
		
		simulator.getSimStateProperty().subscribe(boardViewModel::handleSimState);
		
		
		
		
		
		
		Scene scene = new Scene(canvasApp);
		scene.getStylesheets().add(getClass().getResource("gameApp.css").toExternalForm());
		
		
		// setting windows size
		primaryStage.setMinHeight(500);
		primaryStage.setMinWidth(1200);
		primaryStage.setMaxHeight(800);
		primaryStage.setMaxWidth(1500);
		
		primaryStage.setHeight(SharedVariable.APP_HEIGHT);
		primaryStage.setWidth(SharedVariable.APP_WIDTH);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
}
