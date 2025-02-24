package ProjectOOP.Collision;

import com.badlogic.gdx.math.Rectangle;

import ProjectOOP.Entity.Entity;

public class CollisionManager {
	
	protected static Rectangle makeRectangle(Entity e) {
		Rectangle rect = new Rectangle(e.getX(), 
				e.getY(), 
				e.getTexture().getWidth(), 
				e.getTexture().getHeight());
		
		return rect;
	}
	
	protected static boolean isColliding(Entity entity1, Entity entity2) {
		// Using LibGDX's Rectangle for more accurate collision detection
		Rectangle rect1, rect2;
		rect1 = makeRectangle(entity1);
		rect2 = makeRectangle(entity2);
		
		boolean colliding = rect1.overlaps(rect2);
		
		return colliding;
	}
	
    public boolean checkCollisions(Entity e1, Entity e2) {
        if (isColliding(e1, e2)) {
            return true;
        }
        else {
        	return false;
        }
    }
}
