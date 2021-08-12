package com.ahmadi.components;

import com.ahmadi.states.ApplicationComponentState;
import com.ahmadi.states.InfoBarComponentState;
import com.ahmadi.view.InfoBar;

public class InfoBarComponent implements Component{
	
	
	
	@Override
	public void initComponent(AppContext context) {
		InfoBarComponentState infoBarComponentState = context.getRegistryState().getState(InfoBarComponentState.class);
		ApplicationComponentState applicationComponentState = context.getRegistryState().getState(ApplicationComponentState.class);
		
		InfoBar infoBar =  new InfoBar(infoBarComponentState);
		context.getCanvasApp().setTop(infoBar);
		
		applicationComponentState.getAppStateProperty().subscribe(infoBarComponentState.getAppState()::setValue);
		
	
	}
	
	@Override
	public void initState(AppContext context) {
		InfoBarComponentState infoBarComponentState = new InfoBarComponentState();
		context.getRegistryState().register(InfoBarComponentState.class , infoBarComponentState);
	}
}
