package io.github.ProjectOOP.lwjgl3;

public class EntityConfig {
    private String texture;
    private float x, y, speed;
    private boolean movable;

    public EntityConfig(String texture, float x, float y, float speed, boolean movable) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.movable = movable;
    }

    public String getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean getMovable() {
        return movable;
    }
}


