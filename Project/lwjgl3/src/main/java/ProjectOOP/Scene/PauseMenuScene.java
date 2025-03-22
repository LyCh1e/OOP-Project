package ProjectOOP.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import ProjectOOP.IO.IOManager;

public class PauseMenuScene extends Scene {
    private Stage stage;
    private Skin skin;
    private TextButton resumeButton;
    private TextButton settingsButton;
    private TextButton menuButton;
    private TextButton exitButton;

    public PauseMenuScene(IOManager ioManager, SceneManager sceneManager) {
        super("pauseBackground.png", 0, 0); 
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //load the skinjson for ui elements, got from libgdx github
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        resumeButton = new TextButton("Resume", skin);
        settingsButton = new TextButton("Settings", skin);
        menuButton = new TextButton("Main Menu", skin);
        exitButton = new TextButton("Exit Game", skin);

        
        // Set button size
        float buttonWidth = 200;
        float buttonHeight = 60;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float spacing = 20; // Space between buttons

        // Button Position
        float centerX = (screenWidth - buttonWidth) / 2;
        float startY = (screenHeight / 2) + buttonHeight; // Start Game button at the top

        resumeButton.setSize(buttonWidth, buttonHeight);
        resumeButton.setPosition(centerX, startY);

        settingsButton.setSize(buttonWidth, buttonHeight);
        settingsButton.setPosition(centerX, startY - (buttonHeight + spacing));
        
        menuButton.setSize(buttonWidth, buttonHeight);
        menuButton.setPosition(centerX, startY - 2 * (buttonHeight + spacing));

        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(centerX, startY - 3 * (buttonHeight + spacing));
        
        // Add listeners on button click
        resumeButton.addListener(ioManager.getClickListener(() -> sceneManager.setState(SceneManager.STATE.Start))); 
        settingsButton.addListener(ioManager.getClickListener(() -> sceneManager.setState(SceneManager.STATE.Settings))); 
        menuButton.addListener(ioManager.getClickListener(() -> sceneManager.setState(SceneManager.STATE.MainMenu))); 
        exitButton.addListener(ioManager.getClickListener(() -> Gdx.app.exit()));

        // Add buttons to the stage
        stage.addActor(resumeButton);
        stage.addActor(settingsButton);
        stage.addActor(menuButton);
        stage.addActor(exitButton);

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