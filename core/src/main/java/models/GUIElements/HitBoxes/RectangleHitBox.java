package models.GUIElements.HitBoxes;

import models.Edge;
import models.Vertex;

public class RectangleHitBox implements HitBox {

    private float width;
    private float height;
    private Vertex pivot;
    private float rotation;
    public boolean active;
    private int layer;
    private Object source;

    public RectangleHitBox(Vertex pivot, float width, float height, float rotation, Object source) {
        this.pivot = pivot;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.source = source;
        active = false;
        layer = 0;
    }

    public RectangleHitBox(Vertex pivot, float width, float height, float rotation, Object source, int layer) {
        this.pivot = pivot;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        active = false;
        this.layer = layer;
        this.source = source;
    }


    @Override
    public boolean hit(float x, float y) {
        float angleRad = (float) Math.toRadians(-rotation);
        float translatedX = x - pivot.getX();
        float translatedY = y - pivot.getY();

        float unrotatedX = translatedX * (float)Math.cos(angleRad) - translatedY * (float)Math.sin(angleRad);
        float unrotatedY = translatedX * (float)Math.sin(angleRad) + translatedY * (float)Math.cos(angleRad);

        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        return Math.abs(unrotatedX) <= halfWidth && Math.abs(unrotatedY) <= halfHeight;
    }

    public Object getSource() {
        return source;
    }

    @Override
    public float getX() {
        return pivot.getX();
    }

    @Override
    public float getY() {
        return pivot.getY();
    }

    @Override
    public int getLayer() {
        return layer;
    }

    public void setPivot(Vertex pivot) {
        this.pivot = pivot;
    }

    public void movePivot(float x, float y) {
        pivot.setX(pivot.getX() + x);
        pivot.setY(pivot.getY() + y);
    }

    @Override
    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public Vertex getVertex() {
        return null;
    }

    @Override
    public Edge getEdge() {
        return (Edge) source;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
}
