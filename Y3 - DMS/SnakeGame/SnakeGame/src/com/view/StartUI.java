package com.view;


import com.controller.Controller;
import com.model.GameModel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartUI extends AnchorPane {

	private String mainid;
	public String getMainid() {
		return mainid;
	}
	public void setMainid(String mainid) {
		this.mainid = mainid;
	}
	double startY=100.0;
	public StartUI(String param) {
		this.mainid=param;
		
		Label lbl=new Label("SNAKE GAME");
		lbl.setLayoutX(180);
		lbl.setLayoutY(startY);
		lbl.setFont(Font.font(30));
		this.getChildren().add(lbl);
		Button infoBtn=new Button("INFO");
		infoBtn.setFont(new Font("宋体", 14));
		infoBtn.setPrefWidth(Double.valueOf(300));
		infoBtn.setPrefHeight(Double.valueOf(50));
		this.setLeftAnchor(infoBtn, Double.valueOf(133));
		this.setTopAnchor(infoBtn, Double.valueOf(startY+200));

		infoBtn.setOnAction(e->{
			Stage stage=new Stage();
		    stage.setTitle("INFO");
		    stage.setScene(new Scene(new InfoUI(""),600,600));
		    stage.show();
		});
		this.getChildren().add(infoBtn);
		Button scoreBtn=new Button("VIEW SCORE");
		scoreBtn.setFont(new Font("宋体", 14));
		scoreBtn.setPrefWidth(Double.valueOf(300));
		scoreBtn.setPrefHeight(Double.valueOf(50));
		this.setLeftAnchor(scoreBtn, Double.valueOf(133));
		this.setTopAnchor(scoreBtn, Double.valueOf(startY+300));
		scoreBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
			    Stage stage=new Stage();
			    stage.setTitle("VIEW SCORE");
			    stage.setScene(new Scene(new Scoreui(""),1100,600));
			    stage.show();
			}
		});
		this.getChildren().add(scoreBtn);
		Button startBtn=new Button("START");
		startBtn.setFont(new Font("宋体", 14));
		startBtn.setPrefWidth(Double.valueOf(300));
		startBtn.setPrefHeight(Double.valueOf(50));
		this.setLeftAnchor(startBtn, Double.valueOf(133));
		this.setTopAnchor(startBtn, Double.valueOf(startY+400));
		startBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
			    Stage stage=new Stage();
			    stage.setTitle("START");
			    Label lbl=new Label("SCORE:");
				GridPane gamePane=new GridPane();
				Controller controller=new Controller(gamePane,lbl);
				controller.setStage(stage);
			    GameUI gui=new GameUI(controller,gamePane,lbl);
			    gui.controller=controller;
			    Scene scene = new Scene(gui, GameModel.SCREEN_SIZE, GameModel.SCREEN_SIZE);
				scene.setOnKeyReleased(k->{
					controller.changeSnakeDirection(k.getCode().toString());
				});
				stage.setTitle("snake game");
			    stage.setScene(scene);
			    stage.setResizable(false);
			    stage.show();
			    ((Stage) ((Button)e.getSource()).getScene().getWindow()).close();
			}
		});
		this.getChildren().add(startBtn);
		
		Label ztlbl=new Label("THEME COLOR");
		ztlbl.setFont(new Font("宋体", 14));
		ztlbl.setPrefWidth(Double.valueOf(200));
		ztlbl.setPrefHeight(Double.valueOf(23));
		this.setLeftAnchor(ztlbl, Double.valueOf(107));
		this.setTopAnchor(ztlbl, Double.valueOf(startY+100));
		this.getChildren().add(ztlbl);
		ComboBox<String> colorcombox = new ComboBox<>();
		colorcombox.setPrefWidth(Double.valueOf(93));
		colorcombox.setPrefHeight(Double.valueOf(23));
		this.setLeftAnchor(colorcombox, Double.valueOf(211));
		this.setTopAnchor(colorcombox, Double.valueOf(startY+100));
		colorcombox.getItems().add("red");
		colorcombox.getItems().add("white");
		colorcombox.getItems().add("black");
		colorcombox.getItems().add("gray");
		colorcombox.getItems().add("yellow");
		colorcombox.getItems().add("blue");
		colorcombox.getItems().add("#e2e9ff");
		colorcombox.getItems().add("#191e23");
		colorcombox.getItems().add("#f79d23");
		this.getChildren().add(colorcombox);
		Button colorbtn=new Button("OPTION");
		colorbtn.setFont(new Font("宋体", 14));
		colorbtn.setPrefWidth(Double.valueOf(93));
		colorbtn.setPrefHeight(Double.valueOf(23));
		this.setLeftAnchor(colorbtn, Double.valueOf(324));
		this.setTopAnchor(colorbtn, Double.valueOf(startY+100));
		colorcombox.getSelectionModel().select(GameModel.BGCOLOR);
		this.getChildren().add(colorbtn);
		colorbtn.setOnAction(e->{
			this.setStyle("-fx-background-color:  "+colorcombox.getSelectionModel().getSelectedItem()+";");
			GameModel.BGCOLOR=colorcombox.getSelectionModel().getSelectedItem();
		});
		
	}
 	public void showMsg(String msg){
   	 Alert alert = new Alert(AlertType.INFORMATION);
        alert.titleProperty().set("提示");
        alert.headerTextProperty().set(msg);
        alert.showAndWait();
   }
}
