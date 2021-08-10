package com.ahmadi.viewModels;

import com.ahmadi.logic.Editor;
import com.ahmadi.logic.Simulator;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.SimulationState;
import com.ahmadi.utils.Property;


public class BoardViewModel {
	
	private final Property<Board> boardProperty;
	private final Editor editor;
	private final Simulator simulator;
	
	public BoardViewModel(Editor editor, Simulator simulator) {
		this.editor = editor;
		this.simulator = simulator;
		this.boardProperty = new Property<>();
	}
	
	public Property<Board> getBoardProperty() {
		return boardProperty;
	}
	
	
	public void handleSimState(SimulationState simulationState) {
		
		switch (simulationState){
			case STOP:{
				editor.getEditBoardProperty().setValue(editor.getEditBoardProperty().getValue());
				editor.getEditBoardProperty().getValue().resetCellState(editor.getEditBoardProperty().getValue().getGrid());
				simulator.getSimBoardProperty().setValue(editor.getEditBoardProperty().getValue());
			}
			break;
			
			case STEP:
				editor.getEditBoardProperty().setValue(simulator.getSimBoardProperty().getValue());
				simulator.getSimulation().setBoard(simulator.getSimBoardProperty().getValue());
				break;
			case START:
				simulator.getSimulation().setBoard(simulator.getSimBoardProperty().getValue());
				break;
		}
		
		
		
		
		
	}
}
