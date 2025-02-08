package io.github.ProjectOOP.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class ImmovableEntity extends Entity{
	ImmovableEntity (){
		
	}
	
	ImmovableEntity (String str, float x, float y, float s){
		setX(x);
		setY(y);
		setSpeed(s);
		texture = new Texture(Gdx.files.internal(str));
	}
	
	ImmovableEntity(String str, float x, float y, float s, boolean m) {
		super(str, x, y, s, m);
	}
    
    public void moveLeft() {
        velocityX = 0;
        System.out.println("Moving Left");
    }

    public void moveRight() {
        velocityX = 0;
        System.out.println("Moving Right");
    }

    public void stopMoving() {
        velocityX = 0;
        System.out.println("Stopped Moving");
    }
	
	@Override
	public void updatePosition() {
		System.out.println("X position of immovable entity = " + getX() + "\n");
		System.out.println("Y position of immovable entity = " + getY() + "\n");
	}
}
