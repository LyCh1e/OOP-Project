package io.github.ProjectOOP.lwjgl3;

import java.util.Random;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

import ProjectOOP.Collision.Collidable;
import ProjectOOP.Collision.CollisionManager;
import ProjectOOP.Entity.EntityManager;
import ProjectOOP.Entity.Health;
import ProjectOOP.Entity.ImmovableEntity;
import ProjectOOP.Entity.Player;
import ProjectOOP.Entity.Platform;
import ProjectOOP.Entity.SoftDrink;
import ProjectOOP.Entity.SpeedBar;
import ProjectOOP.Entity.Water;
import ProjectOOP.Entity.Broccoli;
import ProjectOOP.Entity.Pizza;
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
	private Water[] Waterbottle = new Water[1];
	private Broccoli[] broccoli = new Broccoli[1];
	private Pizza[] pizza = new Pizza[1];
	private SpeedBar speedBar = new SpeedBar();
	private Color[] barColors = { Color.valueOf("#97f0f4"), Color.valueOf("#0bd7f2"), Color.valueOf("#35d1e1"),
			Color.valueOf("#5bbac9"), Color.valueOf("#106ac5"), Color.valueOf("#1d1bb1") };
	private Output staminaOutput;

	// New platform-related variables
	private Platform[] platforms = new Platform[5]; // Adjust number as needed
	private Platform bottomPlatform, middlePlatform, topPlatform;
	private float platformWidth = 100;
	private float platformHeight = 20;
	private float holeWidth = 50;
	private float scrollSpeed = -150;
	private float bottomPlatformY = 30;
	private float middlePlatformY = 250;
	private float topPlatformY = 450;
	private float screenWidth;
	private float screenHeight;
	private float stamina = 30; 
	private float score = 0;

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
	private int currentHealth = maxHealth; // Track health

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

		player1[0] = new Player("player.png", 100, 300, 600);
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

		// Add hearts
		for (int i = 0; i < hearts.length; i++) {
			hearts[i] = new Health(10 + (i * 40), 650);
			entityManager.addEntities(hearts[i]);
		}

		// Spawn moving softDrinks
		for (int i = 0; i < softDrinks.length; i++) {
			float x = random.nextFloat() * Gdx.graphics.getWidth(); // Random X position
			float y = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100; // Random Y position

			softDrinks[i] = new SoftDrink(x, y, 5, y, y); // Assign random Y position
			entityManager.addEntities(softDrinks[i]);
		}

		// Spawn moving Waterbottle
		for (int i = 0; i < Waterbottle.length; i++) {
			float x = random.nextFloat() * Gdx.graphics.getWidth(); // Random X position
			float y = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100; // Random Y position

			Waterbottle[i] = new Water(x, y, 5, y, y); // Assign random Y position
			entityManager.addEntities(Waterbottle[i]);
		}

		// Spawn moving broccoli 
		for (int i = 0; i < broccoli.length; i++) { 
			float x = random.nextFloat() * Gdx.graphics.getWidth(); // Random X position
			float y = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100; // Random Y position

			broccoli[i] = new Broccoli(x, y, 5, y, y); // Assign random Y position
			entityManager.addEntities(broccoli[i]);
		}

		// Spawn moving pizza 
		for (int i = 0; i < pizza.length; i++) { 
			float x = random.nextFloat() * Gdx.graphics.getWidth(); // Random X position
			float y = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100; // Random Y position

			pizza[i] = new Pizza(x, y, 5, y, y); // Assign random Y position
			entityManager.addEntities(pizza[i]);
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

			// Stamina controls (for testing)
			if (Gdx.input.isKeyJustPressed(Keys.L)) {
				if (stamina <= 60 - 10)
					stamina += 10;
			}
			if (Gdx.input.isKeyJustPressed(Keys.EQUALS)) {
				if (stamina <= 60 - 1)
					stamina += 1;
			}
			if (Gdx.input.isKeyJustPressed(Keys.J)) {
				if (stamina >= 10)
					stamina -= 10;
			}
			if (Gdx.input.isKeyJustPressed(Keys.MINUS)) {
				if (stamina >= 1)
					stamina -= 1;
			}
			staminaOutput.setNumber(stamina);
			staminaOutput.setString("Stamina: " + String.valueOf(Math.round(stamina)));
			speedBar.setBar(barColors, stamina);
			scoreOutput.setNumber(score);
			scoreOutput.setString("Score: " + Math.round(scoreOutput.getNumber()));

			for (int i = 0; i < Waterbottle.length; i++) {
				movementManager.updateAIMovementYAxis(Waterbottle[i], MovementManager.Y_Column.BOTTOM);
				movementManager.updateAIMovementYAxis(Waterbottle[i], MovementManager.Y_Column.MIDDLE);
			}
			for (int i = 0; i < softDrinks.length; i++) {
				movementManager.updateAIMovementYAxis(softDrinks[i], MovementManager.Y_Column.BOTTOM);
				movementManager.updateAIMovementYAxis(softDrinks[i], MovementManager.Y_Column.MIDDLE);
			}
			for (int i = 0; i < broccoli.length; i++) {
				movementManager.updateAIMovementYAxis(broccoli[i], MovementManager.Y_Column.BOTTOM);
				movementManager.updateAIMovementYAxis(broccoli[i], MovementManager.Y_Column.MIDDLE);
			}
			for (int i = 0; i < pizza.length; i++) {
				movementManager.updateAIMovementYAxis(pizza[i], MovementManager.Y_Column.BOTTOM);
				movementManager.updateAIMovementYAxis(pizza[i], MovementManager.Y_Column.MIDDLE);
			}
			speedBar.setEntitySpeedsByStamina(stamina, Waterbottle); // Water reacts to stamina
			speedBar.setEntitySpeedsByStamina(stamina, softDrinks); // SoftDrink reacts to stamina
			speedBar.setEntitySpeedsByStamina(stamina, broccoli); // Broccoli reacts to stamina
			speedBar.setEntitySpeedsByStamina(stamina, pizza); // Pizza reacts to stamina

			// Check for Waterbottle collisions
			for (int i = 0; i < Waterbottle.length; i++) {
				if (collisionManager.checkCollisions(player1[0], Waterbottle[i])) {

					//Update stamina
					stamina = Math.min(stamina + 2, 60); // +2 stamina for every Waterbottle collected

					// Reset the position of the collected Waterbottle
					float newX = Gdx.graphics.getWidth() + 50;
					float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
					Waterbottle[i].setPosition(newX, newY);

					// Update score
					score = Math.max(score + 5, 0); // +5 score for every Waterbottle collected
				}
			}

			// Check for softDrink collisions
			for (SoftDrink softDrink : softDrinks) {
				if (collisionManager.checkCollisions(player1[0], softDrink)) {
					
					// Update stamina
					stamina = Math.max(stamina - 5, 0); // -5 stamina for every softDrink collected

					// Resets the position of the collected SoftDrink
					float newX = Gdx.graphics.getWidth() + 50;
					float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
					softDrink.setPosition(newX, newY);

					// Update score
					score = Math.max(score - 3, 0); // -3 score for every softDrink collected
				}
			}
			
			// Check for broccoli collisions
			for (Broccoli broccoli : broccoli) {
				if (collisionManager.checkCollisions(player1[0], broccoli)) {
					if (currentHealth < hearts.length) {
						
					    // Add a heart back to the UI
					    entityManager.addEntities(hearts[currentHealth]); 
					    currentHealth++;
					}

					// Resets the position of the collected broccoli 
					float newX = Gdx.graphics.getWidth() + 50;
					float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
					broccoli.setPosition(newX, newY);

					// Update score
					score = Math.max(score + 40, 0); // +40 score for every broccoli collected
				}
			}
			
			// Check for pizza collisions
			for (Pizza pizza : pizza) {
				if (collisionManager.checkCollisions(player1[0], pizza)) {
					
			        // Reduce health
			        if (currentHealth > 0) {
			            currentHealth--;
			            
			            // Remove the corresponding heart from the UI
			            if (currentHealth < hearts.length) {
			                entityManager.removeEntity(hearts[currentHealth]);
			            }
			        }

					// Resets the position of pizza after collision
					float newX = Gdx.graphics.getWidth() + 50;
					float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
					pizza.setPosition(newX, newY);

					// Update score
					score = Math.max(score - 20, 0); // -20 score for every pizza collected
				}
			}
		}

			// Check for game over condition
			if (currentHealth <= 0) {
				resetGame(player1[0], Waterbottle, softDrinks, broccoli, pizza, bottomPlatform, bottomPlatformY, scoreOutput, hearts,
						entityManager);

				// Reset health, score and stamina
				currentHealth = maxHealth;
				stamina = 30; 
				score = 0;
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

		public void resetGame(Player player, Water[] waterBottles, SoftDrink[] softDrinks, Broccoli[] broccoli, Pizza[] pizza, Platform bottomPlatform,
				float bottomPlatformY, Output scoreOutput, ImmovableEntity[] hearts, EntityManager entityManager) {
			// Reset player position and velocity
			player.setX(10);
			player.setY(300);
			player.setVelocityY(0);
			player.setVelocityX(0);

			// Reset entity positions
			for (int i = 0; i < waterBottles.length; i++) {
				float newX = Gdx.graphics.getWidth() + 50;
				float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
				waterBottles[i].setPosition(newX, newY);
			}
            
			for (int i = 0; i < softDrinks.length; i++) {
				float newX = Gdx.graphics.getWidth() + 50;
				float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
				softDrinks[i].setPosition(newX, newY);
			}
			
			for (int i = 0; i < broccoli.length; i++) {
				float newX = Gdx.graphics.getWidth() + 50;
				float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
				broccoli[i].setPosition(newX, newY);
			}
		    
			for (int i = 0; i < pizza.length; i++) {
				float newX = Gdx.graphics.getWidth() + 50;
				float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
				pizza[i].setPosition(newX, newY);
			}
			
			// Reset platform positions
			bottomPlatform.setPosition(0, bottomPlatformY);

			// Add hearts
			for (int i = 0; i < hearts.length; i++) {
				hearts[i] = new Health(10 + (i * 40), 650);
				entityManager.addEntities(hearts[i]);
			}

			// Go to GameOver state
			sceneManager.setState(SceneManager.STATE.GameOver);
		}

		public void dispose() {
			batch.dispose();
			shape.dispose();
		}
	}