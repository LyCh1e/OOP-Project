package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MovableEntity extends Entity{
    private float speed = 200f; // Example speed
    private boolean isJumping = false;

	MovableEntity (){
		
	}
	
	MovableEntity (String str, float x, float y, float s){
		setX(x);
		setY(y);
		setSpeed(s);
		texture = new Texture(Gdx.files.internal(str));
	}
    
    public void moveLeft() {
        velocityX = -speed;
        System.out.println("Moving Left");
    }

    public void moveRight() {
        velocityX = speed;
        System.out.println("Moving Right");
    }

    public void stopMoving() {
        velocityX = 0;
        System.out.println("Stopped Moving");
    }

    public void jump() {
        if (!isJumping) { // Prevent double jumping
            isJumping = true;
            System.out.println("Jumping");
            // Add jumping physics here
        }
    }
    
    // TODO: Create a method to instantiate multiple entities

    public void land() {
        isJumping = false;
    }

	@Override
	void updatePosition() {
		// TODO print out updated location
		
	}

	@Override
	public void movement() {
		// TODO Auto-generated method stub
		
	}
}
