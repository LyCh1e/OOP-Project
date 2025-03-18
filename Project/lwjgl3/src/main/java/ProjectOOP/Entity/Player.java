package ProjectOOP.Entity;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Player extends MovableEntity {
    private boolean onPlatform = false;
    private Platform currentPlatform = null;
    private Rectangle bounds;
    
    // Constructor
    public Player() {
        // Default constructor
    }
    
    public Player(String texturePath, float x, float y, float speed) {
        super(texturePath, x, y, speed);
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
    
    @Override
    public void updatePosition() {
        // Store previous position
        float prevX = getX();
        float prevY = getY();
        
        // Apply stronger gravity when falling
        if (!onPlatform && (isJumping || getVelocityY() <= 0)) {
            // Apply a fixed falling speed rather than acceleration for more responsive feel
            if (getVelocityY() > -400) { // Terminal velocity cap
                setVelocityY(getVelocityY() - gravity); // Remove deltaTime for stronger effect
            }
        }
        
        // Update position based on velocity - use a larger multiplier for Y
        setX(getX() + velocityX * Gdx.graphics.getDeltaTime());
        setY(getY() + velocityY * Gdx.graphics.getDeltaTime() * 3.0f); // Amplify Y movement
        
        // Debug output
        System.out.println("Y velocity: " + velocityY + ", Y position: " + getY());
        
        // Update bounds
        if (bounds != null) {
            bounds.setPosition(getX(), getY());
        }
        
        // Only reset platform status if we're not just starting to jump
        if (!isJumping || velocityY < 0) {
            onPlatform = false;
            currentPlatform = null;
        }
    }
    
    // Check for platform collisions
    public void checkPlatformCollisions(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Platform) {
                Platform platform = (Platform) entity;
                if (checkPlatformCollision(platform)) {
                    break; // Only need to be on one platform at a time
                }
            }
        }
    }
    
    private boolean checkPlatformCollision(Platform platform) {
        Rectangle platformBounds = platform.getBounds();
        
        // Check if player is above platform and falling
        if (bounds.x + bounds.width > platformBounds.x && 
            bounds.x < platformBounds.x + platformBounds.width) {
            
            float playerBottom = bounds.y;
            float platformTop = platformBounds.y + platformBounds.height;
            
            // Previous bottom position
            float prevPlayerBottom = getPrevY();
            
            // If player was above platform in previous frame and is now below or at platform top
            if (prevPlayerBottom >= platformTop && playerBottom <= platformTop) {
                setY(platformTop);
                velocityY = 0;
                onPlatform = true;
                currentPlatform = platform;
                land(); // Use the existing land method to reset jumping state
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void jump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = 400; // Significantly higher jump velocity
            // Force position to be slightly above the platform to avoid immediate collision detection
            setY(getY() + 10); 
            System.out.println("Jumping with velocity: " + velocityY);
        }
    }
    
    // Getter for bounds (for collision detection)
    public Rectangle getBounds() {
        return bounds;
    }
    
    public void checkBottomPlatformCollision(Platform bottomPlatform) {
        if (bottomPlatform == null) return;
        
        List<Rectangle> segments = bottomPlatform.getSegments();
        boolean onAnySegment = false;
        
        for (Rectangle segment : segments) {
            if (bounds.x + bounds.width > segment.x && 
                bounds.x < segment.x + segment.width) {
                
                float playerBottom = bounds.y;
                float platformTop = segment.y + segment.height;
                
                // Previous bottom position
                float prevPlayerBottom = getPrevY();
                
                // If player was above platform in previous frame and is now below or at platform top
                if (prevPlayerBottom >= platformTop && playerBottom <= platformTop) {
                    setY(platformTop);
                    velocityY = 0;
                    onPlatform = true;
                    land(); // Reset jumping state
                    onAnySegment = true;
                    break;
                }
            }
        }
        
        // If not on any segment, player might fall through a hole
        if (!onAnySegment && !isJumping && getY() <= bottomPlatform.getY() + bottomPlatform.getBounds().height) {
            // Player is falling through a hole
            isJumping = true;
        }
    }
}