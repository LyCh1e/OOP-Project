package ProjectOOP.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Scene {
    private Texture texture;
    private float x_axis, y_axis;

    Scene () {
    	
    }

    public Scene (String str, float x, float y) {
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
    
    public Stage getStage() {
        return null;
    }
    
    public void dispose() {
        if (texture != null) { 
            texture.dispose();
        }
    }
}