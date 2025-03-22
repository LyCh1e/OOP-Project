package ProjectOOP.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public abstract class Entity {    	
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
    
    protected float getX() { 
    	return x_axis;
    }
    
    // Store previous position before updating
    protected void setX(float x) {
    	prevX = x_axis;
    	x_axis = x;
    }
    
    protected float getY() { 
    	return y_axis; 
    }
    
    protected void setY(float y) {
        prevY = y_axis;
        y_axis = y;
    }
    
    protected float getSpeed() { 
    	return speed; 
    }
    
    protected void setSpeed(float s) { 
    	speed = s;
    }
    
    protected float getVelocityX() {
    	return velocityX;
    }
    
    protected void setVelocityX(float f) { 
    	velocityX = f; 
    }
    
    protected float getVelocityY() { 
    	return velocityY; 
    }
    
    protected void setVelocityY(float f) { 
    	velocityY = f;
    }

    void setTexture(Texture t) { 
    	texture = t; 
    }

    public float getPrevX() { 
    	return prevX; 
    }
    
    public float getPrevY() { 
    	return prevY;
    }
    
    public float retrieveX() {
    	return getX();
    }
    
    public void updateX(float x) {
    	setX(x);
    }
    
    public float retrieveY() {
    	return getY();
    }
    
    public void updateY(float y) {
    	setY(y);
    }
    
    public float retrieveVelocityX() {
    	return getVelocityX();
    }
    
    public void updateVelocityX(float x) {
    	setVelocityX(x);
    }
    
    public float retrieveVelocityY() {
    	return getVelocityY();
    }
    
    public void updateVelocityY(float y) {
    	setVelocityY(y);
    }
    
    public float retrieveSpeed() {
    	return getSpeed();
    }
    
    public void updateSpeed(float s) {
    	setSpeed(s);
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

        System.out.println("Entity respawned at: X = " + getX() + ", Y = " + getY() + ", Speed = " + getSpeed());
    }
    
    public void draw(SpriteBatch batch) {
    	batch.draw(getTexture(), getX(), getY(), getTexture().getWidth(), getTexture().getHeight());
    }
    
    abstract void draw(ShapeRenderer shape);

    abstract void updatePosition();
}
