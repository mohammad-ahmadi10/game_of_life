package com.ahmadi.viewModels;

import com.ahmadi.states.*;


public class BoardViewModel {
	
	private final BoardComponentState state;
	private final SimulationComponentState simulationComponentState;
	private final EditorComponentState editorComponentState;
	private final SimulatorComponentState simulatorComponentState;
	
	public BoardViewModel(EditorComponentState editorComponentState, SimulatorComponentState simulatorComponentState, BoardComponentState state, SimulationComponentState simulationComponentState) {
		this.editorComponentState = editorComponentState;
		this.simulatorComponentState = simulatorComponentState;
		this.state = state;
		this.simulationComponentState = simulationComponentState;
	}
	
	public BoardComponentState getState() {
		return state;
	}
	
	public void handleSimState(SimulatorEventType type) {
		
		switch (type){
			case STOP:{
				editorComponentState.getEditBoardProperty().setValue(editorComponentState.getEditBoardProperty().getValue());
				editorComponentState.getEditBoardProperty().getValue().resetCellState(editorComponentState.getEditBoardProperty().getValue().getGrid());
				simulatorComponentState.getSimBoardProperty().setValue(editorComponentState.getEditBoardProperty().getValue());
			}
			break;
			
			case STEP:
				editorComponentState.getEditBoardProperty().setValue(simulatorComponentState.getSimBoardProperty().getValue());
				simulationComponentState.getBoardPro().setValue(simulatorComponentState.getSimBoardProperty().getValue());
				break;
			case START:
				simulationComponentState.getBoardPro().setValue(simulatorComponentState.getSimBoardProperty().getValue());
				break;
		}
		
		
		
		
		
	}
}
