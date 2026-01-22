package models.GUIElements.HitBoxes;

import models.Edge;
import models.Vertex;

public interface HitBox {
    public boolean hit(float x, float y);
    public float getX();
    public float getY();
    public int getLayer();
    public void setLayer(int layer);
    public Vertex getVertex();
    public Edge getEdge();
}
