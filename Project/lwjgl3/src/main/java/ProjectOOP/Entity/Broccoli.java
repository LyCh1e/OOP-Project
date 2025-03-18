package ProjectOOP.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Broccoli extends MovableEntity {
	private float minY, maxY; 
    private boolean movingUp = true;
 
    
	public Broccoli(float x, float y, float speed, float minY, float maxY) {
		super("broccoli.png", x, y, speed);
        this.minY = minY;
        this.maxY = maxY;
	}

	 public void move(float deltaTime) {
	        // Move left
	    	setX(getX() - getSpeed() * deltaTime); // Move left based on current speed

	        // Respawn if off-screen
	        if (getX() < -50) { 
	            float newX = Gdx.graphics.getWidth() + 50; // Respawn on the right
	            float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100; // Random Y position
	            setX(newX);
	            setY(newY);
	        }
	    }


	 @Override
	 public void draw(SpriteBatch batch) {
	 	batch.draw(texture, getX(), getY());
	 }

	 public void setPosition(float newX, float newY) {
	 	setX(newX);
	 	setY(newY);
	 }
}

