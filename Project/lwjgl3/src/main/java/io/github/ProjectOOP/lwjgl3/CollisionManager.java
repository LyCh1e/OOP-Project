package io.github.ProjectOOP.lwjgl3;

public class CollisionManager {
	public void checkCollisions(Entity e1, Entity e2) {
		// Check for collision between bucket and drop
		if (isColliding(e1, e2)) {
			// Handle collision for now just console msg, in future can edit to more stuff
			System.out.println("Collision detected!");
		}
	}

	private boolean isColliding(Entity entity1, Entity entity2) {    	
		return entity1.getX() < entity2.getX() + entity2.getTexture().getWidth() &&
				entity1.getX() + entity1.getTexture().getWidth() > entity2.getX() &&
	            entity1.getY() < entity2.getY() + entity2.getTexture().getHeight() &&
	            entity1.getY() + entity1.getTexture().getHeight() > entity2.getY();
	}
}
