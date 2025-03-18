package ProjectOOP.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class SoftDrink extends MovableEntity {
	private float minY, maxY; 
    private boolean movingUp = true;

    public SoftDrink(float x, float y, float speed, float minY, float maxY) {
        super("sodabottle.png", x, y, speed);
        this.minY = minY;
        this.maxY = maxY;
    }

    public void move(float deltaTime) {
    	setX(getX() - getSpeed() * deltaTime); // Move left based on current speed

        if (movingUp) {
            setY(getY() + speed * 0.5f * deltaTime);
            if (getY() >= maxY) movingUp = false;
        } else {
            setY(getY() - speed * 0.5f * deltaTime);
            if (getY() <= minY) movingUp = true;
        }

        if (getX() < -50) { 
            setX(Gdx.graphics.getWidth()); // Respawn at right
            // Y position remains unchanged (doesn't reset)
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
