package ProjectOOP.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MovableEntity extends Entity {
    private boolean isJumping = false;

    MovableEntity() { }

    public MovableEntity(String str, float x, float y, float s) {
        setX(x);
        setY(y);
        setSpeed(s);
        texture = new Texture(Gdx.files.internal(str));
    }

    public void moveLeft() {
        velocityX = -speed;
        System.out.println("Moving Left");
    }

    public void moveRight() {
        velocityX = speed;
        System.out.println("Moving Right");
    }

    public void stopMoving() {
        velocityX = 0;
        System.out.println("Stopped Moving");
    }

    public void jump() {
        if (!isJumping) { // Prevent double jumping
            isJumping = true;
            System.out.println("Jumping");
            // Add jumping physics here
        }
    }

    public void land() {
        isJumping = false;
    }

    @Override
    public void updatePosition() {
        // Store previous position BEFORE modifying x and y
        float prevX = getX();
        float prevY = getY();

        System.out.println("Previous Spawn: X = " + prevX + ", Y = " + prevY);
        System.out.println("X position of movable entity = " + getX() + "\n");
        System.out.println("Y position of movable entity = " + getY() + "\n");
    }

	@Override
	public void draw(ShapeRenderer shape) {
		
	}

}
