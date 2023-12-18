package com.view;

import com.controller.Controller;
import com.model.GameModel;
import com.util.View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameUI extends BorderPane{
	
	
	
	
	
	Button startBtn=new Button("start");
	Button returnBtn=new Button("finish");
	Controller controller;
	
	public GameUI(Controller controller,GridPane gamePane,Label lbl) {
		
		this.setStyle("-fx-background-color:"+GameModel.BGCOLOR);
		this.controller=controller;
		lbl.setText("score:"+controller.score);
		lbl.setTextFill(Color.RED);
		VBox vBox=new VBox();
		vBox.setPrefWidth(50);
		this.setRight(vBox);
		
		VBox vBoxl=new VBox();
		vBoxl.setPrefWidth(50);
		this.setLeft(vBoxl);
		HBox hBox=new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().add(lbl);hBox.getChildren().add(startBtn);
		hBox.getChildren().add(returnBtn);
		hBox.setSpacing(20);
		hBox.setPrefHeight(100);
		
		
		gamePane.setStyle("-fx-border-color:red");
		this.setCenter(gamePane);
		this.setTop(hBox);
		for (int i = 0; i < GameModel.GAME_SIZE; i++) {
			for (int j = 0; j < GameModel.GAME_SIZE; j++) {
				gamePane.add(View.createBlank(), j, i);
			}
		}
		gamePane.add(controller.snake.getSnakeHead(), controller.snake.getX(), controller.snake.getY());
		controller.snake.getSnakeHead().setRotate(controller.snake.getDirection()*90);
		
		returnBtn.setOnAction(e->{
			controller.scheduleService.shutdownNow();
			Stage primaryStage=new Stage();
			primaryStage.setTitle(GameModel.GAMETITLE);
			Scene scene = new Scene(new StartUI(""),600,600);		
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			((Stage) ((Button)e.getSource()).getScene().getWindow()).close();
		});
		startBtn.setOnAction(e->{
			if(controller.pause){
				controller.pause=false;
				startBtn.setText("pause");
			}else{
				controller.pause=true;
				startBtn.setText("start");
			}
		});
		
	}
	
	public void showMsg(String msg){
	   	 Alert alert = new Alert(AlertType.INFORMATION);
	        alert.titleProperty().set("tips");
	        alert.headerTextProperty().set(msg);
	        alert.showAndWait();
	   }
}
