package ProjectOOP.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SpeedBar extends ImmovableEntity{

	private Color color;
	private float width, height, maxHeight;
	
	public SpeedBar() {
		
	}
	
	public SpeedBar(float x, float y, Color color, float width, float height, float maxHeight) {
		setX(x);
		setY(y);
        this.color = color;
        this.width = width;
        this.height = height;
        this.maxHeight = maxHeight;
    }
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public float getMaxHeight() {
		return maxHeight;
	}
	
	public void setMaxHeight(float maxHeight) {
		this.maxHeight = maxHeight;
	}
    
	public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public void draw(ShapeRenderer shape) {
    	// To draw the shape and the outline, need to end() then begin() the shape again
    	// Will see if got any other ways besides ending and beginning the shape again as it's not good for performance to do this in render()
    	shape.setColor(getColor());
    	shape.rect(this.x_axis, this.y_axis, this.width, this.height);
    	shape.end();
    	shape.begin(ShapeRenderer.ShapeType.Line);
    	shape.setColor(Color.WHITE); 
    	
    	int outlineThickness = 3; // Adjust this value for thicker or thinner outlines
        for (int i = 0; i < outlineThickness; i++) {
        	shape.rect(this.x_axis - i, this.y_axis - i, this.width + i * 2, this.maxHeight + i * 2);
        }
    }
    
    public void setBar(Color[] barColors, float stamina) {

    	// Floor to ensure 0 to 10 is first color, 11 to 20 is second color
    	int index = (int) Math.floor((stamina - 1) / 10);

    	// Ensure the index is within bounds
    	if (index < 0) {
    	    index = 0; // Handle negative stamina
    	} else if (index >= barColors.length) {
    	    index = barColors.length - 1; // Handle stamina greater than 60
    	}
    	setColor(barColors[index]);
    	// If stamina is more than 0, setHeight to segment * index + 1, if not, setHeight to 0
    	setHeight((maxHeight/barColors.length) * (stamina > 0 ? index + 1 : 0));
    }

    public void setEntitySpeedsByStamina(float stamina, Entity[] entityList) {
    	float[] speeds = {7, 6, 5, 4, 3, 2, 1};
        int index = (int) Math.floor(stamina / 10);

    	// Ensure the index is within bounds
    	if (index < 0) {
    	    index = 0; // Handle negative stamina
    	} else if (index >= speeds.length) {
    	    index = speeds.length - 1; // Handle stamina greater than 60
    	}
    	
        for (Entity e: entityList) {
        	e.setSpeed(speeds[index]);
        }
    }
    
}
