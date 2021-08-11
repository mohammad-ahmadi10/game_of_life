package com.ahmadi.logic;

import com.ahmadi.command.CellStateCommand;
import com.ahmadi.command.CommandExecutor;
import com.ahmadi.command.EditorBoardCommand;
import com.ahmadi.command.EditorCommand;
import com.ahmadi.events.CellStateEvent;
import com.ahmadi.states.EditorMouseEventType;
import com.ahmadi.states.EditorComponentState;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.eventbus.Event;

public class Editor {
	
	private final EditorComponentState editorComponentState;
	private final CommandExecutor executor;
	
	
	public EditorComponentState getEditorComponentState() {
		return editorComponentState;
	}
	
	// constructor
	public Editor(EditorComponentState editorComponentState, CommandExecutor executor) {
		
		this.editorComponentState = editorComponentState;
		this.executor = executor;
	}
	
	
	public void handleCursorEvent(Event event) {
		com.ahmadi.events.EditorMouseEvent cursorEvent = (com.ahmadi.events.EditorMouseEvent) event;
		var x = cursorEvent.getCursorPosition().getX();
		var y = cursorEvent.getCursorPosition().getY();
		EditorMouseEventType state = cursorEvent.getState();
		switch (state){
			case CLICK:
			case DRAGGED:
				EditorBoardCommand command = new EditorBoardCommand(new CursorPosition(x ,y) , editorComponentState.getCellStateProperty().getValue());
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
