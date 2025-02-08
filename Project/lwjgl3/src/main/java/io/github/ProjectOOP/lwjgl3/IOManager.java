package io.github.ProjectOOP.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class IOManager {
    private Input input;
    private List<Output> outputList = new ArrayList<>();
    
    public IOManager(Input in) {
        input = in;
    }
    
    public void addOutput(Output out) {
    	outputList.add(out);
    }
    
    public boolean isPauseKeyPressed() {
        return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
    }
    
    public boolean isSettingsKeyPressed() {
        return Gdx.input.isKeyJustPressed(Keys.NUM_1);
    }
    

    public boolean isMovingLeft() {
        return input.isMovingLeft();
    }
    
    public boolean isMovingRight() {
        return input.isMovingRight();
    }
    
    public boolean isJumping() {
        return input.isJumping();
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