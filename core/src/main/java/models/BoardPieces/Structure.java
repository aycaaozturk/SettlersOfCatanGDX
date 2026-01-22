package models.BoardPieces;

import com.badlogic.gdx.graphics.Texture;
import models.Edge;
import models.Vertex;

import java.util.Dictionary;
import java.util.Hashtable;

public interface Structure {

    public Vertex getSourceVertex();
    public Edge getSourceEdge();
    public Texture getTexture();

    public int getWoodCost();
    public int getGrainCost();
    public int getOreCost();
    public int getClayCost();
    public int getWoolCost();
}
