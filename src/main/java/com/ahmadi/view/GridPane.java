package com.ahmadi.view;

import com.ahmadi.drawLayer.DrawLayer;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.CellState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class GridPane extends Pane {
	private final Canvas canvas;
	private final List<DrawLayer> drawLayerList;
	public GridPane(Canvas canvas) {
		this.canvas = canvas;
		this.getChildren().add(canvas);
		drawLayerList = new LinkedList<>();
	}
	
	
	public void handleNewBoard(Board board) {
		draw();
	}
	
	
	public void addDrawLayer(DrawLayer drawLayer){
		drawLayerList.add(drawLayer);
		drawLayerList.sort(Comparator.comparingInt(DrawLayer::getLayerIndex));
	}
	
	// draw on the StandardBoard
	public void draw(){
		GraphicsContext gc =  canvas.getGraphicsContext2D();
		gc.setTransform(canvas.getAffine());
		
		
		// drawing Background
		gc.setFill(Color.LIGHTGREY);
		gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
		
		
		drawLayerList.forEach(drawLayer -> drawLayer.draw(gc));
		
	}
}
