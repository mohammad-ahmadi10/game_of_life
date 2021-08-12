package com.ahmadi.components;

import com.ahmadi.drawLayer.CellStateDrawLayer;
import com.ahmadi.drawLayer.GridDrawLayer;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.BoardComponentState;
import com.ahmadi.states.EditorComponentState;
import com.ahmadi.states.SimulationComponentState;
import com.ahmadi.states.SimulatorComponentState;
import com.ahmadi.view.GridPane;
import com.ahmadi.viewModels.BoardViewModel;
import javafx.scene.Node;

public class BoardComponent implements Component{
	
	
	@Override
	public void initComponent(AppContext context) {
		EditorComponentState editorComponentState = context.getRegistryState().getState(EditorComponentState.class);
		SimulatorComponentState simulatorComponentState = context.getRegistryState().getState(SimulatorComponentState.class);
		BoardComponentState boardComponentState = context.getRegistryState().getState(BoardComponentState.class);
		SimulationComponentState simulationComponentState = context.getRegistryState().getState(SimulationComponentState.class);
		
		GridPane gridPane = (GridPane) context.getCanvasApp().getCenter();
		BoardViewModel boardViewModel = new BoardViewModel(editorComponentState, simulatorComponentState, boardComponentState , simulationComponentState);
		
		
		// DrawLayers
		CellStateDrawLayer cellStateDrawLayer = new CellStateDrawLayer(boardComponentState);
		GridDrawLayer gridDrawLayer = new GridDrawLayer(boardComponentState);
		gridPane.addDrawLayer(cellStateDrawLayer);
		gridPane.addDrawLayer(gridDrawLayer);
		
		
		boardComponentState.getBoardProperty().subscribe(gridPane::handleNewBoard);
		boardComponentState.getBoardProperty().setValue(context.getBoard());
		simulatorComponentState.getSimStateProperty().subscribe(boardViewModel::handleSimState);
	}
	
	
	@Override
	public void initState(AppContext context) {
		Board board = context.getBoard();
		BoardComponentState boardComponentState = new BoardComponentState(board);
		context.getRegistryState().register(BoardComponentState.class , boardComponentState);
		
	}
}
