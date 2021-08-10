package com.ahmadi.command;


import com.ahmadi.states.EditorState;

public interface EditorCommand extends Command<EditorState>{
	@Override
	void execute(EditorState state);
	
	@Override
	default Class<EditorState> getStateClass() {
		return EditorState.class;
	}
}
