package io.github.ProjectOOP.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Entity {    
	private float x_leftLimit = 0;
	private float x_rightLimit = 1210;
	private float y_bottomLimit = 0;
	
	private float gravity = -980f; // gravity of the simulation
	private float verticalVelocity = 2; // Velocity variable
	
	private float x_axis, y_axis, speed; 
	private float velocityX, velocityY;
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
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
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
	
	void setVelocityX(float f) {
		velocityX = f;	
	}
	
	void setVelocityY(float f) {
		velocityY = f;
	}
	
	public void movement() { //movement of entity using arrow keys
		
	    if (Gdx.input.isKeyPressed(Keys.LEFT)) setX(getX() - 300 * Gdx.graphics.getDeltaTime());
	    if (Gdx.input.isKeyPressed(Keys.RIGHT)) setX(getX() + 300 * Gdx.graphics.getDeltaTime());

	    // Apply gravity to vertical velocity
	    verticalVelocity += gravity * Gdx.graphics.getDeltaTime();

	    // Jump when the up arrow key is pressed and if the entity is on the ground
	    if (Gdx.input.isKeyPressed(Keys.UP) && getY() <= y_bottomLimit) {
	        verticalVelocity = 500; // jump height value
	    }

	    // Update vertical position based on velocity
	    setY(getY() + verticalVelocity * Gdx.graphics.getDeltaTime());

	    if (getX() < x_leftLimit) {
	        setX(x_leftLimit);
	    }
	    else if (getX() > x_rightLimit) {
	        setX(x_rightLimit);
	    }

	    if (getY() < y_bottomLimit) {
	        setY(y_bottomLimit);
	        verticalVelocity = 0; // Reset velocity when hitting the ground
	    }
	}
	
	
	public void AIMovment() { //movement of entity from right to left
		Random random = new Random();
		float randomY = random.nextFloat(0, 60);
		float x = getX();
		float speed = getSpeed();
		setX(x -= speed);
		if (getX() < 0) {
			setX(1280);
			setY(randomY);
			setSpeed(speed += speed);
			if (getSpeed() > 8) {
				setSpeed(8);
			}
		}
	}
}
