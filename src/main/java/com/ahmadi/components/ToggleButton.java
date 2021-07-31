package com.ahmadi.components;


import javafx.geometry.Pos;
import javafx.scene.Cursor;

public class ToggleButton extends javafx.scene.control.ToggleButton {
	
	public ToggleButton(String text) {
		super(text);
		this.getStyleClass().add("cost_button");
		this.setPrefSize(140 ,30);
		this.setAlignment(Pos.CENTER);
		this.setCursor(Cursor.HAND);
	}
	
	public ToggleButton(String text , String className) {
		super(text);
		this.getStyleClass().add(className);
		this.setPrefSize(140, 30);
		this.setAlignment(Pos.CENTER);
		this.setCursor(Cursor.HAND);
		
	}
	
	
}
