package com.ahmadi.viewModels;

import com.ahmadi.logic.Editor;
import com.ahmadi.logic.Simulator;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.SimulationState;
import com.ahmadi.states.SimulatorState;
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
	
	
	public void handleSimState(SimulatorState simulationState) {
		
		switch (simulationState){
			case STOP:{
				editor.getEditorState().getEditBoardProperty().setValue(editor.getEditorState().getEditBoardProperty().getValue());
				editor.getEditorState().getEditBoardProperty().getValue().resetCellState(editor.getEditorState().getEditBoardProperty().getValue().getGrid());
				simulator.getSimBoardProperty().setValue(editor.getEditorState().getEditBoardProperty().getValue());
			}
			break;
			
			case STEP:
				editor.getEditorState().getEditBoardProperty().setValue(simulator.getSimBoardProperty().getValue());
				simulator.getSimulation().setBoard(simulator.getSimBoardProperty().getValue());
				break;
			case START:
				simulator.getSimulation().setBoard(simulator.getSimBoardProperty().getValue());
				break;
		}
		
		
		
		
		
	}
}
