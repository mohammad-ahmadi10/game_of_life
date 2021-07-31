package com.ahmadi.viewModels;

import java.util.ArrayList;
import java.util.List;

public class ApplicationViewModel {
	private ApplicationState currentState;
	private final List<StateListener<ApplicationState>> listenerList;
	
	public ApplicationViewModel(ApplicationState currentState) {
		this.currentState = currentState;
		listenerList = new ArrayList<>();
	}
	
	public<T> void addListenerToList(StateListener<ApplicationState> listener){
		listenerList.add(listener);
	}
	
	
	
	// after State Change  every Listener should receive the newState
	public void setCurrentState(ApplicationState newState) {
		this.currentState = newState;
		notifyAllListener();
	}
	
	private void notifyAllListener() {
		listenerList.forEach(listener -> {
			try {
				listener.handle(this.currentState);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}
