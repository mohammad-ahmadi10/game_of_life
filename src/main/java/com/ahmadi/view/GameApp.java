package com.ahmadi.view;

import com.ahmadi.command.CommandExecutor;
import com.ahmadi.components.*;
import com.ahmadi.states.RegistryState;
import com.ahmadi.utils.SharedVariable;
import com.ahmadi.utils.eventbus.Eventbus;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class GameApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		// utils
		Eventbus eventbus = new Eventbus();
		RegistryState registryState = new RegistryState();
		CommandExecutor executor = new CommandExecutor(registryState);
		CanvasApp canvasApp = new CanvasApp(eventbus , executor);
		AppContext context = new AppContext(eventbus , registryState , executor , canvasApp);
		
		
		List<Component> componentList = new LinkedList<>();
		componentList.add(new CanvasComponent());
		componentList.add(new ApplicationComponent());
		componentList.add(new BoardComponent());
		componentList.add(new EditorComponent());
		componentList.add(new InfoBarComponent());
		componentList.add(new SimulationComponent());
		componentList.add(new SimulatorComponent());
		componentList.add(new ToolBarComponent());
		
		
		componentList.forEach(component -> component.initState(context));
		componentList.forEach(component -> component.initComponent(context));
		
		
		Scene scene = new Scene(canvasApp);
		scene.getStylesheets().add(getClass().getResource("gameApp.css").toExternalForm());
		
		
		// setting windows size
		stage.setMinHeight(500);
		stage.setMinWidth(1200);
		stage.setMaxHeight(800);
		stage.setMaxWidth(1500);
		
		stage.setHeight(SharedVariable.APP_HEIGHT);
		stage.setWidth(SharedVariable.APP_WIDTH);
		stage.setScene(scene);
		stage.show();
		
	}
}
