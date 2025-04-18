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
        return (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.W));
    }
    
    public static boolean dropDown() {
        return (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S));
    }
    
    public void setForceJumpFalse(boolean force) { // Force the jump to stop
        forceJumpFalse = force;
    }
    
    public boolean isEscape() {
        return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
    }
    
    
    public ClickListener getClick() {
        return click;
    }
    
    void setClick(ClickListener c) {
        click = c;
    }
    
    
    // Click handling through ClickListener only works for scene2d aka UI elements
    public ClickListener createClickListener(final Runnable action) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run(); // Execute the action when clicked
            }
        };
    }
    
    // method to interact with checkbox
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

    // method to interact with sliders
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