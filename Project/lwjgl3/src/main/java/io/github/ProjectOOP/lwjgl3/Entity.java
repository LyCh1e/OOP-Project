package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {
	private float x_axis, y_axis, speed;
	
	Entity (){
		
	}
	
	Entity (float x, float y, float s){
		
	}
	
	public float getX () {
		return x_axis;
	}
	
	public float getY() {
		return y_axis;
	}
	
	public float getSpeed () {
		return speed;
	}
	
	void setX (float x) {
		x_axis = x;
	}
	
	void setY (float y) {
		y_axis = y;
	}
	
	void setSpeed (float s) {
		speed = s;
	}
	
	
	void draw (SpriteBatch batch) {
		
	}
	
	public void movement() {
		
	}
}
