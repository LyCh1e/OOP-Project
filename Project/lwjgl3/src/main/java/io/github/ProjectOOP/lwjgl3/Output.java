package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Output {
    private BitmapFont font; // Added font for text rendering
	private String strOutput;
	private float number, x_axis, y_axis;
    private Music audio;
    private boolean isMuted = false;
    private float volume;
	
	Output(){
		
	}
	
	Output (String str, Color c, float x, float y, float scale){
		setString(str);
		setX(x);
		setY(y);
		font = new BitmapFont(); // Default LibGDX font
        font.setColor(c);
        font.getData().setScale(scale); // Scale the font size up
	}
	
    Output(String filePath, float v) {
    	setString(filePath);
    	setVolume(v);
    	audio = Gdx.audio.newMusic(Gdx.files.internal(getString()));
    	audio.setLooping(true);
    	audio.setVolume(getVolume()); 
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
	
	public float getVolume() {
		return volume;
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
	
	void setVolume(float v) {
		volume = v;		
	}
	
    public void setMusic(Music music) {
        this.audio = music;
        if(audio != null) {
            audio.setLooping(true);
            audio.setVolume(getVolume());
        }
    }

	public void playMusic() {
        if(audio != null) {
            audio.play();
        }
    }

    public void setAudioVolume(float vol) {
        setVolume(vol);
        if(audio != null && !isMuted) {
            audio.setVolume(getVolume());
        }
    }
	
	public void draw(SpriteBatch batch) {
	    if (font == null) {
	        return;
	    }
		font.draw(batch, getString(), getX(), getY());
    }
}
