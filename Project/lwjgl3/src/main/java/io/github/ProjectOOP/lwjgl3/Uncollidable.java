package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.math.Rectangle;

public class Uncollidable {
	
	public void noCollision(Entity e1, Entity e2, boolean collide) {
    	Rectangle rect1, rect2;
    	rect1 = CollisionManager.makeRectangle(e1);
    	rect2 = CollisionManager.makeRectangle(e2);
    	
        if (CollisionManager.isColliding(e1, e2)) {
            if (collide == false) {
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

                    // Assuming entities have velocity properties
                    float bounceForce = 5f; // Adjust this value as needed
                    
                    // Apply opposite forces to entity e1 from e2
                    e1.setVelocityX(-dx * bounceForce);
                    e1.setVelocityY(-dy * bounceForce);
                    // Apply opposite force to entity e2 from e1
                    e2.setVelocityX(dx * bounceForce);
                    e2.setVelocityY(dy * bounceForce);
                }
            } 
            else if (collide == true){
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
                    } else {
                        e1.setX(e1.getX() + overlapX);
                        //e2.setX(e2.getX() - overlapX / 2); un-comment if you want e2 to move as well
                    }
                } else {
                    if (e1.getY() < e2.getY()) {
                        e1.setY(e1.getY() - overlapY);
                        //e2.setY(e2.getY() + overlapY / 2); un-comment if you want e2 to move as well
                    } else {
                        e1.setY(e1.getY() + overlapY);
                        //e2.setY(e2.getY() - overlapY / 2); un-comment if you want e2 to move as well
                    }
                }
                
                // Reset velocities to prevent further movement
                e1.setVelocityX(0);
                e1.setVelocityY(0);
                e2.setVelocityX(0);
                e2.setVelocityY(0);
            }
        }
    }
}
