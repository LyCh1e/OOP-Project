package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.math.Rectangle;

public class Uncollidable {
	
	public static void noCollision(Entity e1, Entity e2) {
    	Rectangle rect1, rect2;
    	rect1 = CollisionManager.makeRectangle(e1);
    	rect2 = CollisionManager.makeRectangle(e2);
    	
        if (CollisionManager.isColliding(e1, e2)) {
        	// Calculate overlap
            float overlapX = Math.min(rect1.x + rect1.width - rect2.x, rect2.x + rect2.width - rect1.x);
            float overlapY = Math.min(rect1.y + rect1.height - rect2.y, rect2.y + rect2.height - rect1.y);
        	
            if (overlapX > overlapY) {
            	System.out.println("Bypassed Entity\n");
            }
            
            // Reset velocities to prevent further movement
            e1.setVelocityX(0);
            e1.setVelocityY(0);
            e2.setVelocityX(0);
            e2.setVelocityY(0);
        }
    }
}
