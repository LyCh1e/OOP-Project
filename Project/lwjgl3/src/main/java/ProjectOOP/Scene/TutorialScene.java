package ProjectOOP.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import ProjectOOP.IO.IOManager;

public class TutorialScene extends Scene {
    private Stage stage;
    private Skin skin;
    private TextButton backButton;

    public TutorialScene(IOManager ioManager, SceneManager sceneManager) {
        super("tutorialPng.png", 0, 0); 
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //load the skinjson for ui elements, got from libgdx github
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        backButton = new TextButton("Back", skin);


        
        // Set button size
        float buttonWidth = 200;
        float buttonHeight = 60;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float spacing = 20; // Space between buttons

        // Button Position
        float buttonX = screenWidth - buttonWidth - spacing;
        float buttonY = spacing;

        backButton.setSize(buttonWidth, buttonHeight);
        backButton.setPosition(buttonX, buttonY);

        
        // Add listeners on button click
        backButton.addListener(ioManager.getClickListener(() -> sceneManager.setState(SceneManager.STATE.MainMenu))); 

        // Add buttons to the stage
        stage.addActor(backButton);

    }
    
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        batch.end(); // stop drawing "game" elements like sprite batch (there isnt one now)
    	Gdx.input.setInputProcessor(stage);
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