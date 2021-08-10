package com.ahmadi.view;

import com.ahmadi.components.ToggleButton;
import com.ahmadi.states.CellState;
import com.ahmadi.states.SimulationState;
import com.ahmadi.states.SimulatorState;
import com.ahmadi.utils.eventbus.Event;
import com.ahmadi.utils.eventbus.Eventbus;
import com.ahmadi.events.CellStateEvent;
import com.ahmadi.events.EventSimulator;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.CostumEvent;
import org.example.SwitchToggle;


public class ToolBar extends VBox {
	
	
	private final Eventbus eventbus;
	
	private final ToggleButton play;
	private final SwitchToggle switchToggle = SwitchToggle.toggleBuilder("drawState");
	
	Image playImg = new Image("com/ahmadi/play.png");
	Image pauseImg = new Image("com/ahmadi/pause.png");
	ImageView imageView = new ImageView();
	
	public SwitchToggle getSwitchToggle() {
		return switchToggle;
	}
	private boolean isSimulating;

	
	public ToolBar(Eventbus eventbus) {
		this.eventbus = eventbus;
		
		Button nextStep = new Button();
		nextStep.setGraphic(new ImageView(new Image("com/ahmadi/next.png")));
		nextStep.setOnAction(this::handleNextStep);
		nextStep.getStyleClass().add("cost_button");
		nextStep.setCursor(Cursor.HAND);
		
		ToggleButton reset = new ToggleButton("Reset", "reset_btn");
		reset.setOnAction(this::handleReset);
		reset.setSelected(true);
		
		
		switchToggle.setTexts("Draw" , "Erase");
		switchToggle.setBackgroundWidth(80);
		switchToggle.setOnToggle(this::handleOnTogglePressed);
		play = new ToggleButton();
		play.setOnAction(event -> {
			isSimulating = !isSimulating;
			handlePlayPause();
		});
		
		imageView.setImage(playImg);
		play.setGraphic(imageView);
		
		
		
		VBox vbox = new VBox(10);
		vbox.setTranslateY(100);
		vbox.getChildren().addAll(play , nextStep , reset);
		vbox.setAlignment(Pos.CENTER);
		
		switchToggle.getChildren().forEach(child -> child.setTranslateX(15));
		
		this.getChildren().addAll(switchToggle , vbox);
		
		this.setPrefSize(110 , 100);
		this.setMinSize(110 ,100);
		this.setMaxSize(110 ,100);
	}
	
	private void handlePlayPause() {
		if(isSimulating)
			 handlePlay();
		else handlePause();
	}
	
	
	private void handleOnTogglePressed(CostumEvent costumEvent) {
		if(costumEvent.isOn())
				handleDraw();
		else    handleErase();
		
	}
	
	private void handlePause() {
		imageView.setImage(playImg);
		play.setGraphic(imageView);
		eventbus.emitEvent(new EventSimulator(SimulatorState.PAUSE));
	}
	
	
	
	
	private void handleDraw() {
		eventbus.emitEvent(new CellStateEvent(CellState.ALIVE));
	}
	private void handleErase() {
		eventbus.emitEvent(new CellStateEvent(CellState.DEAD));
	}
	
	private void handlePlay() {
		imageView.setImage(pauseImg);
		play.setGraphic(imageView);
		
		eventbus.emitEvent(new EventSimulator(SimulatorState.START));
	}
	
	private void handleNextStep(ActionEvent event) {
		isSimulating = false;
		imageView.setImage(playImg);
		play.setGraphic(imageView);
		play.setSelected(false);
		
		eventbus.emitEvent(new EventSimulator(SimulatorState.STEP));
	}
	
	
	private void handleReset(ActionEvent event) {
		isSimulating = false;
		imageView.setImage(playImg);
		play.setGraphic(imageView);
		play.setSelected(false);
		
		eventbus.emitEvent(new EventSimulator(SimulatorState.STOP));
	}
	
	
	public void handleNewCellState(Event event) {
		CellStateEvent cellState = (CellStateEvent) event;
		getSwitchToggle().switchOnProperty().set(cellState.getCellState().equals(CellState.ALIVE));
	}
}
