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
}
