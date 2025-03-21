package ProjectOOP.Movement;

import ProjectOOP.Entity.Entity;
import ProjectOOP.Entity.MovableEntity;
import ProjectOOP.Entity.Player;
import ProjectOOP.IO.IOManager;
import ProjectOOP.Scene.SceneManager;

public class MovementManager {
    private final UserMovement userMovement;
    private final AIMovement aiMovement;
    private final SceneManager sceneManager;
    private final IOManager iom;

    private static MovementManager instance;

	
	public static synchronized MovementManager getInstance(IOManager ioManager, SceneManager sm) {
	    if (instance == null) {
	        instance = new MovementManager(ioManager, sm);
	    }
	    return instance;
	}
    
    public enum Y_Column {
        BOTTOM, MIDDLE, TOP
    }
    
    public enum X_Row {
        LEFT, MIDDLE, RIGHT
    }
    
    private MovementManager(IOManager ioManager, SceneManager sm) {
        userMovement = new UserMovement(ioManager);
        aiMovement = new AIMovement();
        sceneManager = sm;
        iom = ioManager;
    }
    
    public void updateUserMovement(MovableEntity entity, SceneManager.STATE state) {
        if (state == SceneManager.STATE.Start) {
            if (iom.isMovingLeft()) {
                entity.moveLeft();
            } else if (iom.isMovingRight()) {
                entity.moveRight();
            } else {
                entity.stopMoving();
            }
            
            userMovement.move(entity, state);
            
            // For jumping, only trigger if not already jumping
            if (iom.isJumping() && entity instanceof Player) {
                Player player = (Player) entity;
                if (!player.isJumping()) {
                    player.jump();
                    System.out.println("Jump initiated in MovementManager");
                }
            }
            
            entity.updatePosition();
        }
    }
    
    public void updateAIMovementYAxis(Entity entity, Y_Column layer) {
        switch (layer) {
            case BOTTOM:
                aiMovement.moveBottomYAxis(entity);
                break;
            case MIDDLE:
                aiMovement.moveMiddleYAxis(entity);
                break;
            case TOP:
                aiMovement.moveTopYAxis(entity);
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