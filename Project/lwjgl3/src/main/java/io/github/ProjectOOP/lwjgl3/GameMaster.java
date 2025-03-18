package io.github.ProjectOOP.lwjgl3;

import java.util.Random;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

import ProjectOOP.Collision.Collidable;
import ProjectOOP.Collision.CollisionManager;
import ProjectOOP.Entity.EntityManager;
import ProjectOOP.Entity.Health;
import ProjectOOP.Entity.Player;
import ProjectOOP.Entity.Platform;
import ProjectOOP.Entity.SoftDrink;
import ProjectOOP.Entity.SpeedBar;
import ProjectOOP.Entity.Water;
import ProjectOOP.IO.IOManager;
import ProjectOOP.IO.Input;
import ProjectOOP.IO.Output;
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

    private Health[] hearts = new Health[5];
    private Player[] player1 = new Player[1];
    private SoftDrink[] softDrinks = new SoftDrink[1]; 
    private Water[] Water = new Water[1];
    private SpeedBar speedBar = new SpeedBar();
    private Color[] barColors = {
        Color.valueOf("#97f0f4"), Color.valueOf("#0bd7f2"), Color.valueOf("#35d1e1"), 
        Color.valueOf("#5bbac9"), Color.valueOf("#106ac5"), Color.valueOf("#1d1bb1")
    };
    private Output staminaOutput;

    // New platform-related variables
    private Platform[] platforms = new Platform[5]; // Adjust number as needed
    private Platform bottomPlatform, middlePlatform, topPlatform;
    private float platformWidth = 100;
    private float platformHeight = 40;
    private float holeWidth = 50;
    private float scrollSpeed = -150;
    private float bottomPlatformY = 30;
    private float middlePlatformY = 200;
    private float topPlatformY = 400;
    private float screenWidth;
    private float screenHeight;
    private float stamina = 60;

    private Scene backgroundScene;
    private PauseMenuScene pauseMenuScene;
    private SettingsScene settingsScene;
    private MainMenuScene mainMenuScene;
    private GameOverScene gameOverScene;
    private Input input;
    private Output scoreOutput;
    private Output audioOutput;
    private int frameInStartState = 0;
    
    private int maxHealth = hearts.length;
    private int currentHealth = maxHealth; //Track health

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

        batch = new SpriteBatch();

        scoreOutput = new Output("Score: ", Color.WHITE, Gdx.graphics.getWidth() - 300, 700, 2);
        staminaOutput = new Output("Stamina: ", Color.GOLD, Gdx.graphics.getWidth() - 300, 650, 2);
        audioOutput = new Output("backgroundMusic.mp3", 0.2f);

        // Initialize screen dimensions
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        player1[0] = new Player("player.png", 10, 300, 600);
        // Enable gravity for player
        player1[0].setAffectedByGravity(true);
        entityManager.addEntities(player1[0]);

        // Create moving platforms
//        for (int i = 0; i < platforms.length; i++) {
//            float x = 200 + i * 250; // Spacing platforms
//            float y = 150 + i * 70;  // Different heights
//            float minY = y - 50;     // Movement boundaries
//            float maxY = y + 50;
//            platforms[i] = new Platform("platform.png", x, y, platformWidth, platformHeight, minY, maxY, 50);
//            entityManager.addEntities(platforms[i]);
//        }

        // Create bottom platform with looping and holes feature
        bottomPlatform = new Platform("platform.png", 0, bottomPlatformY, screenWidth, platformHeight, scrollSpeed);
        middlePlatform = new Platform("platform.png", 0, middlePlatformY, screenWidth, platformHeight, scrollSpeed);
        topPlatform = new Platform("platform.png", 0, topPlatformY, screenWidth, platformHeight, scrollSpeed);
        bottomPlatform.setAsBottomPlatform(scrollSpeed, screenWidth);
        middlePlatform.setAsBottomPlatform(scrollSpeed, screenWidth);
        topPlatform.setAsBottomPlatform(scrollSpeed, screenWidth);
        entityManager.addEntities(bottomPlatform);
        entityManager.addEntities(middlePlatform);
        entityManager.addEntities(topPlatform);
        
        // Add droplets (water collectibles)
