package models.BoardPieces;

import com.badlogic.gdx.graphics.Texture;
import models.Edge;
import models.Player;
import models.Vertex;

import java.util.Dictionary;
import java.util.Hashtable;

public class Road implements Structure {

    public static int WoodCost = 1;
    public static int ClayCost = 1;
    public static int GrainCost = 0;
    public static int WoolCost = 0;
    public static int OreCost = 0;

    private Texture texture;

    private Edge edge;
    private Player assignedPlayer = null;



    public Road(Edge edge, Player assignedPlayer) {
        this.edge = edge;
        this.assignedPlayer = assignedPlayer;
        loadTexture();
    }

    private void loadTexture() {
        switch (assignedPlayer.getColor()){
            case BLUE -> {
                texture = new Texture("Structures/BlueRoad.png");
            }
            case RED -> {
                texture = new Texture("Structures/RedRoad.png");
            }
            case WHITE -> {
                texture = new Texture("Structures/WhiteRoad.png");
            }
            case GREEN -> {
                texture = new Texture("Structures/GreenRoad.png");
            }
        }
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public Edge getEdge() {
        return edge;
    }

    public Player getAssignedPlayer() {
        return assignedPlayer;
    }

    @Override
    public Vertex getSourceVertex() {
        return null;
    }

    @Override
    public Edge getSourceEdge() {
        return edge;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public int getWoodCost() {
        return WoodCost;
    }

    @Override
    public int getGrainCost() {
        return GrainCost;
    }

    @Override
    public int getOreCost() {
        return OreCost;
    }

    @Override
    public int getClayCost() {
        return ClayCost;
    }

    @Override
    public int getWoolCost() {
        return WoolCost;
    }

}
