package com.ahmadi.viewModels;

import com.ahmadi.SimulationState;
import com.ahmadi.interfaces.Board;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SimulationViewModel {
	
	// States
	private Board simBoard;
	private SimulationState simulationState = SimulationState.STOP;
	private final Stack<Board> allPrevBoardList;
	private final SimpleBooleanProperty isPrevBoardListEmpty;
	private final SimpleBooleanProperty isAllCellDead;
	private final SimpleBooleanProperty isSimulatting;
	
	
	
	public boolean isIsPrevBoardListEmpty() {
		return isPrevBoardListEmpty.get();
	}
	
	public SimpleBooleanProperty isPrevBoardListEmptyProperty() {
		return isPrevBoardListEmpty;
	}
	
	public Stack<Board> getAllPrevBoardList() {
		return allPrevBoardList;
	}
	
	// Liste
	private final List<StateListener<Board>> simBoardListeners;
	private final List<StateListener<SimulationState>> simStateListeners;
	
	public boolean isIsAllCellDead() {
		return isAllCellDead.get();
	}
	
	public SimpleBooleanProperty isAllCellDeadProperty() {
		return isAllCellDead;
	}
	
	public void setIsAllCellDead(boolean isAllCellDead) {
		this.isAllCellDead.set(isAllCellDead);
	}
	
	// Constructor
	public SimulationViewModel() {
		this.simBoardListeners = new LinkedList<>();
		this.simStateListeners = new LinkedList<>();
		this.allPrevBoardList = new Stack<>();
		this.isPrevBoardListEmpty  = new SimpleBooleanProperty(true);
		this.isAllCellDead = new SimpleBooleanProperty(true);
		this.isSimulatting = new SimpleBooleanProperty(false);
		
	}
	
	public boolean isIsSimulatting() {
		return isSimulatting.get();
	}
	
	public SimpleBooleanProperty isSimulattingProperty() {
		return isSimulatting;
	}
	
	public void setIsSimulatting(boolean isSimulatting) {
		this.isSimulatting.set(isSimulatting);
	}
	
	// handing simBoardListeners and notify all Listeners
	public void registerToSimBoardList(StateListener<Board> listener){
		simBoardListeners.add(listener);
	}
	
	public void setSimBoard(Board newBoard) {
		this.simBoard = newBoard;
		notifyAllSimBoardListeners();
	}
	
	private void notifyAllSimBoardListeners() {
		this.simBoardListeners.forEach(listener -> {
			try {
				listener.handle(this.simBoard);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	
	public Board getSimBoard() {
		return simBoard;
	}
	// End handing simBoardListeners and notify all Listeners
	
	
	
	// handing simStateListeners and notify all Listeners
	private void notifyAllSimStateListeners() {
		this.simStateListeners.forEach(listener -> {
			try {
				listener.handle(this.simulationState);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	public void setSimulationState(SimulationState newSimState) {
		this.simulationState = newSimState;
		notifyAllSimStateListeners();
	}
	
	public void registerToSimtstate(StateListener<SimulationState> listener){
		this.simStateListeners.add(listener);
		
	}
	
	public SimulationState getSimulationState() {
		return simulationState;
	}
	// End handing simStateListeners and notify all Listeners
	
}
