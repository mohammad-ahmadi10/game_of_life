package com.ahmadi.viewModels;

import com.ahmadi.states.ApplicationState;
import com.ahmadi.states.CellState;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.Property;

public class InfoBarViewModel {
	private final Property<CursorPosition> cursorProperty = new Property<>();
	private final Property<CellState> cellProperty = new Property<>();
	private final Property<ApplicationState> appState = new Property<>();
	
	public Property<CursorPosition> getCursorProperty() {
		return cursorProperty;
	}
	
	public Property<CellState> getCellProperty() {
		return cellProperty;
	}
	
	public Property<ApplicationState> getAppState() {
		return appState;
	}
}
