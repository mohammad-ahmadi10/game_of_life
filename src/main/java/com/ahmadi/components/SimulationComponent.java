package com.ahmadi.components;

import com.ahmadi.model.Simulation;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.SimulationComponentState;
import com.ahmadi.states.SimulatorComponentState;

public class SimulationComponent implements Component{
	
	
	@Override
	public void initComponent(AppContext context) {
		SimulationComponentState simulationComponentState = context.getRegistryState().getState(SimulationComponentState.class);
		SimulatorComponentState simulatorComponentState = context.getRegistryState().getState(SimulatorComponentState.class);
		
		Simulation simulation = new Simulation(context.getEventbus() , simulationComponentState, context.getExecutor());
		simulationComponentState.getBoardPro().subscribe(simulatorComponentState.getSimBoardProperty()::setValue);
		simulatorComponentState.getSimStateProperty().subscribe(simulation::handleNewSimState);
		
		
	}
	
	@Override
	public void initState(AppContext context) {
		Board board = context.getBoard();
		SimulationComponentState simulationComponentState = new SimulationComponentState(board);
		context.getRegistryState().register(SimulationComponentState.class , simulationComponentState);
	}
}
