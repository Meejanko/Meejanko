package com.model;
import java.util.ArrayList;
import java.util.List;

import com.util.View;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SnakeModel {

	private int x;
	private int y;
	private Rectangle snakeHead;
	int direction=0;
	List<SnakeBody> bodylist=new ArrayList<>();
	public SnakeModel() {
		x=2;
		y=5;
		snakeHead=View.createRect();
		snakeHead.setFill(Color.YELLOW);
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
	public List<SnakeBody> getBodylist() {
		return bodylist;
	}
	public void setBodylist(List<SnakeBody> bodylist) {
		this.bodylist = bodylist;
	}
	public Rectangle getSnakeHead() {
		return snakeHead;
	}
	public void setSnakeHead(Rectangle snakeHead) {
		this.snakeHead = snakeHead;
	}
	
	
	
}
