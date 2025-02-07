package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.math.Rectangle;

public class Collidable {
	
	public static void doCollision(Entity e1, Entity e2) {
    	Rectangle rect1, rect2;
    	rect1 = CollisionManager.makeRectangle(e1);
    	rect2 = CollisionManager.makeRectangle(e2);
    	
        if (CollisionManager.isColliding(e1, e2)) {
        	// Stop entities from moving through each other
            // Calculate overlap and push entities apart

            // Calculate overlap
            float overlapX = Math.min(rect1.x + rect1.width - rect2.x, rect2.x + rect2.width - rect1.x);
            float overlapY = Math.min(rect1.y + rect1.height - rect2.y, rect2.y + rect2.height - rect1.y);

            // Push entities apart based on the smallest overlap
            if (overlapX < overlapY) {
            	if (e1.getX() < e2.getX()) {
            		e1.setX(e1.getX() - overlapX);
                    //e2.setX(e2.getX() + overlapX / 2); un-comment if you want e2 to move as well
                }
            	
            	else {
                    e1.setX(e1.getX() + overlapX);
                    //e2.setX(e2.getX() - overlapX / 2); un-comment if you want e2 to move as well
                }
            	System.out.println("Collided with entity");
            } 
            else {
                if (e1.getY() < e2.getY()) {
                	e1.setY(e1.getY() - overlapY);
                    //e2.setY(e2.getY() + overlapY / 2); un-comment if you want e2 to move as well
                } 
                
                else {
                    e1.setY(e1.getY() + overlapY);
                    //e2.setY(e2.getY() - overlapY / 2); un-comment if you want e2 to move as well
                }
                System.out.println("Collided with entity");
            }
                
            // Reset velocities to prevent further movement
            e1.setVelocityX(0);
            e1.setVelocityY(0);
            e2.setVelocityX(0);
            e2.setVelocityY(0);
        }
	}
}
