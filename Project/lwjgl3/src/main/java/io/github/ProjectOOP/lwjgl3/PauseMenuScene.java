package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseMenuScene extends Scene {
	
    private Texture resumeButtonTexture; 

    public PauseMenuScene() {
        super("pause_background.png", 0, 0); 
        resumeButtonTexture = new Texture(Gdx.files.internal("resume_button.png"));

    }

    @Override
    public void draw(SpriteBatch batch) {

        super.draw(batch);
        batch.draw(resumeButtonTexture, 0, 0);

    }
    
    @Override
    public void dispose() {
        super.dispose(); 
        if (resumeButtonTexture != null) {
            resumeButtonTexture.dispose(); 
        }
    }
}