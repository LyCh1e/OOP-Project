package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;

public class UserMovement{
    private static final float MOVEMENT_SPEED = 300f;
    private static final float JUMP_VELOCITY = 500f;
    private static final float GRAVITY = -980f;
    
    private final float x_leftLimit = 0;
    private final float x_rightLimit = 1210;
    private final float y_bottomLimit = 0;
    
    private float verticalVelocity = 4;
    private final IOManager ioManager;
    
    UserMovement(IOManager ioM) {
    	ioManager = ioM;
    }
    
    public void move(Entity e) {
        handleHorizontalMovement(e);
        handleVerticalMovement(e);
        enforceBoundaries(e);
    }
    
    private void handleHorizontalMovement(Entity e) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        if (ioManager.isMovingLeft()) {
            e.setX(e.getX() - MOVEMENT_SPEED * deltaTime);
        }
        
        if (ioManager.isMovingRight()) {
            e.setX(e.getX() + MOVEMENT_SPEED * deltaTime);
        }
    }
    
    private void handleVerticalMovement(Entity e) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        verticalVelocity += GRAVITY * deltaTime;
        
        if (ioManager.isJumping() && e.getY() <= y_bottomLimit) {
            verticalVelocity = JUMP_VELOCITY;
        }
        
        e.setY(e.getY() + verticalVelocity * deltaTime);
    }
    
    private void enforceBoundaries(Entity e) {
        // Horizontal boundaries
        if (e.getX() < x_leftLimit) {
            e.setX(x_leftLimit);
        } else if (e.getX() > x_rightLimit) {
            e.setX(x_rightLimit);
        }
        
        // Vertical boundaries
        if (e.getY() < y_bottomLimit) {
            e.setY(y_bottomLimit);
            verticalVelocity = 0;
        }
    }
}