package io.github.ProjectOOP.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter{
	private SpriteBatch batch;
	
	private EntityManager entityManager;
	private SceneManager sceneManager;
	private CollisionManager collisionManager;
	private MovementManager movementManager;
	private IOManager ioManager;
	
	private Entity entity;
	private Scene scene;
	
	public void create() {
		batch = new SpriteBatch();
		
		entityManager = new EntityManager();
		sceneManager = new SceneManager();
		collisionManager = new CollisionManager();
		movementManager = new MovementManager();
		ioManager = new IOManager();
		
		entity = new Entity();
		scene = new Scene();
		
		entityManager.addEntities(entity);
		
		
		/*Type code bellow this comment*/
	}
	
	public void render () {
		entityManager.draw(batch);
	}
	
	public void dispose() {
		batch.dispose();
	}
	
}
