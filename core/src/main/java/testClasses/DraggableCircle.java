package testClasses;

public class DraggableCircle {
    public float x;
    public float y;
    float radius;

    public DraggableCircle(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    // used for hitbox checks. Formula: (px - x)² + (py - y)² <= r² (Euclidean distance) px, py are cursor positions, x, y are the
    // midpoints of the circle
    public boolean contains(float px, float py) {
        float dx = px - x;
        float dy = py - y;
        return dx * dx + dy * dy <= radius * radius;
    }


    public float getRadius() {
        return radius;
    }

}
