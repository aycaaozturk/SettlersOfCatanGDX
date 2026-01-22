package models.BoardPieces;

import com.badlogic.gdx.graphics.Texture;
import models.Edge;
import models.Player;
import models.Vertex;

import java.util.Dictionary;
import java.util.Hashtable;

public class Settlement implements Structure {

    public static int WoolCost = 1;
    public static int WoodCost = 1;
    public static int GrainCost = 1;
    public static int ClayCost = 1;
    public static int OreCost = 0;

    private Texture texture;
    private Vertex vertex;
    private Player assignedPlayer = null;

    public Settlement(Vertex vertex, Player assignedPlayer) {
        this.vertex = vertex;
        this.assignedPlayer = assignedPlayer;
        loadTexture();
    }

    public Player getAssignedPlayer() {
        return assignedPlayer;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    @Override
    public Vertex getSourceVertex() {
        return vertex;
    }

    @Override
    public Edge getSourceEdge() {
        return null;
    }

    private void loadTexture() {
        switch (assignedPlayer.getColor()){
            case BLUE -> {
                texture = new Texture("Structures/BlueSettlement.png");
            }
            case RED -> {
                texture = new Texture("Structures/RedSettlement.png");
            }
            case WHITE -> {
                texture = new Texture("Structures/WhiteSettlement.png");
            }
            case GREEN -> {
                texture = new Texture("Structures/GreenSettlement.png");
            }
        }
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

