package com.ahmadi.command;


import com.ahmadi.states.RegistryState;
import com.ahmadi.states.State;

public class CommandExecutor {
	private final RegistryState registryState;
	
	public CommandExecutor(RegistryState registryState) {
		this.registryState = registryState;
	}
	
	public void execute(Command command){
		State state = (State) registryState.getState(command.getStateClass());
		command.execute(state);
	}
	
}
