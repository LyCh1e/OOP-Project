package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Input;

public class KeyBindings {
    private Preferences prefs;
    
//    public KeyBindings() {
//        prefs = Gdx.app.getPreferences("KeyBindings");
//
//        // Load default keys if not set
//        if (!prefs.contains("left")) prefs.putInteger("left", Input.Keys.LEFT);
//        if (!prefs.contains("right")) prefs.putInteger("right", Input.Keys.RIGHT);
//        if (!prefs.contains("jump")) prefs.putInteger("jump", Input.Keys.SPACE);
//        prefs.flush(); // Save changes
//    }

    public int getKey(String action) {
        return prefs.getInteger(action, Input.Keys.UNKNOWN);
    }

    public void setKey(String action, int key) {
        prefs.putInteger(action, key);
        prefs.flush(); // Save changes
    }
}
