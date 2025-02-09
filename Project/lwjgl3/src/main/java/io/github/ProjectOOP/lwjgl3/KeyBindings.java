package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input.Keys;

public class KeyBindings {
    private Preferences prefs;
    private boolean initialized = false;
    
    // Default key values
    private int leftKey = Keys.A;
    private int rightKey = Keys.D;
    private int jumpKey = Keys.SPACE;
    
    public void initialize() {
        if (!initialized && Gdx.app != null) {
            prefs = Gdx.app.getPreferences("KeyBindings");
            loadDefaultKeys();
            initialized = true;
        }
    }

    private void loadDefaultKeys() {
        if (!prefs.contains("left")) {
        	prefs.putInteger("left", leftKey);
        }
        
        if (!prefs.contains("right")) {
        	prefs.putInteger("right", rightKey);
        }
        
        if (!prefs.contains("jump")) {
        	prefs.putInteger("jump", jumpKey);
        }
        prefs.flush();
    }

    public int getKey(String action) {
        if (!initialized) {
            return getDefaultKey(action);
        }
        
        return prefs.getInteger(action, getDefaultKey(action));
    }
    
    private int getDefaultKey(String action) {
        switch(action) {
            case "left": return leftKey;
            case "right": return rightKey;
            case "jump": return jumpKey;
            default: return Keys.UNKNOWN;
        }
    }

    public void setKey(String action, int key) {
        if (initialized) {
            prefs.putInteger(action, key);
            prefs.flush();
        }
    }
}