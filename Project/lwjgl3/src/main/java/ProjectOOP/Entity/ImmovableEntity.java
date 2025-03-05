package ProjectOOP.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ImmovableEntity extends Entity {

    ImmovableEntity() { }

    public ImmovableEntity(String str, float x, float y, float s) {
        setX(x);
        setY(y);
        setSpeed(s);
        texture = new Texture(Gdx.files.internal(str));

    }

    public void moveLeft() {
        velocityX = 0;
        System.out.println("No Moving Left");
    }

    public void moveRight() {
        velocityX = 0;
        System.out.println("No Moving Right");
    }

    public void stopMoving() {
        velocityX = 0;
        System.out.println("Stopped Moving");
    }

    @Override
    public void updatePosition() {
        // Store previous position BEFORE modifying x and y
        float prevX = getX();
        float prevY = getY();

        System.out.println("Previous Spawn: X = " + prevX + ", Y = " + prevY);
        System.out.println("Current Position: X = " + getX() + ", Y = " + getY());
    }

	@Override
	void draw(ShapeRenderer shape) {
		
	}

}
