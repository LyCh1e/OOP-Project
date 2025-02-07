package io.github.ProjectOOP.lwjgl3;

public class MovementManager {
    private final UserMovement userMovement;
    private final AIMovement aiMovement;
    
    public enum Y_Column {
        BOTTOM, MIDDLE, TOP
    }
    
    public enum X_Row {
        LEFT, MIDDLE, RIGHT
    }
    
    public MovementManager(IOManager ioManager) {
        userMovement = new UserMovement(ioManager);
        aiMovement = new AIMovement();
    }
    
    public void updateUserMovement(Entity entity) {
        userMovement.move(entity);
    }
    
    public void updateAIMovementYAxis(Entity entity, Y_Column layer) {
        switch (layer) {
            case BOTTOM:
                aiMovement.moveBottom(entity);
                break;
            case MIDDLE:
                aiMovement.moveMiddle(entity);
                break;
            case TOP:
                aiMovement.moveTop(entity);
                break;
        }
    }
    public void updateAIMovementXAxis(Entity entity, X_Row layer) {
        switch (layer) {
            case LEFT:
                aiMovement.moveBottomXAxis(entity);
                break;
            case MIDDLE:
                aiMovement.moveMiddleXAxis(entity);
                break;
            case RIGHT:
                aiMovement.moveTopXAxis(entity);
                break;
        }
    }
}