package com.ahmadi.model;

import com.ahmadi.model.abstracts.Board;
import com.ahmadi.model.interfaces.RuleSets;
import com.ahmadi.states.ApplicationState;
import com.ahmadi.states.CellState;
import com.ahmadi.states.SimulationState;
import com.ahmadi.utils.Property;
import com.ahmadi.utils.eventbus.Eventbus;
import com.ahmadi.events.ApplicationEvent;
import com.ahmadi.events.EventSimulator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulation {
	
	
	private final Timeline timeline_forward = new Timeline();
	private Board simBoard;
	private final Eventbus eventbus;
	private final RuleSets rules;
	private final Property<Board> simProperty;
	
	
	public Property<Board> getSimProperty() {
		return simProperty;
	}
	
	
	
	public Simulation(RuleSets rules, Eventbus eventbus) {
		this.rules = rules;
		this.eventbus = eventbus;
		this.simProperty = new Property<>();
		
		KeyFrame keyFrame_forward = new KeyFrame(Duration.millis(50), event -> handleNextstep());
		timeline_forward.getKeyFrames().add(keyFrame_forward);
		timeline_forward.setCycleCount(Timeline.INDEFINITE);
	}
	
	
	public void handleNewSimState(SimulationState state) {
		switch (state){
			case START:
				eventbus.emitEvent(new ApplicationEvent(ApplicationState.SIMULATING));
				timeline_forward.play();
				break;
			case PAUSE:
				timeline_forward.pause();
				eventbus.emitEvent(new ApplicationEvent(ApplicationState.EDITING));
				break;
			case STOP:
				eventbus.emitEvent(new ApplicationEvent(ApplicationState.EDITING));
				timeline_forward.stop();
				break;
			case STEP:
				timeline_forward.stop();
				eventbus.emitEvent(new ApplicationEvent(ApplicationState.SIMULATING));
				handleNextstep();
				eventbus.emitEvent(new ApplicationEvent(ApplicationState.EDITING));
				break;
		}
		
	}
	
	
	private void handleNextstep(){
		nextStep();
		
		if(this.getBoard().isSameBoard(simProperty.getValue())){
			eventbus.emitEvent(new EventSimulator(SimulationState.STOP));
		}
		
		
		if(this.getBoard() != null)
			simProperty.setValue(this.getBoard());
			//simulationViewModel.getSimBoardProperty().setValue(this.getBoard());
	}
	
	
	public void nextStep(){
		
		Board board = new StandardBoard(simBoard.getWidth() , simBoard.getHeight() , "copy");
		Board.copyBoard(this.simBoard , board);
		
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				CellState nextState = rules.calcNextGen( i, j ,this.simBoard );
				board.setState(i , j , nextState);
			}
		}
		this.simBoard = board;
	}
	
	
	public Board getBoard() {
		return this.simBoard;
	}
	
	public void setBoard(Board board) {
		this.simBoard = board;
		simProperty.setValue(this.simBoard);
	}
}
