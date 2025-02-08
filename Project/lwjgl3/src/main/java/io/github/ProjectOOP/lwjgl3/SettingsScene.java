package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsScene extends Scene {
    private float volume = 0.2f;
    private Music backgroundMusic;
    private IOManager ioManager; 

 
    public SettingsScene(Music backgroundMusic, IOManager ioManager) {
        super("SettingsBackground.png", 0, 0);
        this.backgroundMusic = backgroundMusic;
        this.ioManager = ioManager;
        this.backgroundMusic.setVolume(volume); // Match Volume
    }

    // Update method to adjust volume based on left right key presses
    public void updateVolume() {
        // Use IOManager methods for left/ right and A/D
        if (ioManager.isMovingLeft()) {
        	volume = Math.max(0, volume - 0.01f); //this is needed if not the thing will crash once its below 0
            backgroundMusic.setVolume(volume);
            System.out.println("Volume decreased to: " + volume); //see in console what the volume is now
        }
        if (ioManager.isMovingRight()) {
        	volume = Math.min(1, volume + 0.01f); //this is needed if not the thing will crash once its above 1
            backgroundMusic.setVolume(volume);
            System.out.println("Volume increased to: " + volume); //see in console what the volume is now
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Update the volume before drawing
        updateVolume();
        super.draw(batch);
        // Optionally, display volume information on-screen here
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
