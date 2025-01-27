package io.github.ProjectOOP.lwjgl3;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
	private List<Scene> sceneList = new ArrayList<>();
	
	enum STATE{
		Start, End, Pause, Background
	}
	
	void addScenes(Scene s) {
		sceneList.add(s);
	}
	
	void drawScene (SpriteBatch batch) {
		batch.begin();
		for (int i = 0; i < sceneList.size(); i++) {
			sceneList.get(i).draw(batch);
		}
		batch.end();
	}
}
