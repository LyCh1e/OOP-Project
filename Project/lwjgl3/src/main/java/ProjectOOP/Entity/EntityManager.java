package ProjectOOP.Entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
private List<Entity> entityList = new ArrayList<>();
	
	public EntityManager(){
		
	}

	public void addEntities(Entity e) {
		entityList.add(e);
	}
	
	public void draw(SpriteBatch batch, ShapeRenderer shape) {
		// Same way as I did in Lab, can see if got any better ways
		shape.begin(ShapeRenderer.ShapeType.Filled);
		for (int i = 0; i < entityList.size(); i++) {
			if (entityList.get(i) instanceof SpeedBar) entityList.get(i).draw(shape);
		}
		shape.end();
		
		batch.begin();
		for (int i = 0; i < entityList.size(); i++) {
			if (!(entityList.get(i) instanceof SpeedBar)) entityList.get(i).draw(batch);
		}
		batch.end();
	}
}
