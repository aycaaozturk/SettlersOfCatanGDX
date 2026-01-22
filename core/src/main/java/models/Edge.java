package models;

public class Edge {
    private Vertex start;
    private Vertex end;

    public Edge(Vertex start, Vertex end) {
        this.start = start;
        this.end = end;
    }
    public Vertex getStart() {
        return start;
    }
    public Vertex getEnd() {
        return end;
    }

    public Vertex getCenter(){
        float x = (start.getX() + end.getX()) / 2;
        float y = (start.getY() + end.getY()) / 2;
        return new Vertex(x, y);
    }

    public float getAngle() {
        float angle = (float) Math.toDegrees(Math.atan2(start.getY() - end.getY(), start.getX() - end.getX()));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != Edge.class)
            return false;
        Edge edge = (Edge) obj;
        if(edge.getStart() == this.start && edge.getEnd() == this.end)
            return true;
        if(edge.getStart() == this.end && edge.getEnd() == this.start)
            return true;
        return false;
    }
}
