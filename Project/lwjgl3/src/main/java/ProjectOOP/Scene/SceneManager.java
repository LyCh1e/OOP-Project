// SceneManager.java
package ProjectOOP.Scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

import ProjectOOP.Entity.EntityManager;
import ProjectOOP.Entity.ImmovableEntity;
import ProjectOOP.Entity.MovableEntity;
import ProjectOOP.Entity.Platform;
import ProjectOOP.Entity.SoftDrink;
import ProjectOOP.Entity.Water;
import ProjectOOP.IO.IOManager;
import ProjectOOP.IO.Output;

public class SceneManager implements Disposable {
    // Use a Map to associate states with lists of scenes
    private Map<STATE, List<Scene>> stateSceneMap = new HashMap<>();
    private STATE currentState = STATE.MainMenu; // MAINMENU state is the first state main screen
    private STATE prevState;


    public enum STATE {
        Start, End, Pause, Background, Settings, MainMenu, GameOver //added new settings STATE
    }

    public SceneManager(IOManager ioManager) {
        // Initialize lists for each state in the constructor
        for (STATE state : STATE.values()) {
            stateSceneMap.put(state, new ArrayList<>());
        }
    }

    public void setState(STATE newState) {
        this.prevState = this.currentState; // Save current state before changing
        this.currentState = newState;
    }

    public STATE getPrevState() {
        return prevState;
    }

    public STATE getState() {
        return currentState;
    }

    public void drawScene(SpriteBatch batch) {
        batch.begin();
        // Get the list of scenes associated with the current state
        List<Scene> currentScenes = stateSceneMap.get(currentState);
        if (currentScenes != null) {
            for (Scene scene : currentScenes) {
                scene.draw(batch);
            }
        }
        batch.end();
    }

    // Method to add a scene to a specific state, use in gamemaster
    public void addSceneToState(STATE state, Scene scene) {
        stateSceneMap.get(state).add(scene);
    }
    
    public void switchScene(IOManager ioManager) {
        if (ioManager.isEscape()) {
            // Toggle pause state
            if (currentState == SceneManager.STATE.Start) {
                setState(SceneManager.STATE.Pause);
            } else if (currentState == SceneManager.STATE.Pause) {
                setState(SceneManager.STATE.Start);
            }
        }
    }

    public void resetGame(MovableEntity player, Water[] waterBottles, SoftDrink[] softDrinks,
            Platform bottomPlatform, float bottomPlatformY, Output staminaOutput, 
            Output scoreOutput, ImmovableEntity[] hearts, EntityManager entityManager) {
		// Reset player position and velocity
		player.setX(10);
		player.setY(300);
		player.setVelocityY(0);
		player.setVelocityX(0);
		
		// Reset stamina
		float stamina = 30;
		staminaOutput.setNumber(stamina);
		staminaOutput.setString("Stamina: " + Math.round(stamina));
		
		// Reset score
		scoreOutput.setNumber(0);
		scoreOutput.setString("Score: 0");
		
		// Reset entity positions
		for (int i = 0; i < waterBottles.length; i++) {
		float newX = Gdx.graphics.getWidth() + 50;
		float newY = (float) Math.random() * (Gdx.graphics.getHeight() - 200) + 100;
		waterBottles[i].setPosition(newX, newY);
		}
		
		for (int i = 0; i < softDrinks.length; i++) {
		float newX = Gdx.graphics.getWidth() + 50;
		float minY = 50;
		float maxY = 250;
		float newY = (float) Math.random() * (maxY - minY) + minY;
		softDrinks[i].setPosition(newX, newY);
		}
		
		// Reset platform positions
		bottomPlatform.setPosition(0, bottomPlatformY);
		
        // Add hearts
        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new ImmovableEntity("heart.png", 10 + (i * 40), 650, 0);
            entityManager.addEntities(hearts[i]);
        }
		
		// Go to gameover state
		setState(SceneManager.STATE.GameOver);
}



    	
    @Override
    public void dispose() {
        for (List<Scene> sceneList : stateSceneMap.values()) {
            for (Scene scene : sceneList) {
                scene.dispose();
            }
        }
        stateSceneMap.clear();
    }
}