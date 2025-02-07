package io.github.ProjectOOP.lwjgl3;

import java.util.Random;
import com.badlogic.gdx.Gdx;

public class MovementManager {
    private IOManager ioManager;
    
    float x_leftLimit = 0;
    float x_rightLimit = 1210;
    float y_bottomLimit = 0;
    
    float gravity = -980f;
    float verticalVelocity = 4;
    
    public MovementManager(IOManager ioM) {
        ioManager = ioM;
    }
    
    public void userMovement(Entity e) {
        if (ioManager.isMovingLeft()) {
            e.setX(e.getX() - 300 * Gdx.graphics.getDeltaTime());
        }
        
        if (ioManager.isMovingRight()) {
            e.setX(e.getX() + 300 * Gdx.graphics.getDeltaTime());
        }

        verticalVelocity += gravity * Gdx.graphics.getDeltaTime();

        if (ioManager.isJumping() && e.getY() <= y_bottomLimit) {
            verticalVelocity = 500;
        }

        e.setY(e.getY() + verticalVelocity * Gdx.graphics.getDeltaTime());

        if (e.getX() < x_leftLimit) {
            e.setX(x_leftLimit);
        }
        else if (e.getX() > x_rightLimit) {
            e.setX(x_rightLimit);
        }

        if (e.getY() < y_bottomLimit) {
            e.setY(y_bottomLimit);
            verticalVelocity = 0;
        }
    }
	
	public void AIMovementBottom(Entity e) { //movement of entity from right to left
		Random random = new Random();
		float randomY = random.nextFloat(0, 100);
		float x = e.getX();
		float speed = e.getSpeed();
		e.setX(x -= speed);
		if (e.getX() < 0) {
			e.setX(1280);
			e.setY(randomY);
			e.setSpeed(speed += speed);
			if (e.getSpeed() > 8) {
				e.setSpeed(8);
			}
		}
	}
	
	public void AIMovementMiddle(Entity e) { //movement of entity from right to left
		Random random = new Random();
		float randomY = random.nextFloat(200, 300);
		float x = e.getX();
		float speed = e.getSpeed();
		e.setX(x -= speed);
		if (e.getX() < 0) {
			e.setX(1280);
			e.setY(randomY);
			e.setSpeed(speed += speed);
			if (e.getSpeed() > 8) {
				e.setSpeed(8);
			}
		}
	}
	
	public void AIMovementTop(Entity e) { //movement of entity from right to left
		Random random = new Random();
		float randomY = random.nextFloat(400, 500);
		float x = e.getX();
		float speed = e.getSpeed();
		e.setX(x -= speed);
		if (e.getX() < 0) {
			e.setX(1280);
			e.setY(randomY);
			e.setSpeed(speed += speed);
			if (e.getSpeed() > 8) {
				e.setSpeed(8);
			}
		}
	}
}
