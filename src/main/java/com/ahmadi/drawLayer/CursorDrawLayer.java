package com.ahmadi.drawLayer;

import com.ahmadi.states.CanvasComponentState;
import com.ahmadi.utils.CursorPosition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CursorDrawLayer extends DrawLayer{
	
	CanvasComponentState state;
	
	public CursorDrawLayer(CanvasComponentState state) {
		this.state = state;
		
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		
		Boolean isEditing = state.getIsEditing().getValue();
		Boolean isMouseOutOfCanvas = state.getIsMouseOutOfCanvas().getValue();
		Boolean isAlive = state.getIsAlive().getValue();
		CursorPosition cursorPos = state.getCursorPositionProperty().getValue();
		
		// Cursor hover draw
		if(!isMouseOutOfCanvas && isEditing){
			if(isAlive)
				gc.setStroke(Color.GREEN);
			else gc.setStroke(Color.SADDLEBROWN);
			gc.setLineWidth(0.07f);
		}
		else{
			gc.setStroke(Color.LIGHTGREY);
		}
		
		if(cursorPos != null)
			gc.strokeRect( cursorPos.getX(), cursorPos.getY() , 1,1);
		// Ende Cursor hover draw
	}
	
	@Override
	public int getLayerIndex() {
		return 9;
	}
}
