package com.ahmadi.components;

import com.ahmadi.events.CellStateEvent;
import com.ahmadi.events.EditorMouseEvent;
import com.ahmadi.logic.Editor;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.BoardComponentState;
import com.ahmadi.states.EditorComponentState;
import com.ahmadi.states.InfoBarComponentState;

public class EditorComponent implements Component{
	
	
	@Override
	public void initComponent(AppContext context) {
		EditorComponentState state = context.getRegistryState().getState(EditorComponentState.class);
		InfoBarComponentState infoBarComponentState = context.getRegistryState().getState(InfoBarComponentState.class);
		BoardComponentState boardComponentState = context.getRegistryState().getState(BoardComponentState.class);
		
		Editor editor = new Editor(state, context.getExecutor());
		
		context.getEventbus().subscribeTo(EditorMouseEvent.class , editor::handleCursorEvent);
		context.getEventbus().subscribeTo(CellStateEvent.class , editor::handleNewCellState);
		
		state.getCellStateProperty().subscribe(infoBarComponentState.getCellProperty()::setValue);
		state.getCursorProperty().subscribe(infoBarComponentState.getCursorProperty()::setValue);
		state.getEditBoardProperty().subscribe(boardComponentState.getBoardProperty()::setValue);
		
		
	}
	
	@Override
	public void initState(AppContext context) {
		Board board = context.getBoard();
		EditorComponentState state = new EditorComponentState(board);
		context.getRegistryState().register(EditorComponentState.class , state);
	}
}
