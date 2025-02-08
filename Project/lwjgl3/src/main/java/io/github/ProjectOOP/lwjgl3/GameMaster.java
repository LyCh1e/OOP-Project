// GameMaster.java
package io.github.ProjectOOP.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music; // import Music class
import com.badlogic.gdx.Gdx; // import Gdx for file handling
import com.badlogic.gdx.graphics.Color;

public class GameMaster extends ApplicationAdapter {
    private SpriteBatch batch;

    private EntityManager entityManager;
    private SceneManager sceneManager;
    private CollisionManager collisionManager;
    private MovementManager movementManager;
    private IOManager ioManager;

    private MovableEntity entity;
    private MovableEntity drop, drop1;
    private ImmovableEntity heart1, heart2, heart3;
    private Scene scene;
    private PauseMenuScene pauseMenuScene;
    private SettingsScene settingsScene;
    private KeyBindings keyBindings;
    private Input input;
    private Output output;
    private Music backgroundMusic; // music variable for background music

    GameMaster() {
        entityManager = new EntityManager();
        sceneManager = new SceneManager();
        collisionManager = new CollisionManager();
        input = new Input();
	    // Create IOManager with Input
	    ioManager = new IOManager(input);
	
	    // Create MovementManager with IOManager
	    movementManager = new MovementManager(ioManager);
//	    keyBindings = new KeyBindings();
    }

    public void create() {
        Random random = new Random();
        float randomYBottom = random.nextFloat(AIMovement.bottomMinY, AIMovement.bottomMaxY);
        float randomYMiddle = random.nextFloat(AIMovement.middleMinY, AIMovement.middleMaxY);
        float randomYTop = random.nextFloat(AIMovement.topMinY, AIMovement.topMaxY);

        batch = new SpriteBatch();
        //keyBindings.initialize();  // Initialize after LibGDX is ready
	    output = new Output("Score: ", Color.WHITE, Gdx.graphics.getWidth() - 300, 700, 2);
        entity = new MovableEntity("bucket.png", 10, 0, 0);
        drop = new MovableEntity("droplet.png", 1280, randomYBottom, 2);
        drop1 = new MovableEntity("droplet.png", 1280, randomYBottom, 2);
        heart1 = new ImmovableEntity("heart.png", 10, 650, 0);
        heart2 = new ImmovableEntity("heart.png", 50, 650, 0);
        heart3 = new ImmovableEntity("heart.png", 90, 650, 0);
        scene = new Scene("background.png", 0, 0);
        pauseMenuScene = new PauseMenuScene();
        settingsScene = new SettingsScene();

        // Configure SceneManager to associate scenes with states
        sceneManager.addSceneToState(SceneManager.STATE.Start, scene); // baackground.png in Start state
        sceneManager.addSceneToState(SceneManager.STATE.Pause, pauseMenuScene); // Pause Menu only in Pause state
        sceneManager.addSceneToState(SceneManager.STATE.Settings, settingsScene); // SettingsScene only in Settings state
        sceneManager.setState(SceneManager.STATE.Start); // First state, (game playing state)

        entityManager.addEntities(drop);
        entityManager.addEntities(drop1);
        entityManager.addEntities(entity);
        entityManager.addEntities(heart1);
        entityManager.addEntities(heart2);
        entityManager.addEntities(heart3);
        
        ioManager.addOutput(output);
        
        // Load and play background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundMusic.mp3")); // Load music file
        backgroundMusic.setLooping(true); // Set looping to true
        backgroundMusic.play(); // Start playing the music
        backgroundMusic.setVolume(0.2f); // Lower down the default background music bc its too loud
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

        if (ioManager.isSettingsKeyPressed()) {
            // Toggle settings state, settings can only be opened from pause menu, click 1 to open settings
            if (sceneManager.getState() == SceneManager.STATE.Pause) {
                sceneManager.setState(SceneManager.STATE.Settings);
            } else if (sceneManager.getState() == SceneManager.STATE.Settings) {
                sceneManager.setState(SceneManager.STATE.Pause);
            }
        }

        // Draw scenes, SceneManager handles drawing based on currentState!!!
        // Draw the background
        sceneManager.drawScene(batch);

        // Game logic (movement, collisions) ONLY in Start(game) state

        // Draw entities
        if (sceneManager.getState() == SceneManager.STATE.Start) {
            entityManager.draw(batch);
            ioManager.draw(batch);
            movementManager.updateUserMovement(entity);
            movementManager.updateAIMovementXAxis(drop, MovementManager.X_Row.LEFT);
            movementManager.updateAIMovementYAxis(drop1, MovementManager.Y_Column.BOTTOM);
            
            float score = output.getNumber();
            if (collisionManager.checkCollisions(entity, drop1)) {
                Collidable.doCollision(entity, drop1);
                output.setNumber(score -= 0.1);
                output.setString("Score: " + String.valueOf(Math.round(output.getNumber())));
            }
            else {
                output.setNumber(score += 0.01);
                output.setString("Score: " + String.valueOf(Math.round(output.getNumber())));
            }
        }
    }

    public void dispose() {
        batch.dispose();
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }
}
