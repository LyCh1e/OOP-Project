package io.github.ProjectOOP.lwjgl3;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {
	private List<Scene> sceneList = new ArrayList<>();
	
	void addScenes(Scene s) {
		sceneList.add(s);
	}
	
	
}
