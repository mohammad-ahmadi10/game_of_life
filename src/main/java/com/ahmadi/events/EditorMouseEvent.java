package com.ahmadi.events;

import com.ahmadi.states.EditorMouseEventType;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.eventbus.Event;

public class EditorMouseEvent extends Event {
	
	private final CursorPosition cursorPosition;
	private final EditorMouseEventType state;
	
	public EditorMouseEvent(CursorPosition cursorPosition, EditorMouseEventType state) {
		this.cursorPosition = cursorPosition;
		this.state = state;
	}
	
	
	
	
	public EditorMouseEventType getState() {
		return state;
	}
	
	public CursorPosition getCursorPosition() {
		return cursorPosition;
	}
}
