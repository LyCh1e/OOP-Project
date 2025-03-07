package ProjectOOP.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MovableEntity extends Entity {
    protected boolean isJumping = false;
    protected float gravity = 9.8f;
    protected boolean affectedByGravity = false;

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
            // Add jumping physics here - now implemented in Player class
        }
    }

    public void land() {
        isJumping = false;
    }
    
    public boolean isJumping() {
        return isJumping;
    }
    
    public void setAffectedByGravity(boolean affected) {
        this.affectedByGravity = affected;
    }

    @Override
    public void updatePosition() {
        // Store previous position BEFORE modifying x and y
        float prevX = getX();
        float prevY = getY();

        // Apply gravity if needed
        if (affectedByGravity && isJumping) {
            velocityY -= gravity * Gdx.graphics.getDeltaTime();
        }
        
        // Update position based on velocity
        setX(getX() + velocityX * Gdx.graphics.getDeltaTime());
        setY(getY() + velocityY * Gdx.graphics.getDeltaTime());

        System.out.println("Previous Spawn: X = " + prevX + ", Y = " + prevY);
        System.out.println("X position of movable entity = " + getX() + "\n");
        System.out.println("Y position of movable entity = " + getY() + "\n");
    }

    @Override
    public void draw(ShapeRenderer shape) {
        // Default implementation
    }
}