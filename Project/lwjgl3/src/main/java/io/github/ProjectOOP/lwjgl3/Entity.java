package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity implements iMovable {    	
	protected float x_axis, y_axis, speed; 
	protected float velocityX, velocityY;
	protected Texture texture;
	protected boolean movable;
	
	Entity (){
		
	}
	
	Entity (String str, float x, float y, float s){
		setX(x);
		setY(y);
		setSpeed(s);
		texture = new Texture(Gdx.files.internal(str));
	}
	
	Entity (String str, float x, float y, float s, boolean m){
		setX(x);
		setY(y);
		setSpeed(s);
		texture = new Texture(Gdx.files.internal(str));
		movable = m;
	}
	
	public Texture getTexture() {
		return texture;
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
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public boolean getMovable() {
		return movable;
	}
	
	void setTexture(Texture t) {
		texture = t;
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
	
	void setVelocityX(float f) {
		velocityX = f;	
	}
	
	void setVelocityY(float f) {
		velocityY = f;
	}
	
	void draw (SpriteBatch batch) {
		batch.draw(getTexture(), getX(), getY(), getTexture().getWidth(), getTexture().getHeight());
	}

	abstract void updatePosition();
}
