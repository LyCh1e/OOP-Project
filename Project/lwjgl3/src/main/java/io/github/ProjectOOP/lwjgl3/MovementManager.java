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
        this.userMovement = new UserMovement(ioManager);
        this.aiMovement = new AIMovement();
    }
    
    public void updateUserMovement(Entity entity) {
        userMovement.move(entity);
    }
    
    public void updateAIMovement(Entity entity, Y_Column layer) {
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
}