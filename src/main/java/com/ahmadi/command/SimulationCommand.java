package com.ahmadi.command;


import com.ahmadi.states.SimulationState;

public interface SimulationCommand extends Command<SimulationState>{
	@Override
	void execute(SimulationState state);
	
	
	@Override
	default Class<SimulationState> getStateClass() {
		return SimulationState.class;
	}
}
