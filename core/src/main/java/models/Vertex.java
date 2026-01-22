package models;

import java.util.List;

public class Vertex {

    float x;
    float y;


    public Vertex(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vertex) {
            Vertex v = (Vertex)obj;
            boolean xInRange = false;
            boolean yInRange = false;

            if(this.x >= v.x -5 && this.x <= v.x + 5) {
                xInRange = true;
            }
            if(this.y >= v.y -5 && this.y <= v.y + 5) {
                yInRange = true;
            }


            return xInRange && yInRange;
        }
        return false;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
