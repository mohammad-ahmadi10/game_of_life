package com.ahmadi.view;

import com.ahmadi.states.EditorMouseState;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.SharedVariable;
import com.ahmadi.utils.eventbus.Eventbus;
import com.ahmadi.events.EditorMouseEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Affine;

public class Canvas extends javafx.scene.canvas.Canvas {
	private final Affine affTrans;
	
	private boolean isMouseOutOfCanvas = true;
	private final Eventbus eventbus;
	private boolean isEditing = true;
	private CursorPosition cursorPosition;
	
	public CursorPosition getCursorPosition() {
		return cursorPosition;
	}
	
	public void setEditing(boolean editing) {
		isEditing = editing;
	}
	
	public Canvas(Eventbus eventbus) {
		super(SharedVariable.CANVAS_WIDTH , SharedVariable.CANVAS_HEIGHT);
		this.eventbus = eventbus;
		affTrans = new Affine();
		affTrans.appendScale(Math.round((float) SharedVariable.CANVAS_WIDTH /  SharedVariable.BORD_WIDTH) ,
				Math.round((float) SharedVariable.CANVAS_HEIGHT / SharedVariable.BORD_HEIGHT));
		
		setOnMouseClicked(this::handleMouseClick);
		setOnMouseMoved(this::handleMouseMoved);
		setOnMouseDragged(this::handleDragged);
		setOnMouseExited(this::handleMouseExit);
		setOnMouseEntered(this::handleMouseEntered);
		
	}
	
	public Affine getAffine() {
		return affTrans;
	}
	
	
	
	// Events
	public void handleMouseEntered(MouseEvent event) {
		isMouseOutOfCanvas = false;
	}
	
	public void handleMouseExit(MouseEvent event) {
		isMouseOutOfCanvas = true;
		eventbus.emitEvent(new EditorMouseEvent(new CursorPosition(0 , 0), EditorMouseState.EXIT));
	}
	
	public void handleDragged(MouseEvent event) {
		CursorPosition pos = handleMouseEvent(event);
		eventbus.emitEvent(new EditorMouseEvent(pos, EditorMouseState.DRAGGED));
	}
	
	
	private CursorPosition handleMouseEvent(MouseEvent event){
		int x = (int) (Math.ceil(event.getX()/ SharedVariable.RESOLUTION));
		int y = (int) (Math.ceil(event.getY()/ SharedVariable.RESOLUTION));
		
		int cursorX = climb(x, SharedVariable.BORD_WIDTH);
		int cursorY = climb(y, SharedVariable.BORD_HEIGHT);
		cursorPosition = new CursorPosition(cursorX , cursorY);
		
		if(isEditing){
			if(cursorX != -1 && cursorY != -1)
				return cursorPosition;
		}
		return new CursorPosition(-1 , -1);
	}
	
	private int climb(int value, int lim) {
		if(value < 0)
			return -1;
		else if( value > lim)
			return -1;
		return  (value -1);
	}
	
	
	public void handleMouseMoved(MouseEvent event) {
		CursorPosition pos = handleMouseEvent(event);
		eventbus.emitEvent(new EditorMouseEvent(pos, EditorMouseState.MOVE));
	}
	
	
	
	// Handle click on StandardBoard to set state to 1
	public void handleMouseClick(MouseEvent event) {
		int x = (int) (Math.ceil(event.getX()/ SharedVariable.RESOLUTION)) -1 ;
		int y = (int) (Math.ceil(event.getY()/ SharedVariable.RESOLUTION)) -1 ;
		
		
		if(isEditing){
			eventbus.emitEvent(new EditorMouseEvent(new CursorPosition(x , y), EditorMouseState.CLICK));
		}
	}
	
	
	public boolean isMouseOutOfCanvas() {
		return isMouseOutOfCanvas;
	}
	
	public boolean isEditing() {
		return isEditing;
	}
	
}
