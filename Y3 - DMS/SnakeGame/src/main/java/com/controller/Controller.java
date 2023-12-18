package com.controller;
import java.io.File;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.model.FoodModel;
import com.model.GameModel;
import com.model.SnakeBody;
import com.model.SnakeModel;
import com.model.Youxifenshu;
import com.util.Dao;
import com.view.GameUI;
import com.view.StartUI;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextInputDialog;

import javafx.stage.Stage;

public class Controller {
	public Random random=new Random();
	public SnakeModel snake=new SnakeModel();
	public boolean hasfood=false;
	public FoodModel food;
	public int score;
	public Dao<Youxifenshu> dao=new Dao<Youxifenshu>(new Youxifenshu());
	public boolean pause=true;
	public int speed=450;
	File directory = new File("");
	public ScheduledExecutorService scheduleService=Executors.newScheduledThreadPool(1);
	GridPane gamePane;
	Label lbl;
	int count=0;
	Stage stage;
	public Controller(GridPane gamePane,Label lbl) {
		this.gamePane=gamePane;
		this.lbl=lbl;
		scheduleService.scheduleAtFixedRate(new Runnable() {
			public void run() {
				Platform.runLater(new Task<Integer>() {
                    protected Integer call() {
                    	start(gamePane);
						return null;
                    }
				});
			}
		}, 1, speed, TimeUnit.MILLISECONDS);
	}
	public void changeSnakeDirection(String direction){
		if(direction.equals("RIGHT")){
			if(snake.getDirection()!=2){
				snake.setDirection(0);
			}
		}else if(direction.equals("LEFT")){
			if(snake.getDirection()!=0){
				snake.setDirection(2);
			}
		}else if(direction.equals("UP")){
			if(snake.getDirection()!=1){
				snake.setDirection(3);
			}
		}else if(direction.equals("DOWN")){
			if(snake.getDirection()!=3){
				snake.setDirection(1);
			}
		}
	}
	public void gameove(){
		showMsg("GAME OVER !player get score:"+score);
		TextInputDialog tg=new TextInputDialog();
		tg.setContentText("please input player：");
		tg.showAndWait();
		String name =tg.getResult();
		Youxifenshu fenshu=new Youxifenshu();
		fenshu.setWanjia(name);
		fenshu.setYingdedejushu(String.valueOf(score));
		dao.add(fenshu);
		scheduleService.shutdownNow();
		Stage primaryStage=new Stage();
		primaryStage.setTitle(GameModel.GAMETITLE);
		Scene scene = new Scene(new StartUI(""),600,600);		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		stage.close();
	}
	
	//Snake移动
	public void snakeMove(ScheduledExecutorService scheduleService){
		if(snake.getDirection()==0){
    		snake.setX(snake.getX()+1);
    	}else if(snake.getDirection()==1){
    		snake.setY(snake.getY()+1);
    	}else if(snake.getDirection()==2){
    		snake.setX(snake.getX()-1);
    	}else if(snake.getDirection()==3){
    		snake.setY(snake.getY()-1);
    	}
    	snake.getSnakeHead().setRotate(snake.getDirection()*90);
    	
	}
	public Object start(GridPane gamePane){
		if(pause)return null;
    	gamePane.getChildren().remove(snake.getSnakeHead());
    	for (int i = 0; i < snake.getBodylist().size(); i++) {
    		if(snake.getX()==snake.getBodylist().get(i).getX()&&snake.getY()==snake.getBodylist().get(i).getY()){
    			scheduleService.shutdownNow();
    			gameove();
    		}
    	}
    	if(!hasfood){
    		food=new FoodModel( random.nextInt(GameModel.GAME_SIZE), random.nextInt(GameModel.GAME_SIZE));
    		gamePane.add(food.getFoodCircle(), food.getX(), food.getY());
    		hasfood=true;
    	}
    	int bx=snake.getX();
    	int by=snake.getY();
    	snakeMove(scheduleService);
    	
    	if(snake.getX()==food.getX()&&snake.getY()==food.getY()){
    		SnakeBody sbody=new SnakeBody(food.getX(), food.getY());
    		snake.getBodylist().add(sbody);
    		gamePane.getChildren().remove(food.getFoodCircle());
    		score+=100;
    		lbl.setText("score:"+score+" speed:"+(500-speed));
    		hasfood=false;
    		speed=450-(score/500)*50;
    		if(speed<300){
    			speed=300;
    		}
    		scheduleService.shutdownNow();
    		scheduleService=Executors.newScheduledThreadPool(1);
        	scheduleService.scheduleAtFixedRate(new Runnable() {
    			public void run() {
    				Platform.runLater(new Task<Integer>() {
                        protected Integer call() {
                        	start(gamePane);
    						return null;
                        }
    				});
    			}
    		}, 0, speed, TimeUnit.MILLISECONDS);
    	}
    	
    	if(snake.getX()<0||snake.getY()<0||snake.getX()>GameModel.GAME_SIZE||snake.getY()>(GameModel.GAME_SIZE-1)){
    		scheduleService.shutdownNow();
    		gameove();
    	}
    	
    	for (int i = 0; i < snake.getBodylist().size(); i++) {
    		gamePane.getChildren().remove(snake.getBodylist().get(i).getSnakeBody());
    		int lx=snake.getBodylist().get(i).getX();
    		int ly=snake.getBodylist().get(i).getY();
    		snake.getBodylist().get(i).setX(bx);
    		snake.getBodylist().get(i).setY(by);
    		bx=lx;by=ly;
		}
    	for (int i = 0; i < snake.getBodylist().size(); i++) {
    		gamePane.add(snake.getBodylist().get(i).getSnakeBody(),snake.getBodylist().get(i).getX(),snake.getBodylist().get(i).getY());
    	}
    	gamePane.add(snake.getSnakeHead(), snake.getX(), snake.getY());
		return null;
    	
	}
	
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public void showMsg(String msg){
	   	 Alert alert = new Alert(AlertType.INFORMATION);
	        alert.titleProperty().set("tips");
	        alert.headerTextProperty().set(msg);
	        alert.showAndWait();
	   }
}
