package ProjectOOP.Entity;

public class Health extends ImmovableEntity {
    private int healthValue;

    public Health(float x, float y) {
        super("heart.png", x, y, 0); // Image of heart
        this.healthValue = 1; // Each heart represents 1 health point
    }

    public int getHealthValue() {
        return healthValue;
    }
}