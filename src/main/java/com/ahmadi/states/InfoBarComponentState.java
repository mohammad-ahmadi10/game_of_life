package com.ahmadi.states;

import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.Property;

public class InfoBarComponentState implements State{
	private final Property<CursorPosition> cursorProperty = new Property<>();
	private final Property<CellState> cellProperty = new Property<>();
	private final Property<ApplicationStateType> appState = new Property<>();
	
	public Property<CursorPosition> getCursorProperty() {
		return cursorProperty;
	}
	
	public Property<CellState> getCellProperty() {
		return cellProperty;
	}
	
	public Property<ApplicationStateType> getAppState() {
		return appState;
	}
}
