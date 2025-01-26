package io.github.ProjectOOP.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter{
	private SpriteBatch batch;
	
	private EntityManager entityManager;
	private SceneManager sceneManager;
	private CollisionManager collisionManager;
	private MovementManager movementManager;
	private IOManager ioManager;
	
	private Entity entity;
	private Entity drop;
	private Scene scene;
	
	public void create() {
		Random random = new Random();
		float randomY = random.nextFloat(0, 50);
		float randomX = random.nextFloat(1200, 1280);
		
		batch = new SpriteBatch();
		
		entityManager = new EntityManager();
		sceneManager = new SceneManager();
		collisionManager = new CollisionManager();
		movementManager = new MovementManager();
		ioManager = new IOManager();
		
		entity = new Entity("bucket.png", 10, 0, 0);
		drop = new Entity("droplet.png", randomX, randomY, 2);
		scene = new Scene("background.png", 0, 0);
		
		/*Type code bellow this comment*/	
		entityManager.addEntities(drop);
		entityManager.addEntities(entity);
		sceneManager.addScenes(scene);
	}
	
	public void render () {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		sceneManager.loadScene(batch);
		entityManager.draw(batch);
		
		entity.movement();
		drop.AIMovment();
		
		entityManager.checkCollisions();
	}
	
	public void dispose() {
		batch.dispose();
	}
	
}
