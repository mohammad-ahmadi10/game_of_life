package com.ahmadi.view;

import com.ahmadi.states.ApplicationStateType;
import com.ahmadi.states.CellState;
import com.ahmadi.utils.CursorPosition;
import com.ahmadi.states.InfoBarComponentState;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;


public class InfoBar extends AnchorPane {
	Label cursorLbl , modeLbl , drawSateLbl;
	
	
	
	public InfoBar(InfoBarComponentState infoBarComponentState) {
		String cursorPattern = String.format("Cursor Position: (%d, %d)" , 0 , 0);
		String appStatePattern = String.format("App mode: %s" , ApplicationStateType.EDITING.toString().toLowerCase());
		String drawStatePattern = "Draw mode: draw";
		
		infoBarComponentState.getCursorProperty().subscribe(this::setCursorPosToLbl);
		infoBarComponentState.getCellProperty().subscribe(this::setDrawMode);
		infoBarComponentState.getAppState().subscribe(this::setAppMode);
		
		cursorLbl = new Label(cursorPattern);
		modeLbl = new Label(appStatePattern);
		drawSateLbl = new Label(drawStatePattern);
		
		cursorLbl.setAlignment(Pos.CENTER_LEFT);
		modeLbl.setAlignment(Pos.CENTER);
		drawSateLbl.setAlignment(Pos.CENTER_RIGHT);
		cursorLbl.setFont(Font.font("Andalus"));
		modeLbl.setFont(Font.font("Andalus"));
		drawSateLbl.setFont(Font.font("Andalus"));
		
		addNodeToAncharpane(cursorLbl , 5 , 850 );
		addNodeToAncharpane(modeLbl , 0 , 150  );
		addNodeToAncharpane(drawSateLbl , 0 , 305 );
		
		
		this.getChildren().addAll(cursorLbl , modeLbl , drawSateLbl);
		this.setWidth(Double.MAX_VALUE);
	}
	
	public void setCursorPosToLbl(CursorPosition posis){
		int cursorX = posis.getX();
		int cursorY = posis.getY();
		
		String formattedString = String.format("Cursor Position: (%d, %d)" , cursorX , cursorY);
		cursorLbl.setText(formattedString);
	}
	
	public void setAppMode(ApplicationStateType state){
		String formattedString = String.format("App mode: %s" , state.toString().toLowerCase());
		modeLbl.setText(formattedString);
	}
	
	public void setDrawMode(CellState state){
		String cellState = state.equals(CellState.ALIVE) ? "draw" : "erase";
		String formattedString = String.format("Draw mode: %s" , cellState);
		drawSateLbl.setText(formattedString);
	}
	
	private void addNodeToAncharpane(Node node, double left, double right){
		AnchorPane.setBottomAnchor(node , 0.0);
		AnchorPane.setTopAnchor(node , 0.0);
		AnchorPane.setLeftAnchor(node , left);
		AnchorPane.setRightAnchor(node , right);
	}
	
	
}
