package ProjectOOP.Movement;

import ProjectOOP.Entity.Entity;
import ProjectOOP.IO.IOManager;
import ProjectOOP.Scene.SceneManager;

public class MovementManager {
    private final UserMovement userMovement;
    private final AIMovement aiMovement;
    private final SceneManager sceneManager;
    
    public enum Y_Column {
        BOTTOM, MIDDLE, TOP
    }
    
    public enum X_Row {
        LEFT, MIDDLE, RIGHT
    }
    
    public MovementManager(IOManager ioManager, SceneManager sm) {
        userMovement = new UserMovement(ioManager);
        aiMovement = new AIMovement();
        sceneManager = sm;
    }
    
    public void updateUserMovement(Entity entity, SceneManager.STATE currentState) {
    	 userMovement.move(entity, currentState);
    }
    
    public void updateAIMovementYAxis(Entity entity, Y_Column layer, float stamina) {
        switch (layer) {
            case BOTTOM:
                aiMovement.moveBottomYAxis(entity, stamina);
                break;
            case MIDDLE:
                aiMovement.moveMiddleYAxis(entity, stamina);
                break;
            case TOP:
                aiMovement.moveTopYAxis(entity, stamina);
                break;
        }
    }
    public void updateAIMovementXAxis(Entity entity, X_Row layer, float stamina) {
        switch (layer) {
            case LEFT:
                aiMovement.moveBottomXAxis(entity, stamina);
                break;
            case MIDDLE:
                aiMovement.moveMiddleXAxis(entity, stamina);
                break;
            case RIGHT:
                aiMovement.moveTopXAxis(entity, stamina);
                break;
        }
    }
}