package io.github.ProjectOOP.lwjgl3;

import java.util.Random;

public class AIMovement {
    private Random random = new Random();
    private static final float MAX_SPEED = 8f;
    private static final float SCREEN_WIDTH = 1280f;
    
    public void moveBottom(Entity e) {
        moveInLayer(e, 0, 100);
    }
    
    public void moveMiddle(Entity e) {
        moveInLayer(e, 200, 300);
    }
    
    public void moveTop(Entity e) {
        moveInLayer(e, 400, 500);
    }
    
    private void moveInLayer(Entity e, float minY, float maxY) {
        float x = e.getX();
        float speed = e.getSpeed();
        e.setX(x - speed);
        
        if (e.getX() < 0) {
            resetPosition(e, minY, maxY);
        }
    }
    
    private void resetPosition(Entity e, float minY, float maxY) {
        float randomY = random.nextFloat(minY, maxY);
        e.setX(SCREEN_WIDTH);
        e.setY(randomY);
        
        float newSpeed = Math.min(e.getSpeed() * 2, MAX_SPEED);
        e.setSpeed(newSpeed);
    }
}
