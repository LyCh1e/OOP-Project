package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {    
	private float x_leftLimit = 0;
	private float x_rightLimit = 1210;
	private float y_bottomLimit = 0;
	private float x_axis, y_axis, speed;
	private Texture texture;
	
	Entity (){
		
	}
	
	Entity (String str, float x, float y, float s){
		setX(x);
		setY(y);
		setSpeed(s);
		texture = new Texture(Gdx.files.internal(str));
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
	
	void draw (SpriteBatch batch) {
		batch.draw(getTexture(), getX(), getY(), getTexture().getWidth(), getTexture().getHeight());
	}
	
	public void movement() {
		if (Gdx.input.isKeyPressed(Keys.LEFT)) setX(getX() - 200 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) setX(getX() + 200 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.DOWN)) setY(getY() - 200 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.UP)) setY(getY() + 200 * Gdx.graphics.getDeltaTime());
		
		if (getX() < x_leftLimit) {
			setX(x_leftLimit);
		}
		else if (getX() > x_rightLimit) {
			setX(x_rightLimit);
		}
		
		if (getY() < y_bottomLimit) {
			setY(y_bottomLimit);
		}
	}
	
	public void AIMovment() {
		float x = getX();
		float speed = getSpeed();
		setX(x -= speed);
		if (getX() < 0) {
			setX(1280);
			setSpeed(speed += speed);
			if (getSpeed() > 8) {
				setSpeed(8);
			}
		}
	}
}
