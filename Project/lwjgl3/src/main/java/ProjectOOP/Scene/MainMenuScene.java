package ProjectOOP.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import ProjectOOP.IO.IOManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScene extends Scene {
    private Stage stage;
    private Skin skin;
    private TextButton exitButton;
    private TextButton startButton;
    private TextButton settingsButton;
    private TextButton tutorialButton;

    public MainMenuScene(IOManager ioManager, SceneManager sceneManager) {
        super("aquazoom.png", 0, 0);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //load the skinjson for ui elements, got from libgdx github
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create buttons
        startButton = new TextButton("Start Game", skin);
        settingsButton = new TextButton("Settings", skin);
        exitButton = new TextButton("Exit", skin);
        tutorialButton = new TextButton("Tutorial", skin);

        // Set button size
        float buttonWidth = 200;
        float buttonHeight = 60;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float spacing = 20; // Space between buttons

        // Button Position
        float centerX = (screenWidth - buttonWidth) / 2;
        float startY = (screenHeight / 2) + buttonHeight; // Start Game button at the top

        startButton.setSize(buttonWidth, buttonHeight);
        startButton.setPosition(centerX, startY);

        settingsButton.setSize(buttonWidth, buttonHeight);
        settingsButton.setPosition(centerX, startY - (buttonHeight + spacing));

        tutorialButton.setSize(buttonWidth, buttonHeight);
        tutorialButton.setPosition(centerX, startY - 2 * (buttonHeight + spacing));
        
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(centerX, startY - 3 * (buttonHeight + spacing));

        // Add listeners on button click
        startButton.addListener(ioManager.getClickListener(() -> sceneManager.setState(SceneManager.STATE.Start))); 
        settingsButton.addListener(ioManager.getClickListener(() -> sceneManager.setState(SceneManager.STATE.Settings))); 
        exitButton.addListener(ioManager.getClickListener(() -> Gdx.app.exit()));
        tutorialButton.addListener(ioManager.getClickListener(() -> sceneManager.setState(SceneManager.STATE.Tutorial))); 

        

        // Add buttons to the stage
        stage.addActor(startButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);
        stage.addActor(tutorialButton);
    
    }
    
    public Stage getStage() {
        return stage;
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
