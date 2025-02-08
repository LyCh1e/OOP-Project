package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Output {
    private BitmapFont font; // Added font for text rendering
	private String strOutput;
	private float number, x_axis, y_axis;
	
	Output(){
		
	}
	
	Output (String str, Color c, float x, float y, float scale){
		setString(str);
		setX(x);
		setY(y);
		font = new BitmapFont(); // Default LibGDX font
        font.setColor(c); // Set font color to white
        font.getData().setScale(scale); // Scale the font size up
	}
	
	public float getNumber() {
		return number;
	}
	
	public String getString() {
		return strOutput;
	}
	
	public float getX() {
		return x_axis;
	}
	
	public float getY() {
		return y_axis;
	}
	
	void setNumber(float n) {
		number = n;
	}
	
	void setString(String s) {
		strOutput = s;
	}
	
	void setX(float x) {
		x_axis = x;
	}
	
	void setY(float y) {
		y_axis = y;
	}
	
	public void draw(SpriteBatch batch) {
		font.draw(batch, getString(), getX(), getY());
    }
}
