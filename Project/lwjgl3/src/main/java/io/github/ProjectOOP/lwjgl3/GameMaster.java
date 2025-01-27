package io.github.ProjectOOP.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMaster extends ApplicationAdapter {
    private SpriteBatch batch;
    private EntityManager entityManager;
    private SceneManager sceneManager;
    private CollisionManager collisionManager;
    private MovementManager movementManager;
    private IOManager ioManager;
    private Entity entity, drop, heart1, heart2, heart3;
    private Scene scene;
    private PauseMenuScene pauseMenuScene;
    private boolean isPaused = false;

    public void create() {
        Random random = new Random();
        float randomY = random.nextFloat(0, 50);
        float randomX = random.nextFloat(1260, 1280);

        batch = new SpriteBatch();
        entityManager = new EntityManager();
        sceneManager = new SceneManager();
        collisionManager = new CollisionManager();
        movementManager = new MovementManager();
        ioManager = new IOManager();

        entity = new Entity("bucket.png", 10, 0, 0);
        heart1 = new Entity("heart.png", 10, 650, 0);
        heart2 = new Entity("heart.png", 50, 650, 0);
        heart3 = new Entity("heart.png", 90, 650, 0);
        drop = new Entity("droplet.png", 400, randomY, 2);
        scene = new Scene("background.png", 0, 0);
        pauseMenuScene = new PauseMenuScene();

        entityManager.addEntities(drop);
        entityManager.addEntities(entity);
        entityManager.addEntities(heart1);
        entityManager.addEntities(heart2);
        entityManager.addEntities(heart3);
        
        sceneManager.addScenes(scene);
        sceneManager.addScenes(pauseMenuScene); // Add PauseMenuScene
        sceneManager.setState(SceneManager.STATE.Start); // First state, (game playing state)
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        if (ioManager.isPauseKeyPressed()) {
            // Toggle pause state
            if (sceneManager.getState() == SceneManager.STATE.Start) {
                sceneManager.setState(SceneManager.STATE.Pause);
            } else if (sceneManager.getState() == SceneManager.STATE.Pause) {
                sceneManager.setState(SceneManager.STATE.Start);
            }
        }

        // Draw scenes, SceneManager handles drawing based on currentState
        sceneManager.drawScene(batch);

        // Game logic (movement, collisions) ONLY in Start(game) state
        if (sceneManager.getState() == SceneManager.STATE.Start) {
            entityManager.draw(batch);
            entity.movement();
            drop.AIMovment();
            collisionManager.checkCollisions(entity, drop); //check collision between 2 entities
            collisionManager.doCollision(entity, drop, false);
        }
    }

    public void dispose() {
        batch.dispose();
    }
}