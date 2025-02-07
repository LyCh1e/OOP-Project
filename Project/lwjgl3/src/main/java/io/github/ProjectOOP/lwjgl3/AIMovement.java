package io.github.ProjectOOP.lwjgl3;

import java.util.Random;

public class AIMovement {
    private static final float MAX_SPEED = 8f;
    private static final float SCREEN_WIDTH = 1280f;
    
    public static float topMinY = 400, topMaxY = 550;
    public static float middleMinY= 200, middleMaxY = 350;
    public static float bottomMinY = 0, bottomMaxY = 150; 
    
    public static float leftMinX= 0, leftMaxX = 400;
    public static float middlieMinX= 450, middleMaxX = 850;
    public static float rightMinX= 900, rightMaxX = 1280;
    
    private Random random = new Random();
    
    public void moveBottom(Entity e) {
        moveInLayer(e, bottomMinY, bottomMaxY);
    }
    
    public void moveMiddle(Entity e) {
        moveInLayer(e, middleMinY, middleMaxY);
    }
    
    public void moveTop(Entity e) {
        moveInLayer(e, topMinY, topMaxY);
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
