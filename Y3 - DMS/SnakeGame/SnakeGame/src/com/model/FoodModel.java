package com.model;
import com.util.View;

import javafx.scene.shape.Circle;

public class FoodModel {
	
	
	private int x;
	private int y;
	private Circle foodCircle;

	private String food;
	public FoodModel(int x,int y) {
		this.x=x;
		this.y=y;
		foodCircle=View.createFoodCircle();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public Circle getFoodCircle() {
		return foodCircle;
	}
	public void setFoodCircle(Circle foodCircle) {
		this.foodCircle = foodCircle;
	}
	
	
}
