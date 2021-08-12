package com.ahmadi.model;

import com.ahmadi.command.CommandExecutor;
import com.ahmadi.command.SimulationCommand;
import com.ahmadi.events.ApplicationEvent;
import com.ahmadi.events.EventSimulator;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.model.interfaces.RuleSets;
import com.ahmadi.states.ApplicationStateType;
import com.ahmadi.states.CellState;
import com.ahmadi.states.SimulationComponentState;
import com.ahmadi.states.SimulatorEventType;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.eventbus.Eventbus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulation {
	
	
	private final Timeline timeline_forward = new Timeline();
	private Board simBoard;
	private final Eventbus eventbus;
	private final SimulationComponentState state;
	private final CommandExecutor executor;
	private final RuleSets rules;
	
	
	
	
	
	public Simulation(Eventbus eventbus , SimulationComponentState state , CommandExecutor executor) {
		this.rules = new StandardRules();
		this.eventbus = eventbus;
		this.state = state;
		this.executor = executor;
		state.getBoardPro().subscribe(this::setBoard);
		
		KeyFrame keyFrame_forward = new KeyFrame(Duration.millis(50), event -> handleNextstep());
		timeline_forward.getKeyFrames().add(keyFrame_forward);
		timeline_forward.setCycleCount(Timeline.INDEFINITE);
	}
	
	
	
	public void handleNewSimState(SimulatorEventType state) {
		switch (state){
			case START:
				eventbus.emitEvent(new ApplicationEvent(ApplicationStateType.SIMULATING));
				timeline_forward.play();
				break;
			case PAUSE:
				timeline_forward.pause();
				eventbus.emitEvent(new ApplicationEvent(ApplicationStateType.EDITING));
				break;
			case STOP:
				eventbus.emitEvent(new ApplicationEvent(ApplicationStateType.EDITING));
				timeline_forward.stop();
				break;
			case STEP:
				timeline_forward.stop();
				eventbus.emitEvent(new ApplicationEvent(ApplicationStateType.SIMULATING));
				handleNextstep();
				eventbus.emitEvent(new ApplicationEvent(ApplicationStateType.EDITING));
				break;
		}
		
	}
	
	
	private void handleNextstep(){
		nextStep();
		
		if(this.getBoard().isSameBoard(state.getBoardPro().getValue())){
			eventbus.emitEvent(new EventSimulator(SimulatorEventType.STOP));
		}
		
		
		if(this.getBoard() != null)
		{
			SimulationCommand command = state -> state.getBoardPro().setValue(this.simBoard);
			executor.execute(command);
		}
	}
	
	
	public void nextStep(){
		
		Board board = new StandardBoard(simBoard.getWidth() , simBoard.getHeight() , "copy");
		Board.copyBoard(this.simBoard , board);
		
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				CellState nextState = rules.calcNextGen( i, j ,this.simBoard );
				board.setState(new CursorPosition(i , j), nextState);
			}
		}
		this.simBoard = board;
	}
	
	
	public Board getBoard() {
		return this.simBoard;
	}
	
	public void setBoard(Board board) {
		this.simBoard = board;
	}
	
}