//        for (int i = 0; i < droplets.length; i++) {
//            droplets[i] = new MovableEntity("waterbottle.png", 1280, randomYBottom, 2);
//            entityManager.addEntities(droplets[i]);
//        }
        
        // Add hearts
        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new Health(10 + (i * 40), 650);
            entityManager.addEntities(hearts[i]);
        }

         // Spawn moving SoftDrinks
        for (int i = 0; i < softDrinks.length; i++) {
         float x = 1280;
         float minY = 50;  // Minimum Y (closer to the ground)
         float maxY = 250; // Maximum Y (lower on the screen)
         float y = random.nextFloat() * (maxY - minY) + minY;

         softDrinks[i] = new SoftDrink(x, y, 5, minY, maxY); 
         entityManager.addEntities(softDrinks[i]);
         }
        
        // Spawn moving waterbottle
        for (int i = 0; i < Water.length; i++) {
            float x = random.nextFloat() * Gdx.graphics.getWidth(); // Random X position
            float y = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100; // Random Y position

            Water[i] = new Water(x, y, 5, y, y); // Assign random Y position
            entityManager.addEntities(Water[i]);
        }

        
        speedBar = new SpeedBar(10, 150, barColors[0], 60, 0, 400);
        entityManager.addEntities(speedBar);
                
        ioManager.addOutput(audioOutput);
        ioManager.addOutput(scoreOutput);
        ioManager.addOutput(staminaOutput);
        staminaOutput.setNumber(stamina);
        
        backgroundScene = new Scene("background.png", 0, 0);
        pauseMenuScene = new PauseMenuScene(ioManager, sceneManager);
        settingsScene = new SettingsScene(ioManager, sceneManager);
        mainMenuScene = new MainMenuScene(ioManager, sceneManager);
        gameOverScene = new GameOverScene(ioManager, sceneManager);

        // Configure SceneManager
        sceneManager.addSceneToState(SceneManager.STATE.Start, backgroundScene);
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

//            // Update moving platforms
//            for (int i = 0; i < platforms.length; i++) {
//                platforms[i].updatePosition();
//            }
            
            // Update bottom platform with looping and holes
            bottomPlatform.updatePosition();
            middlePlatform.updatePosition();
            topPlatform.updatePosition();
            // Update player movement
            movementManager.updateUserMovement(player1[0], currentState);
            
//            // Check platform collisions if entity is a Player
//            if (ioManager.isJumping() && player1[0] instanceof Player) {
//                Player player = player1[0];
//                
//                // If the player is not already jumping, initiate a jump
//                if (!player.isJumping()) {
//                    player.jump();
//                    System.out.println("Jump initiated in GameMaster");
//                }
//            }

            // Then in the platform collision section, simplify it:
            if (player1[0] instanceof Player) {
                Player player = player1[0];
                
                boolean onAnyPlatform = false;

             // Check bottom platform segments
                if (bottomPlatform != null) {
                    // Skip platform collision checks completely if player is jumping upward
                    if (!(player.isJumping() && player.getVelocityY() > 0)) {
                        List<Rectangle> segments = bottomPlatform.getSegments();
                        for (Rectangle segment : segments) {
                            if (Collidable.doSegmentedPlatformCollision(player, bottomPlatform, segment)) {
                                onAnyPlatform = true;
                                break;
                            }
                        }
                    }
                }

                // Check middle platform segments - same logic as bottom platform
                if (middlePlatform != null && !onAnyPlatform) {
                    // Skip platform collision checks completely if player is jumping upward
                    if (!(player.isJumping() && player.getVelocityY() > 0)) {
                        List<Rectangle> segments = middlePlatform.getSegments();
                        for (Rectangle segment : segments) {
                            if (Collidable.doSegmentedPlatformCollision(player, middlePlatform, segment)) {
                                onAnyPlatform = true;
                                break;
                            }
                        }
                    }
                }

                // Check top platform segments - same logic as middle and bottom platforms
                if (topPlatform != null && !onAnyPlatform) {
                    // Skip platform collision checks completely if player is jumping upward
                    if (!(player.isJumping() && player.getVelocityY() > 0)) {
                        List<Rectangle> segments = topPlatform.getSegments();
                        for (Rectangle segment : segments) {
                            if (Collidable.doSegmentedPlatformCollision(player, topPlatform, segment)) {
                                onAnyPlatform = true;
                                break;
                            }
                        }
                    }
                }

                // Apply gravity if not on platform and not jumping upward
                if (!onAnyPlatform && !(player.isJumping() && player.getVelocityY() > 0)) {
                    float currentVelocity = player.getVelocityY();
                    // Apply strong fixed gravity - don't use deltaTime for more immediate effect
                    player.setVelocityY(currentVelocity - 9.8f); 
                }
            }

            float score = scoreOutput.getNumber();
            
            // Stamina controls (for testing)
            if (Gdx.input.isKeyJustPressed(Keys.L)) {
                if (stamina <= 60 - 10) staminaOutput.setNumber(stamina += 10);
            }
            if (Gdx.input.isKeyJustPressed(Keys.EQUALS)) {
                if (stamina <= 60 - 1) staminaOutput.setNumber(stamina += 1);
            }
            if (Gdx.input.isKeyJustPressed(Keys.J)) {
                if (stamina >= 10) staminaOutput.setNumber(stamina -= 10);
            }
            if (Gdx.input.isKeyJustPressed(Keys.MINUS)) {
                if (stamina >= 1) staminaOutput.setNumber(stamina -= 1);
            }
            staminaOutput.setString("Stamina: " + String.valueOf(Math.round(stamina)));
            speedBar.setBar(barColors, stamina);
            
            // Check for water collisions
