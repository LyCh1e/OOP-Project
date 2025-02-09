package io.github.ProjectOOP.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class IOManager {
    private Input input;
    private List<Output> outputList = new ArrayList<>();
    
    public IOManager(Input in) {
        input = in;
    }
    
    public void addOutput(Output out) {
    	outputList.add(out);
    }
    
    public boolean isEscape() {
    	return input.isEscape();
    }
    
    public boolean isNum1() {
    	return input.isNum1();
    }
    

    public boolean isMovingLeft() {
        return input.isMovingLeft();
    }
    
    public boolean isMovingRight() {
        return input.isMovingRight();
    }
    
    public boolean isJumping() {
        return input.isJumping(); // Now uses Input's isJumping which checks forceJumpFalse
    }

    public void setForceJumpFalse(boolean force) { //Force jump to be false for first few frames before switching to avoid overlap (implemented in gamemaster)
        input.setForceJumpFalse(force);
    }
    
    //public boolean isClicked(Rectangle bounds) {
    //    return input.isClicked(bounds);
   // }
    
    // click listener
    public ClickListener getClickListener(Runnable action) {
        return input.createClickListener(action);
    }
    
    // Method to render text on the screen
    public void draw(SpriteBatch batch) {
    	batch.begin();
    		for (int i = 0; i < outputList.size(); i++) {
    				outputList.get(i).draw(batch);
    		}
    	batch.end();
    }
}