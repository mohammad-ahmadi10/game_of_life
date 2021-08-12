package com.ahmadi.components;

import com.ahmadi.events.ApplicationEvent;
import com.ahmadi.states.ApplicationComponentState;

public class ApplicationComponent implements Component{
	
	
	@Override
	public void initComponent(AppContext context) {
		ApplicationComponentState applicationComponentState = context.getRegistryState().getState(ApplicationComponentState.class);
		context.getEventbus().subscribeTo(ApplicationEvent.class, applicationComponentState::handleEvent);
		
	}
	
	@Override
	public void initState(AppContext context) {
		ApplicationComponentState applicationComponentState = new ApplicationComponentState();
		context.getRegistryState().register(ApplicationComponentState.class , applicationComponentState);
	}
}
