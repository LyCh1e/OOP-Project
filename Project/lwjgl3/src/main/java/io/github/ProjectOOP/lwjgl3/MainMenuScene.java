package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScene extends Scene {

    private Texture exitButtonTexture;
    private Sprite exitButtonSprite;
    private Rectangle exitButtonBounds;

    public MainMenuScene() {
        super("MainMenuBackground.png", 0, 0);

        exitButtonTexture = new Texture(Gdx.files.internal("resume_button.png"));
        exitButtonSprite = new Sprite(exitButtonTexture);

        // Position button at bottom-right
        float x = Gdx.graphics.getWidth() - exitButtonSprite.getWidth() - 20;
        float y = 20;
        exitButtonSprite.setPosition(x, y);
        
        // Create rectangle to so called "overlay" on top of the sprite for click detection
        exitButtonBounds = new Rectangle(x, y, exitButtonSprite.getWidth(), exitButtonSprite.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        exitButtonSprite.draw(batch);

        
        //get coordinates of where the user touch/click. If it is in the bounds of the rectangle, app will exit
        if (Gdx.input.justTouched()) {
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); 

            if (exitButtonBounds.contains(touchX, touchY)) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        exitButtonTexture.dispose();
    }
}
