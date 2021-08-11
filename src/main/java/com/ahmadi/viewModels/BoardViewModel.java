package com.ahmadi.viewModels;

import com.ahmadi.logic.Editor;
import com.ahmadi.logic.Simulator;
import com.ahmadi.states.BoardComponentState;
import com.ahmadi.states.SimulatorEventType;


public class BoardViewModel {
	
	private final BoardComponentState state;
	private final Editor editor;
	private final Simulator simulator;
	
	public BoardViewModel(Editor editor, Simulator simulator , BoardComponentState state) {
		this.editor = editor;
		this.simulator = simulator;
		this.state = state;
	}
	
	public BoardComponentState getState() {
		return state;
	}
	
	public void handleSimState(SimulatorEventType simulationState) {
		
		switch (simulationState){
			case STOP:{
				editor.getEditorComponentState().getEditBoardProperty().setValue(editor.getEditorComponentState().getEditBoardProperty().getValue());
				editor.getEditorComponentState().getEditBoardProperty().getValue().resetCellState(editor.getEditorComponentState().getEditBoardProperty().getValue().getGrid());
				simulator.getSimBoardProperty().setValue(editor.getEditorComponentState().getEditBoardProperty().getValue());
			}
			break;
			
			case STEP:
				editor.getEditorComponentState().getEditBoardProperty().setValue(simulator.getSimBoardProperty().getValue());
				simulator.getSimulation().setBoard(simulator.getSimBoardProperty().getValue());
				break;
			case START:
				simulator.getSimulation().setBoard(simulator.getSimBoardProperty().getValue());
				break;
		}
		
		
		
		
		
	}
}
