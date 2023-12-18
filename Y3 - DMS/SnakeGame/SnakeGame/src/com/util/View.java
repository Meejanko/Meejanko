package com.util;
import com.model.GameModel;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class View {
	public static Rectangle createBlank(){
		Rectangle rect=new Rectangle();
		rect.setFill(Color.TRANSPARENT);
		rect.setHeight(GameModel.GAME_UNITS);
		rect.setWidth(GameModel.GAME_UNITS);
		return rect;
	}
	
	public static Rectangle createRect(){
		Rectangle rect=new Rectangle();
		rect.setFill(Color.GREEN);
		rect.setHeight(GameModel.GAME_UNITS);
		rect.setWidth(GameModel.GAME_UNITS);
		return rect;
	}
	public static Circle createFoodCircle(){
		Circle circle=new Circle();
		circle.setFill(Color.RED);
		circle.setRadius(GameModel.GAME_UNITS/2);
		return circle;
	}
}
