package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Scene {
    private Texture texture;
    private float x_axis, y_axis;

    Scene () {
    }

    Scene (String str, float x, float y) {
        setX(x);
        setY(y);
        texture = new Texture(Gdx.files.internal(str));
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x_axis;
    }

    public float getY() {
        return y_axis;
    }

    void setTexture(Texture t) {
        texture = t;
    }

    void setX(float x) {
        x_axis = x;
    }

    void setY (float y) {
        y_axis = y;
    }

    void draw (SpriteBatch batch) {
        batch.draw(getTexture(), getX(), getY());
    }
    
    public void dispose() {
        if (texture != null) { 
            texture.dispose();
        }
    }
}