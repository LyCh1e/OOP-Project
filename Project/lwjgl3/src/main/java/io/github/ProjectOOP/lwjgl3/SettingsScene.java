package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.Gdx;

public class SettingsScene extends Scene {
    private float volume = 0.2f;
    private boolean isMuted = false;
    private IOManager ioManager;
    private Stage stage;
    private Skin skin;
    private CheckBox muteCheckbox;
    private Slider volumeSlider;
 
    public SettingsScene(IOManager ioManager) {
        super("SettingsBackground2.png", 0, 0);
        this.ioManager = ioManager;
        
        stage = new Stage();  
        Gdx.input.setInputProcessor(stage); // UI receives input
        
        // Load PRIVATE Skin for this scene
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        
        Label volumeLabel = new Label("Volume", skin);
        volumeLabel.setFontScale(1.5f);
        
        // Create Mute Checkbox
        muteCheckbox = new CheckBox(" Mute", skin);
        muteCheckbox.setChecked(false);
        // Call method from ioManager
        ioManager.checkBoxListen(muteCheckbox, () -> {
                isMuted = true;
                ioManager.setMusicVolume(0); //now using ioManager's set volume function
            }, () -> {
                isMuted = false;
                ioManager.setMusicVolume(volume); //now using ioManager's set volume function
            }
        );

        // Create Volume Slider (min:0,max:1,each step 0.01, false means slider is horizontal)
        volumeSlider = new Slider(0, 1, 0.01f, false, skin);
        volumeSlider.setValue(volume);
        // Call method from ioManager
        ioManager.sliderListen(volumeSlider, () -> {
            if (!isMuted) {
                volume = volumeSlider.getValue();
                ioManager.setMusicVolume(volume); //now using ioManager's set volume function
            }
        });

        // UI Layout to make things more organized, less messy
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        //increase size of checkbox
        muteCheckbox.getLabel().setFontScale(1.5f);
        muteCheckbox.getImage().setScale(1.5f);

        table.add(muteCheckbox).center().padBottom(20).row();
        table.add(volumeLabel).center().padBottom(10).row();
        table.add(volumeSlider).width(300).height(50).center(); // No ".row()" as it is the last element

        stage.addActor(table);
    }

    // Update method to adjust volume based on left right key presses
    public void updateVolume() {
        // Use IOManager methods for left/ right and A/D
        if (ioManager.isMovingLeft() && !isMuted) { // Make sure that its not muted before changing
        	volume = Math.max(0, volume - 0.01f); //this is needed if not the thing will crash once its below 0
        	   ioManager.setMusicVolume(volume); //now using ioManager's set volume function
            volumeSlider.setValue(volume); // Change slider value based on left/right A/D presses
            System.out.println("Volume decreased to: " + volume); //see in console what the volume is now
        }
        if (ioManager.isMovingRight() && !isMuted) { // Make sure that its not muted before changing
        	volume = Math.min(1, volume + 0.01f); //this is needed if not the thing will crash once its above 1
        	   ioManager.setMusicVolume(volume); //now using ioManager's set volume function
            volumeSlider.setValue(volume); // Change slider value based on left/right A/D presses
            System.out.println("Volume increased to: " + volume); //see in console what the volume is now
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
    	//same logic as in MainMenu
        super.draw(batch);
        updateVolume();
        batch.end();
        	Gdx.input.setInputProcessor(stage);
        	stage.act(Gdx.graphics.getDeltaTime());
        	stage.draw();
        batch.begin();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}