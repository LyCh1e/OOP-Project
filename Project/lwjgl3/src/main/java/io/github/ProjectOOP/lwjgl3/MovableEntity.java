package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MovableEntity extends Entity {
    private boolean isJumping = false;
    private float prevX, prevY; // Store previous spawn position

    MovableEntity() { }

    MovableEntity(String str, float x, float y, float s) {
        setX(x);
        setY(y);
        setSpeed(s);
        texture = new Texture(Gdx.files.internal(str));

        // Initialize previous position when entity is first created
        prevX = x;
        prevY = y;
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

    @Override
    public void updatePosition() {
        // Store previous position BEFORE modifying x and y
        prevX = getX();
        prevY = getY();

        System.out.println("Previous Spawn: X = " + prevX + ", Y = " + prevY);
        System.out.println("X position of movable entity = " + getX() + "\n");
        System.out.println("Y position of movable entity = " + getY() + "\n");
    }

    // Method to retrieve previous position if needed
    public float getPrevX() { return prevX; }
    public float getPrevY() { return prevY; }
}
