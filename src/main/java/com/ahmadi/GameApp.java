package com.ahmadi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		CanvasApp canvasApp = new CanvasApp();
		Scene scene = new Scene(canvasApp);
		scene.getStylesheets().add(getClass().getResource("gameApp.css").toExternalForm());
		
		primaryStage.setMinHeight(500);
		primaryStage.setMinWidth(1200);
		primaryStage.setHeight(SharedVariable.APP_HEIGHT.getValue());
		primaryStage.setWidth(SharedVariable.APP_WIDTH.getValue());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
