package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScene extends Scene {
    private Stage stage;
    private Skin skin;
    private TextButton exitButton;
    private ImageButton testImageButton;

    public MainMenuScene(IOManager ioManager) {
        super("MainMenuBackground.png", 0, 0);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //load the skinjson for ui elements, got from libgdx github
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create text button, using libgdx official skin
        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition(Gdx.graphics.getWidth() - 150, 50); // Set position
        exitButton.setSize(100, 50); // Set size
        
       //testing image button
        Texture buttonTexture = new Texture(Gdx.files.internal("resume_button.png"));
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(buttonTexture);
        //testing image button
        testImageButton = new ImageButton(buttonDrawable);
        testImageButton.setPosition(Gdx.graphics.getWidth() - 1250, 50); // Set position  
        testImageButton.setSize(150, 100);  // Set size
       //testing image button
        testImageButton.addListener(ioManager.getClickListener(() -> System.out.println("Image Button Clicked! TEST WORKING")));
        
        // Use IOManager to check click listener
        exitButton.addListener(ioManager.getClickListener(() -> Gdx.app.exit())); //this is a "Runnable action" accepted by libgdx
        
        stage.addActor(exitButton);
        stage.addActor(testImageButton);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        batch.end(); // stop drawing "game" elements like sprite batch (there isnt one now)
        stage.act(Gdx.graphics.getDeltaTime()); //Updates UI elements like buttons, label, etc etc
        stage.draw(); //Draws all UI elements (Related to Scene2D)
        batch.begin(); //Resumes normal game rendering
        //reason is because libgdx cant render both spritebatch aka game stuff at the same time as UI elements (Scene2D UI stuff)
        //SCENE2D is ui stuff from libgdx!
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }
}