//            for (int i = 0; i < droplets.length; i++) {
//                if (collisionManager.checkCollisions(entities[0], droplets[i])) {
//                    Collidable.doCollision(entities[0], droplets[i], false);
//                    
//                    // Increase stamina when collecting water
//                    if (staminaOutput.getNumber() <= 60 - 5) {
//                        staminaOutput.setNumber(staminaOutput.getNumber() + 5);
//                    }
//                    
//                    // Update score
//                    output.setNumber(score += 1);
//                    output.setString("Score: " + String.valueOf(Math.round(output.getNumber())));
//                }
//            }
            for (int i = 0; i < Water.length; i++) {
                movementManager.updateAIMovementYAxis(Water[i], MovementManager.Y_Column.BOTTOM);
                movementManager.updateAIMovementYAxis(Water[i], MovementManager.Y_Column.MIDDLE);
            }
            for (int i = 0; i < softDrinks.length; i++) {
                movementManager.updateAIMovementYAxis(softDrinks[i], MovementManager.Y_Column.BOTTOM);
                movementManager.updateAIMovementYAxis(softDrinks[i], MovementManager.Y_Column.MIDDLE);
            }
            speedBar.setEntitySpeedsByStamina(stamina, Water);      //  Water reacts to stamina
            speedBar.setEntitySpeedsByStamina(stamina, softDrinks); //  SoftDrink reacts to stamina
            
            // Update soft drinks
//            for (int i = 0; i < Water.length; i++) {
//                Water[i].move(Gdx.graphics.getDeltaTime()); // Move water bottle correctly
//            }
            
            // Check for waterbottle collisions
            for (int i = 0; i < Water.length; i++) {
                Water[i].move(Gdx.graphics.getDeltaTime()); // Move water bottle

                if (collisionManager.checkCollisions(player1[0], Water[i])) {
                    Collidable.doCollision(player1[0], Water[i], false);

//                    // Increase stamina when collecting water
//                    if (stamina <= 60 - 5) {
//                        stamina += 5;
//                        staminaOutput.setNumber(stamina);
//                    }
                    stamina = Math.min(stamina + 5, 60);
                    staminaOutput.setNumber(stamina);

                    // Reset the position of the collected water bottle
                    float newX = Gdx.graphics.getWidth() + 50;
                    float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
                    Water[i].setPosition(newX, newY);

                    // Apply speed update ONLY to the collected water bottle
                    speedBar.setEntitySpeedsByStamina(stamina, new Water[]{Water[i]}); 

                    // Update score
                    scoreOutput.setNumber(score += 1);
                    scoreOutput.setString("Score: " + Math.round(scoreOutput.getNumber()));
                }
            }
            
            // Update soft drinks
//            for (SoftDrink softDrink : softDrinks) {
//                softDrink.move(Gdx.graphics.getDeltaTime()); // Move soft drink
//            }
            
            // Check for soft drink collisions
            for (SoftDrink softDrink : softDrinks) {
                if (collisionManager.checkCollisions(player1[0], softDrink)) {
                    Collidable.doCollision(player1[0], softDrink, false);

                    if (currentHealth > 0) {
                        currentHealth--;
                        
                     // Remove the corresponding heart from the UI
                        if (currentHealth < hearts.length) {
                            entityManager.removeEntity(hearts[currentHealth]); // Remove heart
                        }
                        
                        if (stamina >= 5) {
                            stamina -= 5;
                        } else {
                            stamina = 0;  // Prevent negative stamina
                        }
                        staminaOutput.setNumber(stamina);

                        //Resets the position of the soft drink after collision
                        float newX = Gdx.graphics.getWidth() + 50;
                        float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200)+ 100;
//                        float newY = (float) Math.random() * (softDrink.maxY - softDrink.minY) + softDrink.minY; // Ensure Y stays in range
                        softDrink.setPosition(newX, newY);
                        speedBar.setEntitySpeedsByStamina(stamina, new SoftDrink[]{softDrink}); 
                    }
                }
            }
            
            // Check for game over condition
            if (currentHealth <= 0) {
                sceneManager.resetGame(player1[0], Water, softDrinks, bottomPlatform,
                                      bottomPlatformY, scoreOutput, hearts, entityManager);
                
                // Reset health and stamina
                currentHealth = maxHealth;
                stamina = 30; // Reset stamina
                staminaOutput.setNumber(stamina);
                staminaOutput.setString("Stamina: " + Math.round(stamina));
                
                frameInStartState = 0;
            }
  
            
//            // Gradually decrease stamina over time
//            staminaOutput.setNumber(stamina -= 0.01f);
//            staminaOutput.setString("Stamina: " + String.valueOf(Math.round(staminaOutput.getNumber())));
//            
//            // Update score slowly over time
//            output.setNumber(score += 0.01);
//            output.setString("Score: " + String.valueOf(Math.round(output.getNumber())));
        }
    }

    public void dispose() {
        batch.dispose();
        shape.dispose();
    }
}