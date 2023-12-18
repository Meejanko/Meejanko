package com.model;
import com.util.View;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class SnakeBody {
	private int x;
	private int y;
	private Rectangle snakeBody;
	int direction=0;
	
	public SnakeBody(int x,int y) {
		this.x=x;
		this.y=y;
		snakeBody=View.createRect();
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
	public int getDirection() {
		return direction;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public Rectangle getSnakeBody() {
		return snakeBody;
	}
	public void setSnakeBody(Rectangle snakeBody) {
		this.snakeBody = snakeBody;
	}
	
	
}
