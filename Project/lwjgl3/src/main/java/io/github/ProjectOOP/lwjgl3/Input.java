package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Input {
    private ClickListener click;
    private KeyBindings keyBindings;

    
    public Input() {
      this.keyBindings = new KeyBindings();
  }
    
    // New methods from IOManager moved here
    public boolean isMovingLeft() {
        return Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A);
    }
    
    public boolean isMovingRight() {
        return Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D);
    }
    
    public boolean isJumping() {
        return (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE));
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
}
