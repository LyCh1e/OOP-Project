// SceneManager.java
package io.github.ProjectOOP.lwjgl3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class SceneManager implements Disposable {
    // Use a Map to associate states with lists of scenes
    private Map<STATE, List<Scene>> stateSceneMap = new HashMap<>();
    private STATE currentState = STATE.MainMenu; // MAINMENU state is the first state main screen
    private IOManager ioManager;

    public enum STATE {
        Start, End, Pause, Background, Settings, MainMenu //added new settings STATE
    }

    public SceneManager(IOManager ioManager) {
        // Initialize lists for each state in the constructor
        for (STATE state : STATE.values()) {
            stateSceneMap.put(state, new ArrayList<>());
        }
    }

    public void setState(STATE newState) {
        this.currentState = newState;
    }

    public STATE getState() {
        return currentState;
    }

    void drawScene(SpriteBatch batch) {
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
    void addSceneToState(STATE state, Scene scene) {
        stateSceneMap.get(state).add(scene);
    }
    
    public void switchScene(IOManager ioManager) {
        if (currentState == SceneManager.STATE.MainMenu) {
            if (ioManager.isJumping()) {
                setState(SceneManager.STATE.Start);
            }
        }
       
        if (ioManager.isEscape()) {
            // Toggle pause state
            if (currentState == SceneManager.STATE.Start) {
                setState(SceneManager.STATE.Pause);
            } else if (currentState == SceneManager.STATE.Pause) {
                setState(SceneManager.STATE.Start);
            }
        }

        if (ioManager.isNum1()) {
            // Toggle settings state, settings can only be opened from pause menu, click 1 to open settings
            if (currentState == SceneManager.STATE.Pause) {
                setState(SceneManager.STATE.Settings);
            } else if (currentState == SceneManager.STATE.Settings) {
               setState(SceneManager.STATE.Pause);
            }
        }
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