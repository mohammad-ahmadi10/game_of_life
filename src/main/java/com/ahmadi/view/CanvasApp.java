package com.ahmadi.view;

import com.ahmadi.command.CommandExecutor;
import com.ahmadi.events.EditorMouseEvent;
import com.ahmadi.states.CellState;
import com.ahmadi.utils.eventbus.Event;
import com.ahmadi.utils.eventbus.Eventbus;
import com.ahmadi.events.CellStateEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;


public class CanvasApp extends BorderPane {
	
	private final Eventbus eventbus;
	private final CommandExecutor executor;
	
	public CanvasApp(Eventbus eventbus , CommandExecutor executor) {
		this.eventbus = eventbus;
		this.executor = executor;
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
		else if(event.isControlDown())
			if(event.getCode() == KeyCode.Z){
				executor.undo();
				
			}
		
		
		
	}
	
	
}
