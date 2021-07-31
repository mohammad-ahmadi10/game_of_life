package com.ahmadi;

import javafx.beans.property.SimpleIntegerProperty;

public class SharedVariable {
	public static SimpleIntegerProperty RESOLUTION = new SimpleIntegerProperty(30);
	public static SimpleIntegerProperty CANVAS_WIDTH = new SimpleIntegerProperty(600);
	public static SimpleIntegerProperty CANVAS_HEIGHT = new SimpleIntegerProperty(600);
	public static SimpleIntegerProperty APP_WIDTH = new SimpleIntegerProperty(1280);
	public static SimpleIntegerProperty APP_HEIGHT = new SimpleIntegerProperty(720);
	public static SimpleIntegerProperty BORD_WIDTH = new SimpleIntegerProperty((CANVAS_WIDTH.getValue() / RESOLUTION.getValue() ));
	public static SimpleIntegerProperty BORD_HEIGHT = new SimpleIntegerProperty( CANVAS_HEIGHT.getValue() / RESOLUTION.getValue());
}
