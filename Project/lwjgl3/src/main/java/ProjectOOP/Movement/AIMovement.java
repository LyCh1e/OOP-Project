package ProjectOOP.Movement;

import ProjectOOP.Entity.Entity;

public class AIMovement {
    private static final float MAX_SPEED = 8f;
    private static final float SCREEN_WIDTH = 1280f;

    public static float topMinY = 400, topMaxY = 550;
    public static float middleMinY = 200, middleMaxY = 350;
    public static float bottomMinY = 0, bottomMaxY = 150;

    public static float leftMinX = 0, leftMaxX = 400;
    public static float middleMinX = 450, middleMaxX = 850;
    public static float rightMinX = 900, rightMaxX = 1280;

    public void moveBottomYAxis(Entity e) {
        moveInLayerYAxis(e, bottomMinY, bottomMaxY);
    }

    public void moveMiddleYAxis(Entity e) {
        moveInLayerYAxis(e, middleMinY, middleMaxY);
    }

    public void moveTopYAxis(Entity e) {
        moveInLayerYAxis(e, topMinY, topMaxY);
    }

    public void moveBottomXAxis(Entity e) {
        moveInLayerXAxis(e, leftMinX, leftMaxX);
    }

    public void moveMiddleXAxis(Entity e) {
        moveInLayerXAxis(e, middleMinX, middleMaxX);
    }

    public void moveTopXAxis(Entity e) {
        moveInLayerXAxis(e, rightMinX, rightMaxX);
    }

    private void moveInLayerYAxis(Entity e, float minY, float maxY) {
        float x = e.retrieveX();
        float speed = e.retrieveSpeed();
        e.updateX(x - speed);

        if (e.retrieveX() < 0) {
            resetPositionYAxis(e, minY, maxY);
        }
    }

    private void moveInLayerXAxis(Entity e, float minX, float maxX) {
        float y = e.retrieveY();
        float speed = e.retrieveSpeed();
        e.updateY(y - speed);

        if (e.retrieveY() < minX) {
            resetPositionXAxis(e, minX, maxX);
        }
    }

    private void resetPositionYAxis(Entity e, float minY, float maxY) {
        e.respawn(SCREEN_WIDTH, SCREEN_WIDTH, minY, maxY); // AI respawns at the right side
        float newSpeed = Math.min(e.retrieveSpeed() * 2, MAX_SPEED);
        e.updateSpeed(newSpeed);
    }


    private void resetPositionXAxis(Entity e, float minX, float maxX) {
        e.respawn(minX, maxX, SCREEN_WIDTH, SCREEN_WIDTH); // AI respawns at the top
        float newSpeed = Math.min(e.retrieveSpeed() * 2, MAX_SPEED);
        e.updateSpeed(newSpeed);
    }
}
