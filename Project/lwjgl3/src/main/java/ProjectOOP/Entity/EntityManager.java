package ProjectOOP.Entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
	private List<Entity> entityList = new ArrayList<>();
	
	private EntityManager(){
		
	}
	
    private static EntityManager instance;

	
	public static synchronized EntityManager getInstance() {
	    if (instance == null) {
	        instance = new EntityManager();
	    }
	    return instance;
	}

	public void addEntities(Entity e) {
		entityList.add(e);
	}
	
	public void removeEntity(Entity e) {
		entityList.remove(e); // Remove from the list
	}
	
	public void draw(SpriteBatch batch) {
		// Same way as I did in Lab, can see if got any better ways
		batch.begin();
		for (int i = 0; i < entityList.size(); i++) {
			if (!(entityList.get(i) instanceof SpeedBar)) entityList.get(i).draw(batch);
		}
		batch.end();
	}
	
	public void draw(ShapeRenderer shape) {
		// Same way as I did in Lab, can see if got any better ways
		shape.begin(ShapeRenderer.ShapeType.Filled);
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i) instanceof SpeedBar) entityList.get(i).draw(shape);
		}
		shape.end();
	}
	
	public List<Entity> getEntityList() {
	    return entityList;
	}
}
