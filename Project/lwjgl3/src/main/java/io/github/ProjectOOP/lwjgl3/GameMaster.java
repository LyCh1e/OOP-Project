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

    private MovableEntity[] entities = new MovableEntity[1];
    private MovableEntity[] droplets = new MovableEntity[5];
    private ImmovableEntity[] hearts = new ImmovableEntity[3];

    private Scene scene;
    private PauseMenuScene pauseMenuScene;
    private SettingsScene settingsScene;
    private MainMenuScene mainMenuScene;
    private KeyBindings keyBindings;
    private Input input;
    private Output output;
    private int frameInStartState = 0; // Frame counter for Start state, to help with transtion from menu to start scene

    GameMaster() {
        entityManager = new EntityManager();
        
        collisionManager = new CollisionManager();
        input = new Input();
	    // Create IOManager with Input
	    ioManager = new IOManager(input);
	    sceneManager = new SceneManager(ioManager);
	
	    // Create MovementManager with IOManager
	    movementManager = new MovementManager(ioManager, sceneManager);
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

	    for (int i = 0; i < entities.length; i++) {	
	    	entities[i] = new MovableEntity("bucket.png", 10, 0, 0);
	    	entityManager.addEntities(entities[i]);
	    }
	    for (int i = 0; i < droplets.length; i++) {
	    	droplets[i] = new MovableEntity("droplet.png", 1280, randomYBottom, 2);
		    entityManager.addEntities(droplets[i]);
	    }
	    for (int i = 0; i < hearts.length; i++) {
	    	hearts[i] = new ImmovableEntity("heart.png", 10 + (i * 40), 650, 0);
		    entityManager.addEntities(hearts[i]);
	    }
	            
        ioManager.addOutput(output);
        ioManager.setMusic("backgroundMusic.mp3"); 
        ioManager.playMusic();
        ioManager.setAudioVolume(0.2f);
	    
        scene = new Scene("background.png", 0, 0);
        pauseMenuScene = new PauseMenuScene();
        settingsScene = new SettingsScene(ioManager);
        mainMenuScene = new MainMenuScene(ioManager);

        // Configure SceneManager to associate scenes with states
        sceneManager.addSceneToState(SceneManager.STATE.Start, scene); // baackground.png in Start state
        sceneManager.addSceneToState(SceneManager.STATE.Pause, pauseMenuScene); // Pause Menu only in Pause state
        sceneManager.addSceneToState(SceneManager.STATE.Settings, settingsScene);
        sceneManager.addSceneToState(SceneManager.STATE.MainMenu, mainMenuScene); // SettingsScene only in Settings state
        sceneManager.setState(SceneManager.STATE.MainMenu); // First state, (game playing state)
        
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        SceneManager.STATE currentState = sceneManager.getState();
        sceneManager.switchScene(ioManager);
        


        // Draw scenes, SceneManager handles drawing based on currentState!!!
        // Draw the background
        sceneManager.drawScene(batch);

        // Game logic (movement, collisions) ONLY in Start(game) state

        // Draw entities
        if (currentState == SceneManager.STATE.Start) {
        	frameInStartState++; // Increment frame counter for Start state
        	
            // Force isJumping() to false for the first 10 frames of Start state this is because when i change scene it overlaps
            if (frameInStartState <= 10) {
                ioManager.setForceJumpFalse(true); //if still within 10 frames cant jump, you wont notice bc 10 frames is v short
            } else {
                ioManager.setForceJumpFalse(false); // Disable force false after 10 frames
            }
        	
            entityManager.draw(batch);
            ioManager.draw(batch);
            movementManager.updateUserMovement(entities[0], currentState);
            for (int i = 0; i < droplets.length; i++) {
            	if (i == 1) {
            		movementManager.updateAIMovementYAxis(droplets[i], MovementManager.Y_Column.BOTTOM);
            	} else {
            		movementManager.updateAIMovementXAxis(droplets[i], MovementManager.X_Row.LEFT);
            	}
            }
            
            float score = output.getNumber();
            if (collisionManager.checkCollisions(entities[0], droplets[1])) {
                Collidable.doCollision(entities[0], droplets[1], false);
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
    }
}