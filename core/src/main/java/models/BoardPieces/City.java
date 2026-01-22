package models.BoardPieces;

import com.badlogic.gdx.graphics.Texture;
import models.Edge;
import models.Player;
import models.Vertex;

import java.util.Dictionary;
import java.util.Hashtable;

public class City implements Structure {
    public static int WoolCost = 0;
    public static int GrainCost = 2;
    public static int OreCost = 3;
    public static int ClayCost = 0;
    public static int WoodCost = 0;

    private Vertex vertex;
    private Player assignedPlayer = null;
    private Texture texture;

    public City(Vertex vertex, Player player) {
        this.vertex = vertex;
        this.assignedPlayer = player;
        loadTexture();
    }

    private void loadTexture(){
        switch (assignedPlayer.getColor()){
            case BLUE -> {
                texture = new Texture("Structures/BlueCity.png");
            }
            case RED -> {
                texture = new Texture("Structures/RedCity.png");
            }
            case WHITE -> {
                texture = new Texture("Structures/WhiteCity.png");
            }
            case GREEN -> {
                texture = new Texture("Structures/GreenCity.png");
            }
        }
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
