package ProjectOOP.Movement;

import com.badlogic.gdx.Gdx;

import ProjectOOP.Entity.Entity;
import ProjectOOP.Entity.Player;
import ProjectOOP.IO.IOManager;
import ProjectOOP.Scene.SceneManager;

public class UserMovement{
    private static final float MOVEMENT_SPEED = 300f;
    private static final float JUMP_VELOCITY = 200f;
    private static final float GRAVITY = -980f;
    
    private static final float x_leftLimit = 0;
    private static final float x_rightLimit = 1210;
    
    private float verticalVelocity = 4;
    private IOManager ioManager;
    
    UserMovement(IOManager ioM) {
    	ioManager = ioM;
    }
    
    public void move(Entity e, SceneManager.STATE currentState) {
        handleHorizontalMovement(e);
        handleVerticalMovement(e, currentState); // Pass currentState to vertical movement testing
        enforceBoundaries(e);
    }
    
    private void handleHorizontalMovement(Entity e) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        if (ioManager.isMovingLeft()) {
            e.updateX(e.retrieveX() - MOVEMENT_SPEED * deltaTime);
        }
        
        if (ioManager.isMovingRight()) {
            e.updateX(e.retrieveX() + MOVEMENT_SPEED * deltaTime);
        }
    }
    
    private void handleVerticalMovement(Entity e, SceneManager.STATE currentState) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        verticalVelocity = GRAVITY * deltaTime;
        
        if (currentState == SceneManager.STATE.Start && ioManager.isJumping()) {
            verticalVelocity = JUMP_VELOCITY;
        }
        e.updateY(e.retrieveY() + verticalVelocity * deltaTime);
    }
    
    private void enforceBoundaries(Entity e) {
        // Horizontal boundaries
        if (e.retrieveX() < x_leftLimit) {
            e.updateX(x_leftLimit);
        } else if (e.retrieveX() > x_rightLimit) {
            e.updateX(x_rightLimit);
        }
    }
}