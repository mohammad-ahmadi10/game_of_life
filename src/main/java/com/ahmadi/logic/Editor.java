package com.ahmadi.logic;

import com.ahmadi.command.CellStateCommand;
import com.ahmadi.command.CommandExecutor;
import com.ahmadi.command.EditorBoardCommand;
import com.ahmadi.command.EditorCommand;
import com.ahmadi.events.CellStateEvent;
import com.ahmadi.events.EditorMouseEvent;
import com.ahmadi.states.EditorMouseState;
import com.ahmadi.states.EditorState;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.eventbus.Event;

public class Editor {
	
	private final EditorState editorState;
	private final CommandExecutor executor;
	
	
	public EditorState getEditorState() {
		return editorState;
	}
	
	// constructor
	public Editor(EditorState editorState, CommandExecutor executor) {
		
		this.editorState = editorState;
		this.executor = executor;
	}
	
	
	public void handleCursorEvent(Event event) {
		EditorMouseEvent cursorEvent = (EditorMouseEvent) event;
		var x = cursorEvent.getCursorPosition().getX();
		var y = cursorEvent.getCursorPosition().getY();
		EditorMouseState state = cursorEvent.getState();
		switch (state){
			case CLICK:
			case DRAGGED:
				EditorBoardCommand command = new EditorBoardCommand(new CursorPosition(x ,y) , editorState.getCellStateProperty().getValue());
				executor.execute(command);
				break;
			case EXIT:
				handleNewCursorPosition(0 , 0);
				break;
		}
		
		if(x == -1) x = 0;
		if(y == -1) y = 0;
		handleNewCursorPosition(x, y);
	}
	
	private void handleNewCursorPosition(int x, int y){
		EditorCommand command = state ->{
			state.getCursorProperty().setValue(new CursorPosition(x, y));
			state.getEditBoardProperty().setValue(state.getEditBoardProperty().getValue());
		};
		executor.execute(command);
	}
	
	
	public void handleNewCellState(Event event) {
		CellStateEvent cellState = (CellStateEvent) event;
		
		CellStateCommand command = new CellStateCommand(cellState.getCellState());
		executor.execute(command);
	}
	
	
}
