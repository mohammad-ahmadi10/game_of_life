package com.ahmadi;

import com.ahmadi.interfaces.Board;
import com.ahmadi.interfaces.RuleSets;
import com.ahmadi.model.CellState;
import com.ahmadi.model.StandardBoard;
import com.ahmadi.viewModels.ApplicationState;
import com.ahmadi.viewModels.ApplicationViewModel;
import com.ahmadi.viewModels.SimulationViewModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Simulation {
	
	
	private final Timeline timeline_forward = new Timeline();
	private final Timeline timeline_backward = new Timeline();
	private Board simBoard;
	
	
	private final SimulationViewModel simulationViewModel;
	private final ApplicationViewModel applicationViewModel;
	private final RuleSets rules;
	
	public Simulation(RuleSets rules , SimulationViewModel simulationViewModel , ApplicationViewModel applicationViewModel) {
		this.rules = rules;
		this.simBoard = simulationViewModel.getSimBoard();
		this.simulationViewModel = simulationViewModel;
		this.applicationViewModel = applicationViewModel;
		
		
		
		KeyFrame keyFrame_forward = new KeyFrame(Duration.millis(250), event -> handleNextstep());
		timeline_forward.getKeyFrames().add(keyFrame_forward);
		timeline_forward.setCycleCount(Timeline.INDEFINITE);
		
		KeyFrame keyFrame_backward = new KeyFrame(Duration.millis(250), event -> handlePrevStep());
		timeline_backward.getKeyFrames().add(keyFrame_backward);
		timeline_backward.setCycleCount(Timeline.INDEFINITE);
		
		
		
	}
	
	
	
	
	public void handleNewSimState(SimulationState state) throws InterruptedException {
		switch (state){
			case START_FORWARD:
				if(timeline_backward.getStatus().equals(Animation.Status.RUNNING))
					timeline_backward.stop();
				this.simBoard = simulationViewModel.getSimBoard();
				simulationViewModel.isSimulattingProperty().set(true);
				Thread.sleep(200);
				timeline_forward.play();
				break;
			case START_BACKWARD:
				if(timeline_forward.getStatus().equals(Animation.Status.RUNNING))
					timeline_forward.stop();
				simulationViewModel.isSimulattingProperty().set(true);
				Thread.sleep(200);
				timeline_backward.play();
				break;
			case PAUSE:
				timeline_forward.pause();
				timeline_backward.pause();
				simulationViewModel.isSimulattingProperty().set(false);
				break;
			case STOP:
				timeline_forward.stop();
				timeline_backward.stop();
				this.simBoard = null;
				
				break;
			case STEP:
				timeline_forward.stop();
				timeline_backward.stop();
				
				this.simBoard = simulationViewModel.getSimBoard();
				handleNextstep();
				break;
			case PREVISE_BOARD:
				timeline_forward.stop();
				timeline_backward.stop();
				
				handlePrevStep();
				break;
		}
		
	}
	
	
	private void handlePrevStep() {
		if(!simulationViewModel.getAllPrevBoardList().empty()) {
			Board prevBoard = simulationViewModel.getAllPrevBoardList().pop();
			simulationViewModel.setSimBoard(prevBoard);
		}
		if(simulationViewModel.getAllPrevBoardList().empty()){
			applicationViewModel.setCurrentState(ApplicationState.EDITING);
			timeline_backward.stop();
			simulationViewModel.isPrevBoardListEmptyProperty().set(true);
			simulationViewModel.isSimulattingProperty().set(false);
		}
	}
	
	private void handleNextstep(){
		if(timeline_backward.getStatus().equals(Animation.Status.RUNNING))
			timeline_backward.stop();
		
		//push newBoard and check if the same board is pushed
		simulationViewModel.getAllPrevBoardList().push(simulationViewModel.getSimBoard());
		if(simulationViewModel.isPrevBoardListEmptyProperty().getValue())
			simulationViewModel.isPrevBoardListEmptyProperty().set(false);
		
		int index1 = simulationViewModel.getAllPrevBoardList().size() -1;
		int index2 = simulationViewModel.getAllPrevBoardList().size() -2;
		if(index1 > 0 && index2 > 0 && simulationViewModel.getAllPrevBoardList().get(index1).isSameBoard(simulationViewModel.getAllPrevBoardList().get(index2)))
			simulationViewModel.getAllPrevBoardList().pop();
			
		
		nextStep();
		
		if(this.getBoard().isSameBoard(simulationViewModel.getSimBoard())){
			simulationViewModel.setSimulationState(SimulationState.STOP);
			applicationViewModel.setCurrentState(ApplicationState.EDITING);
			simulationViewModel.isSimulattingProperty().set(false);
		}
		
		
		if(this.getBoard() != null)
			simulationViewModel.setSimBoard(this.getBoard());
	}
	
	
	public void nextStep(){
		this.simBoard.resize(SharedVariable.BORD_WIDTH.getValue() , SharedVariable.BORD_HEIGHT.getValue());
		
		
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
}
