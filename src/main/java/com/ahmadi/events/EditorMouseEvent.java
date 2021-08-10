package com.ahmadi.events;

import com.ahmadi.states.EditorMouseState;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.eventbus.Event;

public class EditorMouseEvent extends Event {
	
	private final CursorPosition cursorPosition;
	private final EditorMouseState state;
	
	public EditorMouseEvent(CursorPosition cursorPosition, EditorMouseState state) {
		this.cursorPosition = cursorPosition;
		this.state = state;
	}
	
	public EditorMouseState getState() {
		return state;
	}
	
	public CursorPosition getCursorPosition() {
		return cursorPosition;
	}
}
