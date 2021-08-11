package com.ahmadi.command;


import com.ahmadi.states.SimulationComponentState;

public interface SimulationCommand extends Command<SimulationComponentState>{
	@Override
	void execute(SimulationComponentState state);
	
	
	@Override
	default Class<SimulationComponentState> getStateClass() {
		return SimulationComponentState.class;
	}
}
