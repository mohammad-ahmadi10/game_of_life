package com.ahmadi.drawLayer;

import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;

public abstract class DrawLayer {
	private final List<InvalidationListener> listenerList = new LinkedList<>();
	
	
	
	public abstract void draw(GraphicsContext gc);
	public abstract int getLayerIndex();
	
	public void addListener(InvalidationListener listener){
		listenerList.add(listener);
	}
	
	public void invalidate(){ listenerList.forEach(InvalidationListener::onInvalidated);}
	
}
