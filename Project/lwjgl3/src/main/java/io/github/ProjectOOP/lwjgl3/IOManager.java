package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class IOManager {
    public boolean isPauseKeyPressed() {
        return Gdx.input.isKeyJustPressed(Keys.ESCAPE);
    }
}