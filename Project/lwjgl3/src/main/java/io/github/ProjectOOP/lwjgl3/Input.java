package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Input {
    private ClickListener click;
    private boolean forceJumpFalse = false; 
//    private KeyBindings keyBindings;

    public Input() {
//      this.keyBindings = new KeyBindings();
  }
    
    // New methods from IOManager moved here
    public boolean isMovingLeft() {
        return Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A);
    }
    
    public boolean isMovingRight() {
        return Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D);
    }
    
    public boolean isJumping() {
        if (forceJumpFalse) { // CHECK FORCE FLAG FIRST
            return false;
        }
        return (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE));
    }
    
    public void setForceJumpFalse(boolean force) { // Force the jump to stop
        forceJumpFalse = force;
    }
    
    public boolean isEscape() {
        return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
    }
    
    public boolean isNum1() {
        return Gdx.input.isKeyJustPressed(Keys.NUM_1);
    }
    

//    // Existing methods remain unchanged
//    public boolean keyDown(int keycode) {
//        if (keycode == keyBindings.getKey("left")) {
//            player.moveLeft();
//        } else if (keycode == keyBindings.getKey("right")) {
//            player.moveRight();
//        } else if (keycode == keyBindings.getKey("jump")) {
//            player.jump();
//        }
//        return true;
//    }
//
//    public boolean keyUp(int keycode) {
//        if (keycode == keyBindings.getKey("left") || keycode == keyBindings.getKey("right")) {
//            player.stopMoving();
//        }
//        return true;
//    }
    
    public ClickListener getClick() {
        return click;
    }
    
    void setClick(ClickListener c) {
        click = c;
    }
    
  //get coordinates of where the user touch/click. If it is in the bounds of the rectangle, more general approach, reuseable
    public boolean isClicked(Rectangle bounds) {
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            return bounds.contains(touchX, touchY);
        }
        return false;
    }
}
