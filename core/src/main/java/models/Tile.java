package models;

import Enums.TileType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import models.BoardPieces.NumberChip;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private int index;
    private TileType tileType;
    private Texture image;
    private NumberChip numberChip;
    private List<Vertex> vertices;
    private List<Edge> edges;
    private float x;
    private float y;


    public Tile(int index, TileType tileType, NumberChip numberChip) {
        this.index = index;
        this.tileType = tileType;
        this.numberChip = numberChip;
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        setImage();
    }

    //todo set image to correct asset when assets arrive
    private void setImage() {
        switch (tileType) {
            case HILLS -> {
                image = new Texture("Tiles/HillsTile.png");
            }
            case FOREST -> {
                image = new Texture("Tiles/ForestTile.png");
            }
            case MEADOW -> {
                image = new Texture("Tiles/MeadowTile.png");
            }
            case FARMLAND -> {
                image = new Texture("Tiles/FarmlandTile.png");
            }
            case MOUNTAIN -> {
                image = new Texture("Tiles/MountainTile.png");
            }
            case DESERT -> {
                image = new Texture("Tiles/DesertTile.png");
            }
        }
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

    public List<Vertex> getVertices() {
        return vertices;
    }
    public List<Edge> getEdges() {
        return edges;
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public int getIndex() {
        return index;
    }

    public TileType getTileType() {
        return tileType;
    }

    //might not be necessary
    public Texture getImage() {
        return image;
    }

    public NumberChip getNumberChip() {
        return numberChip;
    }

    public static List<TileType> getTileTypes() {
        List<TileType> tileTypes = new ArrayList<TileType>();
        for (int i = 0; i < 3; i++) {
            tileTypes.add(TileType.HILLS);
            tileTypes.add(TileType.MOUNTAIN);
        }

        for (int i = 0; i < 4; i++) {
            tileTypes.add(TileType.FOREST);
            tileTypes.add(TileType.MEADOW);
            tileTypes.add(TileType.FARMLAND);
        }

        tileTypes.add(TileType.DESERT);
        return tileTypes;
    }
}
