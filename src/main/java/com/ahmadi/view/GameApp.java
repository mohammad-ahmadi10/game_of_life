package com.ahmadi.view;

import com.ahmadi.command.CommandExecutor;
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
import com.ahmadi.states.EditorState;
import com.ahmadi.states.RegistryState;
import com.ahmadi.states.SimulationState;
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
		
		EditorState editorState = new EditorState(board);
		SimulationState simulationState = new SimulationState(board);
		
		registryState.register(EditorState.class , editorState);
		registryState.register(SimulationState.class , simulationState);
		
		
		Simulation simulation = new Simulation(standardRules , eventbus , simulationState, executor);
		
		// logic
		ApplicationStateManager applicationStateManager = new ApplicationStateManager();
		Editor editor = new Editor(editorState , executor);
		Simulator simulator = new Simulator(simulation);
		
		// viewModels
		BoardViewModel boardViewModel = new BoardViewModel(editor, simulator);
		InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
		
		
		// views
		Canvas canvas = new Canvas(eventbus);
		InfoBar infoBar =  new InfoBar(infoBarViewModel);
		GridPane gridPane = new GridPane(canvas);
		ToolBar toolbar  = new ToolBar(eventbus);
		CanvasApp canvasApp = new CanvasApp(eventbus);
		
		canvasApp.setTop(infoBar);
		canvasApp.setCenter(gridPane);
		canvasApp.setLeft(toolbar);
		
		
		// setting init values
		simulator.getSimBoardProperty().setValue(editorState.getEditBoardProperty().getValue());
		
		
		// events fire
		eventbus.subscribeTo(EventSimulator.class , simulator::handleEvent);
		eventbus.subscribeTo(ApplicationEvent.class, applicationStateManager::handleEvent);
		eventbus.subscribeTo(EditorMouseEvent.class , editor::handleCursorEvent);
		eventbus.subscribeTo(CellStateEvent.class , editor::handleNewCellState);
		eventbus.subscribeTo(CellStateEvent.class , toolbar::handleNewCellState);
		
		
		// subscriptions infoBar
		applicationStateManager.getAppStateProperty().subscribe(infoBarViewModel.getAppState()::setValue);
		editorState.getCellStateProperty().subscribe(infoBarViewModel.getCellProperty()::setValue);
		editorState.getCursorProperty().subscribe(infoBarViewModel.getCursorProperty()::setValue);
		
		
		// subscriptions boardViewModel and Editor && simulation && simulator
		editorState.getEditBoardProperty().subscribe(boardViewModel.getBoardProperty()::setValue);
		simulator.getSimBoardProperty().subscribe(boardViewModel.getBoardProperty()::setValue);
		simulator.getSimBoardProperty().subscribe(editorState.getEditBoardProperty()::setValue);
		simulationState.getBoardPro().subscribe(simulator.getSimBoardProperty()::setValue);
		simulator.getSimStateProperty().subscribe(simulation::handleNewSimState);
		
		
		
		// subscriptions gridPane && boardViewModel
		boardViewModel.getBoardProperty().subscribe(gridPane::handleNewBoard);
		boardViewModel.getBoardProperty().setValue(board);
		applicationStateManager.getAppStateProperty().subscribe(gridPane::handleAppStateChange);
		editorState.getCellStateProperty().subscribe(gridPane::handleNewCellState);
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
