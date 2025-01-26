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
}
