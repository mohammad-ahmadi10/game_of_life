package com.ahmadi;

import com.ahmadi.components.ToggleButton;
import com.ahmadi.components.ToggleGroup;
import com.ahmadi.model.CellState;
import com.ahmadi.viewModels.ApplicationState;
import com.ahmadi.viewModels.ApplicationViewModel;
import com.ahmadi.viewModels.EditorViewModel;
import com.ahmadi.viewModels.SimulationViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.CostumEvent;
import org.example.SwitchToggle;


public class ToolBar extends VBox {
	
	
	private final ApplicationViewModel appStateViewModel;
	private final SimulationViewModel simulationViewModel;
	private final EditorViewModel editorViewModel;
	
	private final ToggleButton startForward;
	private final ToggleButton startBackward;
	private final SwitchToggle switchToggle = SwitchToggle.toggleBuilder("drawState");
	
	public SwitchToggle getSwitchToggle() {
		return switchToggle;
	}
	
	
	public ToolBar(ApplicationViewModel appStateViewModel, SimulationViewModel simulationViewModel, EditorViewModel editorViewModel) {
		this.appStateViewModel = appStateViewModel;
		this.simulationViewModel = simulationViewModel;
		this.editorViewModel = editorViewModel;
		
		Button nextStep = new Button("Next");
		nextStep.setOnAction(this::handleNextStep);
		Button previousStep = new Button("previous");
		previousStep.setOnAction(this::handlePrevBoard);
		nextStep.getStyleClass().add("cost_button");
		previousStep.getStyleClass().add("cost_button");
		nextStep.setCursor(Cursor.HAND);
		previousStep.setCursor(Cursor.HAND);
		previousStep.setPrefSize(120 , 30);
		nextStep.setPrefSize(120 , 30);
		
		ToggleButton reset = new ToggleButton("Reset", "reset_btn");
		reset.setOnAction(this::handleReset);
		
		
		switchToggle.setTexts("Draw" , "Erase");
		switchToggle.setBackgroundWidth(120);
		switchToggle.setOnToggle(this::handleOnTogglePressed);
		startForward = new ToggleButton("Forward");
		startForward.setOnAction(this::handleStartForward);
		
		ToggleButton pause = new ToggleButton("pause");
		pause.setOnAction(this::handlePause);
		
		
		startBackward = new ToggleButton("Backward");
		startBackward.setOnAction(this::handleStartBackward);
		
		
		
		
		ToggleGroup simGroup = new ToggleGroup();
		startForward.setToggleGroup(simGroup);
		startBackward.setToggleGroup(simGroup);
		pause.setToggleGroup(simGroup);
		reset.setToggleGroup(simGroup);
		reset.setSelected(true);
		
		
		startBackward.mouseTransparentProperty().bind(simulationViewModel.isPrevBoardListEmptyProperty());
		previousStep.mouseTransparentProperty().bind(simulationViewModel.isPrevBoardListEmptyProperty());
		
		nextStep.mouseTransparentProperty().bind(simulationViewModel.isAllCellDeadProperty());
		startForward.mouseTransparentProperty().bind(simulationViewModel.isAllCellDeadProperty());
		pause.mouseTransparentProperty().bind(Bindings.when(simulationViewModel.isSimulattingProperty()).then(false).otherwise(true));
		
		simulationViewModel.isPrevBoardListEmptyProperty().addListener((observableValue, aBoolean, t1) -> {
			if((simulationViewModel.getSimulationState().equals(SimulationState.START_FORWARD)))
				return;
 			startBackward.setSelected(false);
		});
		
		simulationViewModel.isSimulattingProperty().addListener((observableValue, aBoolean, t1) -> {
			System.out.println(aBoolean);
			
			startForward.setSelected(!aBoolean);
			
		});
		
		
		HBox row1 = generateBox( switchToggle);
		HBox row2 = generateBox(previousStep, nextStep);
		HBox row3 = generateBox( startBackward , startForward);
		HBox row4 = generateBox(reset, pause);
		
		
		this.setSpacing(20);
		this.getChildren().addAll(row1, new Separator() , row2 , row3 , row4);
		
		this.setPrefSize(200 , 100);
		this.setMinSize(200 ,100);
		this.setMaxSize(200 ,100);
	}
	
	private void handleOnTogglePressed(CostumEvent costumEvent) {
		if(costumEvent.isOn())
				handleDraw();
		else    handleErase();
		
	}
	
	private HBox generateBox(Node node){
		HBox hBox = new HBox();
		hBox.getChildren().add(node);
		hBox.setPrefSize(100 ,100);
		hBox.setPadding(new Insets(40 , 0, 0, 0));
		hBox.setAlignment(Pos.CENTER);
		return hBox;
	}
	private HBox generateBox(Node nodeLeft, Node nodeRight) {
		HBox hBox = new HBox();
		hBox.getChildren().addAll(nodeLeft , nodeRight);
		hBox.setSpacing(5);
		hBox.setAlignment(Pos.CENTER);
		return hBox;
	}
	
	
	private void handleStartBackward(ActionEvent event) {
		
		simulationViewModel.setSimulationState(SimulationState.START_BACKWARD);
		appStateViewModel.setCurrentState(ApplicationState.SIMULATING);
	}
	
	private void handlePrevBoard(ActionEvent event) {
		appStateViewModel.setCurrentState(ApplicationState.SIMULATING);
		simulationViewModel.setSimulationState(SimulationState.PREVISE_BOARD);
		
		appStateViewModel.setCurrentState(ApplicationState.EDITING);
		editorViewModel.setEditBoard(simulationViewModel.getSimBoard());
		
	}
	
	private void handlePause(ActionEvent event) {
		simulationViewModel.setSimulationState(SimulationState.STOP);
		editorViewModel.setEditBoard(editorViewModel.getEditBoard());
		appStateViewModel.setCurrentState(ApplicationState.EDITING);
	}
	
	private void handleStartForward(ActionEvent event) {
		simulationViewModel.setSimulationState(SimulationState.START_FORWARD);
		appStateViewModel.setCurrentState(ApplicationState.SIMULATING);
	}
	
	
	
	private void handleDraw() {
		editorViewModel.changeCellState(CellState.ALIVE);
	}
	private void handleErase() {
		editorViewModel.changeCellState(CellState.DEAD);
	}
	private void handleNextStep(ActionEvent event) {
		appStateViewModel.setCurrentState(ApplicationState.SIMULATING);
		simulationViewModel.setSimulationState(SimulationState.STEP);
		appStateViewModel.setCurrentState(ApplicationState.EDITING);
		editorViewModel.setEditBoard(simulationViewModel.getSimBoard());
	}
	
	private void handleReset(ActionEvent event) {
		simulationViewModel.setSimulationState(SimulationState.STOP);

		editorViewModel.getEditBoard().resetCellState(editorViewModel.getEditBoard().getGrid());
		editorViewModel.setEditBoard(editorViewModel.getEditBoard());
		//simulationViewModel.setSimBoard(editorViewModel.getEditBoard());
		simulationViewModel.setIsAllCellDead(editorViewModel.getEditBoard().isGridEmpty());
		simulationViewModel.isPrevBoardListEmptyProperty().set(true);
		simulationViewModel.isSimulattingProperty().set(false);
		appStateViewModel.setCurrentState(ApplicationState.EDITING);
		simulationViewModel.getAllPrevBoardList().clear();
	}
	
	
	
}
