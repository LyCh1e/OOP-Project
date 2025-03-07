package ProjectOOP.Movement;

import ProjectOOP.Entity.Entity;

public class AIMovement {
    private static final float MAX_SPEED = 28f;
    private static final float SCREEN_WIDTH = 1280f;

    public static float topMinY = 400, topMaxY = 550;
    public static float middleMinY = 200, middleMaxY = 350;
    public static float bottomMinY = 0, bottomMaxY = 150;

    public static float leftMinX = 0, leftMaxX = 400;
    public static float middleMinX = 450, middleMaxX = 850;
    public static float rightMinX = 900, rightMaxX = 1280;

    public void moveBottomYAxis(Entity e, float stamina) {
        moveInLayerYAxis(e, bottomMinY, bottomMaxY, stamina);
    }

    public void moveMiddleYAxis(Entity e, float stamina) {
        moveInLayerYAxis(e, middleMinY, middleMaxY, stamina);
    }

    public void moveTopYAxis(Entity e, float stamina) {
        moveInLayerYAxis(e, topMinY, topMaxY, stamina);
    }

    public void moveBottomXAxis(Entity e, float stamina) {
        moveInLayerXAxis(e, leftMinX, leftMaxX, stamina);
    }

    public void moveMiddleXAxis(Entity e, float stamina) {
        moveInLayerXAxis(e, middleMinX, middleMaxX, stamina);
    }

    public void moveTopXAxis(Entity e, float stamina) {
        moveInLayerXAxis(e, rightMinX, rightMaxX, stamina);
    }

    private void moveInLayerYAxis(Entity e, float minY, float maxY, float stamina) {
        float x = e.getX();
        float speed = e.getSpeed();
        e.setX(x - speed);

        if (e.getX() < 0) {
            resetPositionYAxis(e, minY, maxY, stamina);
        }
    }

    private void moveInLayerXAxis(Entity e, float minX, float maxX, float stamina) {
        float y = e.getY();
        float speed = e.getSpeed();
        e.setY(y - speed);

        if (e.getY() < minX) {
            resetPositionXAxis(e, minX, maxX, stamina);
        }
    }

    private void resetPositionYAxis(Entity e, float minY, float maxY, float stamina) {
        e.respawn(SCREEN_WIDTH, SCREEN_WIDTH, minY, maxY); // AI respawns at the right side
        float newSpeed = getNewSpeed(stamina);
//        float newSpeed = Math.min(e.getSpeed() * 2, MAX_SPEED);
        e.setSpeed(newSpeed);
    }


    private void resetPositionXAxis(Entity e, float minX, float maxX, float stamina) {
        e.respawn(minX, maxX, SCREEN_WIDTH, SCREEN_WIDTH); // AI respawns at the top
        float newSpeed = getNewSpeed(stamina);
//        float newSpeed = Math.min(e.getSpeed() * 2, MAX_SPEED);
        e.setSpeed(newSpeed);
    }

    private float getNewSpeed(float stamina) {
    	float[] speeds = {22, 19, 16, 13, 10, 7, 4};
        int index = (int) Math.floor(stamina / 10);

    	// Ensure the index is within bounds
    	if (index < 0) {
    	    index = 0; // Handle negative stamina
    	} else if (index >= speeds.length) {
    	    index = speeds.length - 1; // Handle stamina greater than 60
    	}
        float newSpeed = Math.min(speeds[index], MAX_SPEED);
        return newSpeed;
    }
}
