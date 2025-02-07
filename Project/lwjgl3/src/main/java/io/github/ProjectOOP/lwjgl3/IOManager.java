package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class IOManager {
    private Input input;
    
    public IOManager(Input input) {
        this.input = input;
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
}