package io.github.ProjectOOP.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager {
private List<Entity> entityList = new ArrayList<>();
	
	void addEntities(Entity e) {
		entityList.add(e);
	}
	
	void draw (SpriteBatch batch) {
		batch.begin();
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(batch);
			}
		batch.end();
	}
	
	public void movement() {
		
	}
	
    public void checkCollisions() {
        // Assuming the first entity is the bucket and the second is the drop
        if (entityList.size() >= 2) {
            Entity bucket = entityList.get(0); // Bucket
            Entity drop = entityList.get(1);   // Drop

            // Check for collision between bucket and drop
            if (isColliding(bucket, drop)) {
                // Handle collision for now just console msg, in future can edit to more stuff
                System.out.println("Collision detected!");
            }
        }
    }

    private boolean isColliding(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getX() + entity2.getTexture().getWidth() &&
               entity1.getX() + entity1.getTexture().getWidth() > entity2.getX() &&
               entity1.getY() < entity2.getY() + entity2.getTexture().getHeight() &&
               entity1.getY() + entity1.getTexture().getHeight() > entity2.getY();
    }
}
