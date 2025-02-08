package io.github.ProjectOOP.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager {
private List<Entity> entityList = new ArrayList<>();
	
	public void addEntities(Entity e) {
		entityList.add(e);
	}
	
	public void draw(SpriteBatch batch) {;
		batch.begin();
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(batch);
			}
		batch.end();
	}
	
	// TODO: Populate this list with multiple entities
	// TODO: Create a method to instantiate multiple entities
	public List<Entity> instantializeEntities(List<EntityConfig> configs) {
		List<Entity> entities = new ArrayList<>();
	    
	    for (EntityConfig config : configs) {
	        Entity e;
	        
	        if (config.getMovable()) {
	            e = new MovableEntity(config.getTexture(), config.getX(), config.getY(), config.getSpeed());
	        } else {
	            e = new ImmovableEntity(config.getTexture(), config.getX(), config.getY(), config.getSpeed());
	        }
	        
	        entities.add(e);
	        addEntities(e);
	    }
	    
	    return entities;
    }
}
