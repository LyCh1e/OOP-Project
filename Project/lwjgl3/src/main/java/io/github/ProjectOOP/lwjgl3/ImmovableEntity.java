package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ImmovableEntity extends Entity {
    private float prevX, prevY; // Store previous spawn position

    ImmovableEntity() { }

    ImmovableEntity(String str, float x, float y, float s) {
        setX(x);
        setY(y);
        setSpeed(s);
        texture = new Texture(Gdx.files.internal(str));

        // Initialize previous position when entity is first created
        prevX = x;
        prevY = y;
    }

    public void moveLeft() {
        velocityX = 0;
        System.out.println("Moving Left");
    }

    public void moveRight() {
        velocityX = 0;
        System.out.println("Moving Right");
    }

    public void stopMoving() {
        velocityX = 0;
        System.out.println("Stopped Moving");
    }

    @Override
    public void updatePosition() {
        // Store previous position BEFORE modifying x and y
        prevX = getX();
        prevY = getY();

        System.out.println("Previous Spawn: X = " + prevX + ", Y = " + prevY);
        System.out.println("Current Position: X = " + getX() + ", Y = " + getY());
    }

    // Method to retrieve previous position if needed
    public float getPrevX() { return prevX; }
    public float getPrevY() { return prevY; }
}
