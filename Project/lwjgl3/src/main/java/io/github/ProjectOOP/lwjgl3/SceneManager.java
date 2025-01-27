package io.github.ProjectOOP.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class SceneManager implements Disposable {
    private List<Scene> sceneList = new ArrayList<>();

    public enum STATE {
        Start, End, Pause, Background
    }

    private STATE currentState = STATE.Start; // Start state is the first state, the game playing

    public void setState(STATE newState) {
        this.currentState = newState;
    }

    public STATE getState() {
        return currentState;
    }

    void drawScene(SpriteBatch batch) {
        batch.begin();
        for (Scene scene : sceneList) {
            if (currentState == STATE.Start) {
                // In game/Start state, draw scenes that are NOT PauseMenuScene, and draw everything else
                if (!(scene instanceof PauseMenuScene)) {
                    scene.draw(batch);
                }
            } else if (currentState == STATE.Pause) {
            	if ((scene instanceof PauseMenuScene)) {
                    scene.draw(batch);
                }
            }
            // Can Add more 'else if'  for other states like example leaderboard, end state or smth in the future if need
            //just follow the template
        }
        batch.end();
    }

    void addScenes(Scene s) { 
        sceneList.add(s);
    }

    @Override
    public void dispose() {
        for (Scene scene : sceneList) {
            scene.dispose();
        }
        sceneList.clear();;
    }
}