package models.GUIElements.HitBoxes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import models.Edge;
import models.Vertex;

public class CircleHitBox implements HitBox {

    private float radius;
    private Vertex vertex;
    public boolean active;
    private int layer;
    private Vertex source;

    public CircleHitBox(float radius, Vertex vertex) {
        this.radius = radius;
        this.vertex = vertex;
        this.active = false;
        this.layer = 0;
    }

    public CircleHitBox(float radius, Vertex vertex, int layer) {
        this.radius = radius;
        this.vertex = vertex;
        this.active = false;
        this.layer = layer;
    }

    public float getRadius() {
        return radius;
    }

    public Vertex getVertex() {
        return vertex;
    }

    @Override
    public Edge getEdge() {
        return null;
    }

    @Override
    public boolean hit(float x, float y) {
        float dx = x - vertex.getX();
        float dy = y - vertex.getY();
        return dx * dx + dy * dy <= radius * radius;
    }

    @Override
    public float getX() {
        return vertex.getX();
    }

    @Override
    public float getY() {
        return vertex.getY();
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }

    private void render(ShapeRenderer shapeRenderer) {

    }
}
