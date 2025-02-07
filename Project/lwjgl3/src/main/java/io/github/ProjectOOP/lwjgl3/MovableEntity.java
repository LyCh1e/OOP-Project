package io.github.ProjectOOP.lwjgl3;

public class MovableEntity extends Entity{
    private float speed = 200f; // Example speed
    private float velocityX = 0f;
    private boolean isJumping = false;

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
