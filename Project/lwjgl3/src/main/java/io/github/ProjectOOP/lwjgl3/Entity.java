package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public abstract class Entity implements iMovable {    	
    protected float x_axis, y_axis, speed; 
    protected float velocityX, velocityY;
    protected Texture texture;

    // Store previous position
    private float prevX, prevY; 
    private static final Random random = new Random();

    Entity() { }

    Entity(String str, float x, float y, float s) {
        setX(x);
        setY(y);
        setSpeed(s);
        texture = new Texture(Gdx.files.internal(str));

        // Initialize previous position
        prevX = x;
        prevY = y;
    }

    public Texture getTexture() {
    	return texture; 
    }
    
    public float getX() { 
    	return x_axis;
    }
    
    public float getY() { 
    	return y_axis; 
    }
    
    public float getSpeed() { 
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

    // Store previous position before updating
    void setX(float x) {
        this.prevX = this.x_axis;
        this.x_axis = x;
    }

    void setY(float y) {
        this.prevY = this.y_axis;
        this.y_axis = y;
    }

    void setSpeed(float s) { 
    	speed = s;
    }
    
    void setVelocityX(float f) { 
    	velocityX = f; 
    }
    
    void setVelocityY(float f) { 
    	velocityY = f;
    }

    public float getPrevX() { 
    	return prevX; 
    }
    
    public float getPrevY() { 
    	return prevY;
    }

    void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY(), getTexture().getWidth(), getTexture().getHeight());
    }

    //Ensures entity never respawn in the same position.
    public void respawn(float minX, float maxX, float minY, float maxY) {
        float newX, newY;

        do {
            newX = minX + random.nextFloat() * (maxX - minX);
            newY = minY + random.nextFloat() * (maxY - minY);
        } while (newX == prevX && newY == prevY); // Prevent duplicate spawn

        setX(newX);
        setY(newY);

        System.out.println("Entity respawned at: X = " + getX() + ", Y = " + getY());
    }

    abstract void updatePosition();
}
