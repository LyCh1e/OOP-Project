package ProjectOOP.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class SoftDrink extends MovableEntity { 
	private float minY, maxY ;

    public SoftDrink(float x, float y, float speed, float minY, float maxY) {
        super("sodabottle.png", x, y, speed);
        this.minY = minY;
        this.maxY = maxY;
    }

    public void move(float deltaTime) {
    	setX(getX() - getSpeed() * deltaTime); // Move left based on current speed

    	 // Respawn if off-screen
        if (getX() < -50) { 
            float newX = Gdx.graphics.getWidth() + 50; // Respawn on the right
            float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100; // Randomize new Y position
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
