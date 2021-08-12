package com.ahmadi.command;


import com.ahmadi.states.EditorComponentState;
import com.ahmadi.states.RegistryState;
import com.ahmadi.states.State;

import java.util.Stack;

public class CommandExecutor {
	private final RegistryState registryState;
	private final Stack<UndoableCommand> undoCommandsStack;
	
	public CommandExecutor(RegistryState registryState) {
		this.undoCommandsStack = new Stack<>();
		this.registryState = registryState;
	}
	
	public void execute(Command command){
		State state = (State) registryState.getState(command.getStateClass());
		command.execute(state);
	}
	
	public void execute(UndoableCommand command){
		EditorComponentState state = registryState.getState(command.getStateClass());
		command.execute(state);
		undoCommandsStack.add(command);
	}
	
	public void undo(){
		if(!undoCommandsStack.isEmpty()){
			UndoableCommand command = undoCommandsStack.pop();
			EditorComponentState state = registryState.getState(command.getStateClass());
			state.getEditBoardProperty().setValue(state.getEditBoardProperty().getValue());
			command.undo(state);
		}
		
	}
	
	
}
