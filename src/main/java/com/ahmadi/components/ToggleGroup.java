package com.ahmadi.components;

import javafx.scene.Cursor;

public class ToggleGroup extends javafx.scene.control.ToggleGroup {
	
	private ToggleButton newButton = null;
	
	public ToggleGroup() {
		this.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue != null)
				newButton = (ToggleButton) newValue;
			
			// persistenty beeing selected if button is pressed
			if(newButton.equals(oldValue) &&
							!((ToggleButton) oldValue).getText().equalsIgnoreCase("Backward")
					&&      !((ToggleButton) oldValue).getText().equalsIgnoreCase("Forward")){
				oldValue.setSelected(true);
			}
			
		});
	}
}
