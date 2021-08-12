package com.ahmadi.components;

import com.ahmadi.command.CommandExecutor;
import com.ahmadi.model.StandardBoard;
import com.ahmadi.model.abstracts.Board;
import com.ahmadi.states.RegistryState;
import com.ahmadi.utils.SharedVariable;
import com.ahmadi.utils.eventbus.Eventbus;
import com.ahmadi.view.CanvasApp;

public class AppContext {
	
	// utils
	private final Eventbus eventbus ;
	private final RegistryState registryState ;
	private final CommandExecutor executor;
	private final CanvasApp canvasApp;
	// models
	private final Board board;
	
	public AppContext(Eventbus eventbus, RegistryState registryState, CommandExecutor executor, CanvasApp canvasApp) {
		this.eventbus = eventbus;
		this.registryState = registryState;
		this.executor = executor;
		this.canvasApp = canvasApp;
		this.board = new StandardBoard(SharedVariable.BORD_WIDTH , SharedVariable.BORD_HEIGHT);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Eventbus getEventbus() {
		return eventbus;
	}
	
	public RegistryState getRegistryState() {
		return registryState;
	}
	
	public CommandExecutor getExecutor() {
		return executor;
	}
	
	public CanvasApp getCanvasApp() {
		return canvasApp;
	}
}
