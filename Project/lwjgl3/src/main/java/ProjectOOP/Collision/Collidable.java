package ProjectOOP.Collision;

import com.badlogic.gdx.math.Rectangle;

import ProjectOOP.Entity.Entity;
import ProjectOOP.Entity.Platform;
import ProjectOOP.Entity.Player;

public class Collidable {
	
	public static void doCollision(Entity e1, Entity e2, boolean push) {
    	Rectangle rect1, rect2;
    	rect1 = CollisionManager.makeRectangle(e1);
    	rect2 = CollisionManager.makeRectangle(e2);
    	
        if (CollisionManager.isColliding(e1, e2)) {
        	float overlapX = Math.min(rect1.x + rect1.width - rect2.x, rect2.x + rect2.width - rect1.x);
        	float overlapY = Math.min(rect1.y + rect1.height - rect2.y, rect2.y + rect2.height - rect1.y);
        	
        	if (push == true) {
        		// Allows e2 to be pushed
        		if (overlapX < overlapY) {
                	if (e1.getX() < e2.getX()) {
                		e1.setX(e1.getX() - overlapX);
                        e2.setX(e2.getX() + overlapX / 2);
                    }
                	
                	else {
                        e1.setX(e1.getX() + overlapX);
                        e2.setX(e2.getX() - overlapX / 2);
                    }
                	System.out.println("Collided with entity");
                } 
                else {
                    if (e1.getY() < e2.getY()) {
                    	e1.setY(e1.getY() - overlapY);
                        e2.setY(e2.getY() + overlapY / 2);
                    } 
                    
                    else {
                        e1.setY(e1.getY() + overlapY);
                        e2.setY(e2.getY() - overlapY / 2);
                    }
                    System.out.println("Collided with entity");
                }
        	}
        	else if (push == false) {
        		// Push entities apart based on the smallest overlap
                if (overlapX < overlapY) {
                	if (e1.getX() < e2.getX()) {
                		e1.setX(e1.getX() - overlapX);
                    }
                	
                	else {
                        e1.setX(e1.getX() + overlapX);
                    }
                	System.out.println("Collided with entity");
                } 
                else {
                    if (e1.getY() < e2.getY()) {
                    	e1.setY(e1.getY() - overlapY);
                    } 
                    
                    else {
                        e1.setY(e1.getY() + overlapY);
                    }
                    System.out.println("Collided with entity");
                }
        	}
            // Reset velocities to prevent further movement
            e1.setVelocityX(0);
            e1.setVelocityY(0);
            e2.setVelocityX(0);
            e2.setVelocityY(0);
        }
	}
	
	public static void doBounceCollision(Entity e1, Entity e2) {
		// bouncing collision, the 2 entity will bounce off each other
        float e1CenterX = e1.getX() + e1.getTexture().getWidth() / 2;
        float e1CenterY = e1.getY() + e1.getTexture().getHeight() / 2;
        float e2CenterX = e2.getX() + e2.getTexture().getWidth() / 2;
        float e2CenterY = e2.getY() + e2.getTexture().getHeight() / 2;

        // Calculate collision normal
        float dx = e2CenterX - e1CenterX;
        float dy = e2CenterY - e1CenterY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
            
        if (distance != 0) {
        	// Normalize the direction
            dx /= distance;
            dy /= distance;

            //Assuming entities have velocity properties
            float bounceForce = 5f; // Adjust this value as needed
                
            // Apply opposite forces to entity e1 from e2
            e1.setVelocityX(-dx * bounceForce);
            e1.setVelocityY(-dy * bounceForce);
            // Apply opposite force to entity e2 from e1
            e2.setVelocityX(dx * bounceForce);
            e2.setVelocityY(dy * bounceForce);
        }
	}
	
	// New method to handle platform collision
	public static boolean doPlatformCollision(Player player, Platform platform) {
	    // Skip collision detection entirely if the player is jumping upward
	    if (player.isJumping() && player.getVelocityY() > 0) {
	        System.out.println("Skipping platform collision while jumping up");
	        return false;
	    }
	    
	    Rectangle playerRect = CollisionManager.makeRectangle(player);
	    Rectangle platformRect = CollisionManager.makeRectangle(platform);
	    
	    // Check if player is above platform and within horizontal bounds
	    if (playerRect.x + playerRect.width > platformRect.x && 
	        playerRect.x < platformRect.x + platformRect.width) {
	        
	        float playerBottom = player.getY();
	        float platformTop = platform.getY() + platform.getTexture().getHeight();
	        
	        // Only snap player to platform if they're falling onto it
	        if (playerBottom < platformTop && player.getVelocityY() <= 0) {
	            player.setY(platformTop);
	            player.setVelocityY(0);
	            player.land();
	            System.out.println("Landed on platform");
	            return true;
	        }
	    }
	    
	    return false;
	}

	public static boolean doSegmentedPlatformCollision(Player player, Platform platform, Rectangle segment) {
	    // Skip collision detection entirely if the player is jumping upward
	    if (player.isJumping() && player.getVelocityY() > 0) {
	        System.out.println("Skipping segmented platform collision while jumping up");
	        return false;
	    }
	    
	    Rectangle playerRect = CollisionManager.makeRectangle(player);
	    
	    // Check if player is above segment and within horizontal bounds
	    if (playerRect.x + playerRect.width > segment.x && 
	        playerRect.x < segment.x + segment.width) {
	        
	        float playerBottom = player.getY();
	        float segmentTop = segment.y + segment.height;
	        
	        // Only snap player to platform if they're falling onto it
	        if (playerBottom < segmentTop && player.getVelocityY() <= 0) {  
	            player.setY(segmentTop);
	            player.setVelocityY(0);
	            player.land();
	            System.out.println("Landed on platform segment");
	            return true;
	        }
	    }
	    
	    return false;
	}
}