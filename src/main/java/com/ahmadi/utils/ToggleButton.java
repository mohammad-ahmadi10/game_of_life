package com.ahmadi.utils;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	public ToggleButton() {
		super();
		this.getStyleClass().add("cost_button");
		this.setAlignment(Pos.CENTER);
		this.setCursor(Cursor.HAND);
	}
	
	public ToggleButton(String text , String className) {
		super(text);
		this.getStyleClass().add(className);
		this.setAlignment(Pos.CENTER);
		this.setCursor(Cursor.HAND);
		
	}
	
	
}
