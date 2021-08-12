package com.ahmadi.command;

import com.ahmadi.states.EditorComponentState;

public interface UndoableCommand extends Command<EditorComponentState>{
	
	@Override
	void execute(EditorComponentState state);
	
	@Override
	default Class<EditorComponentState> getStateClass() {
		return EditorComponentState.class;
	}
	
	void undo(EditorComponentState state);
	
}
