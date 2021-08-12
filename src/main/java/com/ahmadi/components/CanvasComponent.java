package com.ahmadi.components;

import com.ahmadi.drawLayer.CursorDrawLayer;
import com.ahmadi.states.ApplicationComponentState;
import com.ahmadi.states.ApplicationStateType;
import com.ahmadi.states.CanvasComponentState;
import com.ahmadi.view.Canvas;
import com.ahmadi.view.GridPane;

public class CanvasComponent implements Component{
	
	
	
	@Override
	public void initComponent(AppContext context) {
		CanvasComponentState canvasComponentState = context.getRegistryState().getState(CanvasComponentState.class);
		ApplicationComponentState applicationComponentState = context.getRegistryState().getState(ApplicationComponentState.class);
		
		// views
		Canvas canvas = new Canvas(context.getEventbus() , canvasComponentState);
		GridPane gridPane = new GridPane(canvas);
		context.getCanvasApp().setCenter(gridPane);
		
		
		CursorDrawLayer cursorDrawLayer = new CursorDrawLayer(canvasComponentState);
		gridPane.addDrawLayer(cursorDrawLayer);
		
		
		applicationComponentState.getAppStateProperty().subscribe(state ->{
			canvasComponentState.getIsEditing().setValue(state == ApplicationStateType.EDITING);
		});
		
	}
	
	@Override
	public void initState(AppContext context) {
		CanvasComponentState canvasComponentState = new CanvasComponentState();
		context.getRegistryState().register(CanvasComponentState.class , canvasComponentState);
	}
}
