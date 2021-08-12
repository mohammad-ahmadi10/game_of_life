package com.ahmadi.components;

import com.ahmadi.events.CellStateEvent;
import com.ahmadi.states.ToolBarComponentState;
import com.ahmadi.view.ToolBar;

public class ToolBarComponent implements Component{
	
	
	@Override
	public void initComponent(AppContext context) {
		ToolBarComponentState toolBarComponentState = context.getRegistryState().getState(ToolBarComponentState.class);
		
		context.getEventbus().subscribeTo(CellStateEvent.class , toolBarComponentState::handleNewCellState);
		ToolBar toolbar  = new ToolBar(context.getEventbus() , toolBarComponentState);
		context.getCanvasApp().setLeft(toolbar);
		
		
	}
	
	@Override
	public void initState(AppContext context) {
		ToolBarComponentState toolBarComponentState = new ToolBarComponentState();
		context.getRegistryState().register(ToolBarComponentState.class , toolBarComponentState);
	}
}
