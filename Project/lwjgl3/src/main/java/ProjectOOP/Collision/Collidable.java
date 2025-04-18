package ProjectOOP.Collision;
import com.badlogic.gdx.math.Rectangle;

import ProjectOOP.Entity.Entity;
import ProjectOOP.Entity.Platform;
import ProjectOOP.Entity.Player;
import ProjectOOP.IO.Input;

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
                	if (e1.retrieveX() < e2.retrieveX()) {
                		e1.updateX(e1.retrieveX() - overlapX);
                        e2.updateX(e2.retrieveX() + overlapX / 2);
                    }
                	
                	else {
                        e1.updateX(e1.retrieveX() + overlapX);
                        e2.updateX(e2.retrieveX() - overlapX / 2);
                    }
                	System.out.println("Collided with entity");
                } 
                else {
                    if (e1.retrieveY() < e2.retrieveY()) {
                    	e1.updateY(e1.retrieveY() - overlapY);
                        e2.updateY(e2.retrieveY() + overlapY / 2);
                    } 
                    
                    else {
                        e1.updateY(e1.retrieveY() + overlapY);
                        e2.updateY(e2.retrieveY() - overlapY / 2);
                    }
                    System.out.println("Collided with entity");
                }
        	}
        	else if (push == false) {
        		// Push entities apart based on the smallest overlap
                if (overlapX < overlapY) {
                	if (e1.retrieveX() < e2.retrieveX()) {
                		e1.updateX(e1.retrieveX() - overlapX);
                    }
                	
                	else {
                        e1.updateX(e1.retrieveX() + overlapX);
                    }
                	System.out.println("Collided with entity");
                } 
                else {
                    if (e1.retrieveY() < e2.retrieveY()) {
                    	e1.updateY(e1.retrieveY() - overlapY);
                    } 
                    
                    else {
                        e1.updateY(e1.retrieveY() + overlapY);
                    }
                    System.out.println("Collided with entity");
                }
        	}
            // Reset velocities to prevent further movement
            e1.updateVelocityX(0);
            e1.updateVelocityY(0);
            e2.updateVelocityX(0);
            e2.updateVelocityY(0);
        }
	}
	
	public static void doBounceCollision(Entity e1, Entity e2) {
		// bouncing collision, the 2 entity will bounce off each other
        float e1CenterX = e1.retrieveX() + e1.getTexture().getWidth() / 2;
        float e1CenterY = e1.retrieveY() + e1.getTexture().getHeight() / 2;
        float e2CenterX = e2.retrieveX() + e2.getTexture().getWidth() / 2;
        float e2CenterY = e2.retrieveY() + e2.getTexture().getHeight() / 2;

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
            e1.updateVelocityX(-dx * bounceForce);
            e1.updateVelocityY(-dy * bounceForce);
            // Apply opposite force to entity e2 from e1
            e2.updateVelocityX(dx * bounceForce);
            e2.updateVelocityY(dy * bounceForce);
        }
	}
	
	// New method to handle platform collision
	public static boolean doPlatformCollision(Player player, Platform platform) {
	    // Skip collision detection entirely if the player is jumping upward
	    if ((player.isJumping() && player.retrieveVelocityY() > 0) || Input.dropDown()) {
	        System.out.println("Skipping platform collision while jumping up");
	        return false;
	    }
	    
	    Rectangle playerRect = CollisionManager.makeRectangle(player);
	    Rectangle platformRect = CollisionManager.makeRectangle(platform);
	    
	    // Check if player is above platform and within horizontal bounds
	    if (playerRect.x + playerRect.width > platformRect.x && 
	        playerRect.x < platformRect.x + platformRect.width) {
	        
	        float playerBottom = player.retrieveY();
	        float platformTop = platform.retrieveY() + platform.getTexture().getHeight();
	        
	        // Only snap player to platform if they're falling onto it
	        if (playerBottom < platformTop) {
	            player.updateY(platformTop);
	            player.updateVelocityY(0);
	            player.land();
	            System.out.println("Landed on platform");
	            return true;
	        }
	    }
	    
	    return false;
	}

	public static boolean doSegmentedPlatformCollision(Player player, Platform platform, Rectangle segment) {
	    // Skip collision detection entirely if the player is jumping upward
	    if ((player.isJumping() && player.retrieveVelocityY() > 0) || Input.dropDown()) {
	        System.out.println("Skipping segmented platform collision while jumping up");
	        return false;
	    }
	    
	    Rectangle playerRect = CollisionManager.makeRectangle(player);
	    
	    // Check if player is above segment and within horizontal bounds
	    if (playerRect.x + playerRect.width > segment.x && 
	        playerRect.x < segment.x + segment.width) {
	        
	        float playerBottom = player.retrieveY(); // lowest pixle of character
	        float segmentTop = segment.y + segment.height;
	        
	        // Only snap player to platform if they're falling onto it
	        if (playerBottom < segmentTop) {  
	        	if (player.retrieveVelocityY() > 0) {
	        		return false;
	        	}
	        	player.updateY(segmentTop);
	            player.land();
	            System.out.println("Landed on platform segment");
	            return true;
	        }
	        
	    }
	    
	    return false;
	}
}