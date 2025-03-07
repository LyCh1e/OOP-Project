package ProjectOOP.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

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

    protected Texture getTexture() {
    	return texture; 
    }
    
    protected float getX() { 
    	return x_axis;
    }
    
    protected float getY() { 
    	return y_axis; 
    }
    
    protected float getSpeed() { 
    	return speed; 
    }
    
    protected float getVelocityX() {
    	return velocityX;
    }
    
    protected float getVelocityY() { 
    	return velocityY; 
    }

    protected void setTexture(Texture t) { 
    	texture = t; 
    }

    // Store previous position before updating
    protected void setX(float x) {
        prevX = x_axis;
        x_axis = x;
    }

    protected void setY(float y) {
        prevY = y_axis;
        y_axis = y;
    }

    protected void setSpeed(float s) { 
    	speed = s;
    }
    
    protected void setVelocityX(float f) { 
    	velocityX = f; 
    }
    
    protected void setVelocityY(float f) { 
    	velocityY = f;
    }

    protected float getPrevX() { 
    	return prevX; 
    }
    
    protected float getPrevY() { 
    	return prevY;
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
    
    public void draw(SpriteBatch batch) {
    	batch.draw(getTexture(), getX(), getY(), getTexture().getWidth(), getTexture().getHeight());
    }
    
    abstract void draw(ShapeRenderer shape);

    abstract void updatePosition();
}
