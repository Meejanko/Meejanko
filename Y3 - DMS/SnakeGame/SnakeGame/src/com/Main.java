package com;

import com.model.GameModel;
import com.view.StartUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle(GameModel.GAMETITLE);
		Scene scene = new Scene(new StartUI(""),540,600);		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}
