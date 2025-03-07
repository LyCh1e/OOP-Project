// GameMaster.java
package io.github.ProjectOOP.lwjgl3;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

import ProjectOOP.Collision.Collidable;
import ProjectOOP.Collision.CollisionManager;
import ProjectOOP.Entity.EntityManager;
import ProjectOOP.Entity.ImmovableEntity;
import ProjectOOP.Entity.MovableEntity;
import ProjectOOP.Entity.Platform;
import ProjectOOP.Entity.Player;
import ProjectOOP.Entity.SpeedBar;
import ProjectOOP.IO.IOManager;
import ProjectOOP.IO.Input;
import ProjectOOP.IO.KeyBindings;
import ProjectOOP.IO.Output;
import ProjectOOP.Movement.AIMovement;
import ProjectOOP.Movement.MovementManager;
import ProjectOOP.Scene.GameOverScene;
import ProjectOOP.Scene.MainMenuScene;
import ProjectOOP.Scene.PauseMenuScene;
import ProjectOOP.Scene.Scene;
import ProjectOOP.Scene.SceneManager;
import ProjectOOP.Scene.SettingsScene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;

public class GameMaster extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shape;

    private EntityManager entityManager;
    private SceneManager sceneManager;
    private CollisionManager collisionManager;
    private MovementManager movementManager;
    private IOManager ioManager;

    private MovableEntity[] entities = new MovableEntity[1];
    private MovableEntity[] droplets = new MovableEntity[5];
    private ImmovableEntity[] hearts = new ImmovableEntity[3];
    private SpeedBar speedBar = new SpeedBar();
    private Color[] barColors = {
        Color.valueOf("#97f0f4"), Color.valueOf("#0bd7f2"), Color.valueOf("#35d1e1"), 
        Color.valueOf("#5bbac9"), Color.valueOf("#106ac5"), Color.valueOf("#1d1bb1")
    };
    private Output staminaOutput;

    // New platform-related variables
    private Platform[] platforms = new Platform[5]; // Adjust number as needed
    private Platform bottomPlatform;
    private float platformWidth = 100;
    private float platformHeight = 20;
    private float holeWidth = 50;
    private float scrollSpeed = -150;
    private float bottomPlatformY = 100;
    private float screenWidth;
    private float screenHeight;

    private Scene scene;
    private PauseMenuScene pauseMenuScene;
    private SettingsScene settingsScene;
    private MainMenuScene mainMenuScene;
    private GameOverScene gameOverScene;
    private KeyBindings keyBindings;
    private Input input;
    private Output output;
    private Output audioOutput;
    private int frameInStartState = 0;

    GameMaster() {
        entityManager = new EntityManager();
        collisionManager = new CollisionManager();
        
        // Create IOManager with Input
        input = new Input();
        ioManager = new IOManager(input);
        sceneManager = new SceneManager(ioManager);

        // Create MovementManager with IOManager
        movementManager = new MovementManager(ioManager, sceneManager);
    }

    public void create() {
        shape = new ShapeRenderer();
        Random random = new Random();
        float randomYBottom = random.nextFloat(AIMovement.bottomMinY, AIMovement.bottomMaxY);
        float randomYMiddle = random.nextFloat(AIMovement.middleMinY, AIMovement.middleMaxY);
        float randomYTop = random.nextFloat(AIMovement.topMinY, AIMovement.topMaxY);

        batch = new SpriteBatch();

        output = new Output("Score: ", Color.WHITE, Gdx.graphics.getWidth() - 300, 700, 2);
        staminaOutput = new Output("Stamina: ", Color.GOLD, Gdx.graphics.getWidth() - 300, 650, 2);
        audioOutput = new Output("backgroundMusic.mp3", 0.2f);

        // Initialize screen dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Initialize player as a Player instance instead of MovableEntity
        entities[0] = new Player("bucket.png", 10, 300, 200);
        // Enable gravity for player
        ((Player)entities[0]).setAffectedByGravity(true);
        entityManager.addEntities(entities[0]);

        // Create moving platforms
        for (int i = 0; i < platforms.length; i++) {
            float x = 200 + i * 250; // Spacing platforms
            float y = 150 + i * 70;  // Different heights
            float minY = y - 50;     // Movement boundaries
            float maxY = y + 50;
            platforms[i] = new Platform("platform.png", x, y, platformWidth, platformHeight, minY, maxY, 50);
            entityManager.addEntities(platforms[i]);
        }

        // Create bottom platform with looping and holes feature
        bottomPlatform = new Platform("platform.png", 0, bottomPlatformY, screenWidth, platformHeight, scrollSpeed);
        bottomPlatform.setAsBottomPlatform(scrollSpeed, screenWidth);
        entityManager.addEntities(bottomPlatform);
        
        // Add droplets (water collectibles)
        for (int i = 0; i < droplets.length; i++) {
            droplets[i] = new MovableEntity("droplet.png", 1280, randomYBottom, 2);
            entityManager.addEntities(droplets[i]);
        }
        
        // Add hearts
        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new ImmovableEntity("heart.png", 10 + (i * 40), 650, 0);
            entityManager.addEntities(hearts[i]);
        }
        
        speedBar = new SpeedBar(10, 150, barColors[0], 60, 0, 400);
        entityManager.addEntities(speedBar);
                
        ioManager.addOutput(audioOutput);
        ioManager.addOutput(output);
        ioManager.addOutput(staminaOutput);
        staminaOutput.setNumber(30); // Set at half stamina initially
        
        scene = new Scene("background.png", 0, 0);
        pauseMenuScene = new PauseMenuScene(ioManager, sceneManager);
        settingsScene = new SettingsScene(ioManager, sceneManager);
        mainMenuScene = new MainMenuScene(ioManager, sceneManager);
        gameOverScene = new GameOverScene(ioManager, sceneManager);

        // Configure SceneManager
        sceneManager.addSceneToState(SceneManager.STATE.Start, scene);
        sceneManager.addSceneToState(SceneManager.STATE.Pause, pauseMenuScene);
        sceneManager.addSceneToState(SceneManager.STATE.Settings, settingsScene);
        sceneManager.addSceneToState(SceneManager.STATE.MainMenu, mainMenuScene);
        sceneManager.addSceneToState(SceneManager.STATE.GameOver, gameOverScene);
        sceneManager.setState(SceneManager.STATE.MainMenu);
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        SceneManager.STATE currentState = sceneManager.getState();
        sceneManager.switchScene(ioManager);
        
        // Music will play throughout all scenes
        ioManager.playMusic();

        // Draw scenes
        sceneManager.drawScene(batch);

        // Game logic only in Start state
        if (currentState == SceneManager.STATE.Start) {
            frameInStartState++;
            
            // Force isJumping() to false for the first 10 frames of Start state
            if (frameInStartState <= 10) {
                ioManager.setForceJumpFalse(true);
            } else {
                ioManager.setForceJumpFalse(false);
            }
            
            entityManager.draw(batch);
            entityManager.draw(shape);
            ioManager.draw(batch);

            // Update moving platforms
            for (int i = 0; i < platforms.length; i++) {
                platforms[i].updatePosition();
            }
            
            // Update bottom platform with looping and holes
            bottomPlatform.updatePosition();
            
            // Update player movement
            movementManager.updateUserMovement(entities[0], currentState);
            
            // Check platform collisions if entity is a Player
            if (entities[0] instanceof Player) {
                Player player = (Player) entities[0];
                
                // Check regular platforms
                boolean onAnyPlatform = false;
                
                for (Platform platform : platforms) {
                    if (Collidable.doPlatformCollision(player, platform)) {
                        onAnyPlatform = true;
                    }
                }
            float stamina = staminaOutput.getNumber();
            // Update droplets movement
            for (int i = 0; i < droplets.length; i++) {
                
                movementManager.updateAIMovementYAxis(droplets[i], MovementManager.Y_Column.BOTTOM, stamina);
                movementManager.updateAIMovementYAxis(droplets[i], MovementManager.Y_Column.MIDDLE, stamina);
                
            }
            
            float score = output.getNumber();
            
            // Stamina controls (for testing)
            if (Gdx.input.isKeyJustPressed(Keys.L)) {
                if (staminaOutput.getNumber() <= 60 - 10) staminaOutput.setNumber(stamina+= 10);
            }
            if (Gdx.input.isKeyJustPressed(Keys.EQUALS)) {
                if (staminaOutput.getNumber() <= 60 - 1) staminaOutput.setNumber(stamina += 1);
            }
            if (Gdx.input.isKeyJustPressed(Keys.J)) {
                if (staminaOutput.getNumber() >= 10) staminaOutput.setNumber(stamina -= 10);
            }
            if (Gdx.input.isKeyJustPressed(Keys.MINUS)) {
                if (staminaOutput.getNumber() >= 1) staminaOutput.setNumber(stamina -= 1);
            }
            staminaOutput.setString("Stamina: " + String.valueOf(Math.round(staminaOutput.getNumber())));
            speedBar.setBar(barColors, staminaOutput.getNumber());
            
            // Check for water collisions
            for (int i = 0; i < droplets.length; i++) {
                if (collisionManager.checkCollisions(entities[0], droplets[i])) {
                    Collidable.doCollision(entities[0], droplets[i], false);
                    
                    // Increase stamina when collecting water
                    if (staminaOutput.getNumber() <= 60 - 5) {
                        staminaOutput.setNumber(staminaOutput.getNumber() + 5);
                    }
                    
                    // Update score
                    output.setNumber(score += 1);
                    output.setString("Score: " + String.valueOf(Math.round(output.getNumber())));
                }
            }

            // Gradually decrease stamina over time
            staminaOutput.setNumber(stamina -= 0.01f);
            staminaOutput.setString("Stamina: " + String.valueOf(Math.round(staminaOutput.getNumber())));
            
            // Update score slowly over time
            output.setNumber(score += 0.01);
            output.setString("Score: " + String.valueOf(Math.round(output.getNumber())));
            
            
            // Check bottom platform segments
            if (!onAnyPlatform && bottomPlatform != null) {
                List<Rectangle> segments = bottomPlatform.getSegments();
                for (Rectangle segment : segments) {
                    if (Collidable.doSegmentedPlatformCollision(player, bottomPlatform, segment)) {
                        onAnyPlatform = true;
                        break;
                    }
                }
            }
            
            // If player isn't on any platform and isn't jumping, apply gravity
            if (!onAnyPlatform && !player.isJumping()) {
                player.setVelocityY(player.getVelocityY() - 9.8f * Gdx.graphics.getDeltaTime());
            }
        }
       }
    }

    public void dispose() {
        batch.dispose();
        shape.dispose();
    }
}