package com.ahmadi.view;

import com.ahmadi.states.CellState;
import com.ahmadi.utils.eventbus.Eventbus;
import com.ahmadi.events.CellStateEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;


public class CanvasApp extends BorderPane {
	
	private final Eventbus eventbus;
	public CanvasApp(Eventbus eventbus) {
		this.eventbus = eventbus;
		this.setOnKeyPressed(this::handleKeyPressed);
	}
	
	private void handleKeyPressed(KeyEvent event) {
		if(event.isShiftDown()){
			if(event.getCode() == KeyCode.D){
				eventbus.emitEvent(new CellStateEvent(CellState.ALIVE));
			}
			else if(event.getCode() == KeyCode.E){
				eventbus.emitEvent(new CellStateEvent(CellState.DEAD));
			}
		}
		
		
	}
	
	
}
