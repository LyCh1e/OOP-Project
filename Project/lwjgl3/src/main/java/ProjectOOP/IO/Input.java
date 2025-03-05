package ProjectOOP.IO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Input {
    private ClickListener click;
    private boolean forceJumpFalse = false; 
    //private KeyBindings keyBindings;

    public Input() {
    	//this.keyBindings = new KeyBindings();
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
   // public boolean isClicked(Rectangle bounds) {
     //   if (Gdx.input.justTouched()) {
       //     int touchX = Gdx.input.getX();
         //   int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
          //  return bounds.contains(touchX, touchY);
        //}
        //return false;
   // }
    
    // Click handling through ClickListener only works for scene2d aka UI elements cant work directly with spritebatch aka game elements from what i understand
    public ClickListener createClickListener(final Runnable action) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run(); // Execute the action when clicked
            }
        };
    }
    
    //method to interact with checkbox
    public void checkBoxListen(CheckBox checkbox, Runnable onChecked, Runnable onUnchecked) {
        checkbox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean isChecked = checkbox.isChecked();
                if (isChecked) {
                    onChecked.run();
                } else {
                    onUnchecked.run();
                }
                System.out.println("checkbox changed to: " + isChecked);
            }
        });
    }

    //method to interact with sliders
    public void sliderListen(Slider slider, Runnable onChange) {
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onChange.run();
                System.out.println("slider value changed to: " + slider.getValue());
            }
        });
    }
}