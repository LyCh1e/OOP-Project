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
            
        	// bouncing collision, the 2 entity will bounce off each other
            //float e1CenterX = e1.getX() + e1.getTexture().getWidth() / 2;
            //float e1CenterY = e1.getY() + e1.getTexture().getHeight() / 2;
            //float e2CenterX = e2.getX() + e2.getTexture().getWidth() / 2;
            //float e2CenterY = e2.getY() + e2.getTexture().getHeight() / 2;

            // Calculate collision normal
            //float dx = e2CenterX - e1CenterX;
            //float dy = e2CenterY - e1CenterY;
            //float distance = (float) Math.sqrt(dx * dx + dy * dy);
                
            //if (distance != 0) {
            	// Normalize the direction
                //dx /= distance;
                //dy /= distance;

                // Assuming entities have velocity properties
                //float bounceForce = 5f; // Adjust this value as needed
                    
                // Apply opposite forces to entity e1 from e2
                //e1.setVelocityX(-dx * bounceForce);
                //e1.setVelocityY(-dy * bounceForce);
                // Apply opposite force to entity e2 from e1
                //e2.setVelocityX(dx * bounceForce);
                //e2.setVelocityY(dy * bounceForce);
            //}
                
            // Reset velocities to prevent further movement
            e1.setVelocityX(0);
            e1.setVelocityY(0);
            e2.setVelocityX(0);
            e2.setVelocityY(0);
        }
    }
}
