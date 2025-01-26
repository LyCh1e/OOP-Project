package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Input {
	private ClickListener click;
	
	public ClickListener getClick() {
		return click;
	}
	
	void setClick(ClickListener c) {
		click = c;
	}
}
