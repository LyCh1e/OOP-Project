package ProjectOOP.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import ProjectOOP.IO.IOManager;


public class GameOverScene extends Scene {
    private Stage stage;
    private Skin skin;
    private TextButton menuButton;
    private TextButton exitButton;
    private TextButton labelButton;
    private int finalScore;
	
	public GameOverScene(IOManager ioManager, SceneManager sceneManager) {
        super("gameOverBg.png", 0, 0); 
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        
        //load the skinjson for ui elements, got from libgdx github
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        menuButton = new TextButton("Main Menu", skin);
        exitButton = new TextButton("Exit Game", skin);
        labelButton = new TextButton("Score: 0", skin);
        
        // Set button size
        float buttonWidth = 200;
        float buttonHeight = 60;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float spacing = 20; // Space between buttons
        
        // Button Position
        float centerX = (screenWidth - buttonWidth) / 2;
        float startY = (screenHeight / 2) + buttonHeight; // Score Label at the top

        labelButton.setSize(buttonWidth, buttonHeight);
        labelButton.setPosition(centerX, startY);

        menuButton.setSize(buttonWidth, buttonHeight);
        menuButton.setPosition(centerX, startY - (buttonHeight + spacing));
        
        exitButton.setSize(buttonWidth, buttonHeight);
        exitButton.setPosition(centerX, startY - 2*(buttonHeight + spacing));
       
        
        menuButton.addListener(ioManager.getClickListener(() -> sceneManager.setState(SceneManager.STATE.MainMenu))); 
        exitButton.addListener(ioManager.getClickListener(() -> Gdx.app.exit()));
        
        stage.addActor(labelButton);
        stage.addActor(menuButton);
        stage.addActor(exitButton);
	}
	
	public void setFinalScore(int score) {
	    this.finalScore = score;
	    if (labelButton != null) {
	    	labelButton.setText("Final Score: " + score);
	    }
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
