package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ImmovableEntity extends Entity{
	private float speed = 200f; // Example speed
    private boolean isJumping = false;

	ImmovableEntity (){
		
	}
	
	ImmovableEntity (String str, float x, float y, float s){
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

    public void land() {
        isJumping = false;
    }
}
