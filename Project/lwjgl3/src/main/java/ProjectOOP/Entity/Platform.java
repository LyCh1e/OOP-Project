package ProjectOOP.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Platform extends MovableEntity {
    private float minY, maxY;
    private Rectangle bounds;
    private boolean moveVertically = false;
    
    private boolean isBottomPlatform = false;
    private float scrollSpeed;
    private float screenWidth;
    private List<Rectangle> segments = new ArrayList<>();
    private float segmentWidth = 100;
    private Random random = new Random();
    
    public Platform() {
        // Default constructor
    }
    
    public Platform(String texturePath, float x, float y, float width, float height, float speed) {
        super(texturePath, x, y, speed);
        bounds = new Rectangle(x, y, width, height);
    }
    
    public Platform(String texturePath, float x, float y, float width, float height, float minY, float maxY, float speed) {
        super(texturePath, x, y, speed);
        this.minY = minY;
        this.maxY = maxY;
        bounds = new Rectangle(x, y, width, height);
        moveVertically = true;
        // Set initial vertical velocity
        setVelocityY(speed);
    }
    
//    @Override
//    public void updatePosition() {
//        // Store previous position
//        float prevX = getX();
//        float prevY = getY();
//        
//        if (moveVertically) {
//            // Move platform vertically
//            setY(getY() + getVelocityY() * Gdx.graphics.getDeltaTime());
//            
//            // Reverse direction if reached boundaries
//            if (getY() < minY || getY() > maxY) {
//                setVelocityY(-getVelocityY());
//            }
//        }
//        
//        // Update bounds
//        bounds.setPosition(getX(), getY());
//    }
    
    @Override
    public void draw(ShapeRenderer shape) {
        // If needed, can draw a rectangle for the platform here
        // shape.rect(getX(), getY(), bounds.width, bounds.height);
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
    
    // Method to create a bottom platform with a hole
    public static Platform createBottomPlatform(String texturePath, float x, float y, float width, float height, float speed) {
        Platform platform = new Platform(texturePath, x, y, width, height, speed);
        return platform;
    }
    
 // Add this to Platform.java


    public void setAsBottomPlatform(float scrollSpeed, float screenWidth) {
        this.isBottomPlatform = true;
        this.scrollSpeed = scrollSpeed;
        this.screenWidth = screenWidth;
        initializeSegments();
    }

    private void initializeSegments() {
        float currentX = 0;
        while (currentX < screenWidth + segmentWidth) {
            segments.add(new Rectangle(currentX, getY(), segmentWidth, bounds.height));
            currentX += segmentWidth;
        }
    }

    // Then modify updatePosition() to handle bottom platform logic:
    @Override
    public void updatePosition() {
        if (isBottomPlatform) {
            // Move all segments
            for (Rectangle segment : segments) {
                segment.x += scrollSpeed * Gdx.graphics.getDeltaTime();
            }
            
            // Remove segments that are off-screen
            for (int i = segments.size() - 1; i >= 0; i--) {
                if (segments.get(i).x + segments.get(i).width < 0) {
                    segments.remove(i);
                }
            }
            
            // Add new segments as needed
            if (segments.size() > 0) {
                Rectangle lastSegment = segments.get(segments.size() - 1);
                float rightEdge = lastSegment.x + lastSegment.width;
                
                if (rightEdge < screenWidth) {
                    float newX = rightEdge;
                    
                    
                    segments.add(new Rectangle(newX, getY(), segmentWidth, bounds.height));
                }
            } else {
                // If all segments are gone, add a new one
                segments.add(new Rectangle(screenWidth, getY(), segmentWidth, bounds.height));
            }
        } else {
            // Normal platform movement
            // Store previous position
            float prevX = getX();
            float prevY = getY();
            
            if (moveVertically) {
                // Move platform vertically
                setY(getY() + getVelocityY() * Gdx.graphics.getDeltaTime());
                
                // Reverse direction if reached boundaries
                if (getY() < minY || getY() > maxY) {
                    setVelocityY(-getVelocityY());
                }
            }
            
            // Update bounds
            bounds.setPosition(getX(), getY());
        }
    }
    
    public void setPosition(float newX, float newY) {
        setX(newX);
        setY(newY);
    }

    // Override draw method to handle segments
    @Override
    public void draw(SpriteBatch batch) {
        if (isBottomPlatform) {
            for (Rectangle segment : segments) {
                batch.draw(getTexture(), segment.x, segment.y, segment.width, segment.height);
            }
        } else {
            super.draw(batch);
        }
    }

    // Add this to your Platform class
    public List<Rectangle> getSegments() {
        return segments;
    }
}