package com.ahmadi.components;

import com.ahmadi.events.EventSimulator;
import com.ahmadi.logic.Simulator;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.BoardComponentState;
import com.ahmadi.states.EditorComponentState;
import com.ahmadi.states.SimulatorComponentState;

public class SimulatorComponent implements Component{
	
	
	
	@Override
	public void initComponent(AppContext context) {
		SimulatorComponentState state = context.getRegistryState().getState(SimulatorComponentState.class);
		EditorComponentState editorComponentState = context.getRegistryState().getState(EditorComponentState.class);
		BoardComponentState boardComponentState = context.getRegistryState().getState(BoardComponentState.class);
		
		Simulator simulator = new Simulator(state);
		context.getEventbus().subscribeTo(EventSimulator.class , simulator::handleEvent);
		state.getSimBoardProperty().setValue(editorComponentState.getEditBoardProperty().getValue());
		
		state.getSimBoardProperty().subscribe(boardComponentState.getBoardProperty()::setValue);
		state.getSimBoardProperty().subscribe(editorComponentState.getEditBoardProperty()::setValue);
		
		
	}
	
	@Override
	public void initState(AppContext context) {
		Board board = context.getBoard();
		SimulatorComponentState simulatorComponentState = new SimulatorComponentState(board);
		context.getRegistryState().register(SimulatorComponentState.class , simulatorComponentState);
	}
}
