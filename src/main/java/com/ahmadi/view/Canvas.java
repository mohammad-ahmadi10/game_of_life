package com.ahmadi.view;

import com.ahmadi.states.CanvasComponentState;
import com.ahmadi.states.EditorMouseEventType;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.utils.SharedVariable;
import com.ahmadi.utils.eventbus.Eventbus;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Affine;

public class Canvas extends javafx.scene.canvas.Canvas {
	private final Affine affTrans;
	
	private final CanvasComponentState state;
	private final Eventbus eventbus;
	
	
	public Canvas(Eventbus eventbus , CanvasComponentState state) {
		super(SharedVariable.CANVAS_WIDTH , SharedVariable.CANVAS_HEIGHT);
		this.eventbus = eventbus;
		this.state = state;
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
		state.getIsMouseOutOfCanvas().setValue(false);
	}
	
	public void handleMouseExit(MouseEvent event) {
		state.getIsMouseOutOfCanvas().setValue(true);
		eventbus.emitEvent(new com.ahmadi.events.EditorMouseEvent(new CursorPosition(0 , 0), EditorMouseEventType.EXIT));
	}
	
	public void handleDragged(MouseEvent event) {
		CursorPosition pos = handleMouseEvent(event);
		state.getCursorPositionProperty().setValue(pos);
		eventbus.emitEvent(new com.ahmadi.events.EditorMouseEvent(pos, EditorMouseEventType.DRAGGED));
	}
	
	
	private CursorPosition handleMouseEvent(MouseEvent event){
		int x = (int) (Math.ceil(event.getX()/ SharedVariable.RESOLUTION));
		int y = (int) (Math.ceil(event.getY()/ SharedVariable.RESOLUTION));
		
		int cursorX = climb(x, SharedVariable.BORD_WIDTH);
		int cursorY = climb(y, SharedVariable.BORD_HEIGHT);
		CursorPosition cursorPosition = new CursorPosition(cursorX, cursorY);
		
		if(state.getIsEditing().getValue()){
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
		state.getCursorPositionProperty().setValue(pos);
		eventbus.emitEvent(new com.ahmadi.events.EditorMouseEvent(pos, EditorMouseEventType.MOVE));
	}
	
	
	
	// Handle click on StandardBoard to set state to 1
	public void handleMouseClick(MouseEvent event) {
		int x = (int) (Math.ceil(event.getX()/ SharedVariable.RESOLUTION)) -1 ;
		int y = (int) (Math.ceil(event.getY()/ SharedVariable.RESOLUTION)) -1 ;
		
		
		if(state.getIsEditing().getValue()){
			state.getCursorPositionProperty().setValue(new CursorPosition(x, y));
			eventbus.emitEvent(new com.ahmadi.events.EditorMouseEvent(new CursorPosition(x , y), EditorMouseEventType.CLICK));
		}
	}
	
}
