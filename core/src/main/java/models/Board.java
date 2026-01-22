package models;

import CatanLib.GlobalSettings;
import Enums.TileType;
import com.badlogic.gdx.graphics.Texture;
import models.BoardPieces.Dice;
import models.BoardPieces.NumberChip;
import models.BoardPieces.Robber;
import models.BoardPieces.Structure;

import javax.accessibility.AccessibleIcon;
import java.util.*;

public class Board {
    private Dictionary<Integer, Tile> tiles = new Hashtable<>();
    private List<Structure> structures = new ArrayList<>();
    private Robber robber;
    private float tileWidth;
    private float tileHeight;
    private float viewPortHeight;
    private float viewPortWidth;

    public Board(){
        tileWidth = GlobalSettings.DEFAULT_TILE_WIDTH;
        tileHeight = GlobalSettings.DEFAULT_TILE_HEIGHT;
        viewPortHeight = GlobalSettings.DEFAULT_SCREEN_HEIGHT;
        viewPortWidth = GlobalSettings.DEFAULT_SCREEN_WIDTH;
    }

    private List<Vertex> vertices = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();


   public Dictionary<Integer, Tile> getTiles() {
       return tiles;
   }

   public void addStructure(Structure structure){
       structures.add(structure);
   }
   public void removeStructure (Structure structure){
       structures.remove(structure);
   }

   public List<Structure> getStructures() {
       return structures;
   }

   public List<Edge> getEdges() {
       return edges;
   }

   public List< Vertex> getVertices() {
       return vertices;
   }

   public void addVertex (Vertex vertex) {
       vertices.add(vertex);
   }

   public void addEdge (Edge edge) {
       edges.add(edge);
   }


    public void prepareBoard() {

       //TODO change to global settings
        //first row
        getTiles().get(1).setX(viewPortWidth/2 - tileWidth);
        getTiles().get(1).setY(viewPortHeight/2 - (1.5f * tileHeight));

        getTiles().get(2).setX(viewPortWidth/2);
        getTiles().get(2).setY(viewPortHeight/2 - (1.5f * tileHeight));

        getTiles().get(3).setX(viewPortWidth/2 + tileWidth);
        getTiles().get(3).setY(viewPortHeight/2 - (1.5f * tileHeight));


        //second row
        getTiles().get(4).setX(viewPortWidth/2 - (1.5f * tileWidth));
        getTiles().get(4).setY(viewPortHeight/2 - (0.75f * tileHeight));

        getTiles().get(5).setX(viewPortWidth/2 - (0.5f * tileWidth));
        getTiles().get(5).setY(viewPortHeight/2 - (0.75f * tileHeight));

        getTiles().get(6).setX(viewPortWidth/2 + (0.5f * tileWidth));
        getTiles().get(6).setY(viewPortHeight/2 - (0.75f * tileHeight));

        getTiles().get(7).setX(viewPortWidth/2 + (1.5f * tileWidth));
        getTiles().get(7).setY(viewPortHeight/2 - (0.75f * tileHeight));


        //third row
        getTiles().get(8).setX(viewPortWidth/2 - (2.0f * tileWidth));
        getTiles().get(8).setY(viewPortHeight/2);

        getTiles().get(9).setX(viewPortWidth/2 - tileWidth);
        getTiles().get(9).setY(viewPortHeight/2);

        getTiles().get(10).setX(viewPortWidth/2);
        getTiles().get(10).setY(viewPortHeight/2);

        getTiles().get(11).setX(viewPortWidth/2 + tileWidth);
        getTiles().get(11).setY(viewPortHeight/2);

        getTiles().get(12).setX(viewPortWidth/2 + (2.0f * tileWidth));
        getTiles().get(12).setY(viewPortHeight/2);


        //fourth row
        getTiles().get(13).setX(viewPortWidth/2 - (1.5f * tileWidth));
        getTiles().get(13).setY(viewPortHeight/2 + (0.75f * tileHeight));

        getTiles().get(14).setX(viewPortWidth/2 - (0.5f * tileWidth));
        getTiles().get(14).setY(viewPortHeight/2 + (0.75f * tileHeight));

        getTiles().get(15).setX(viewPortWidth/2 + (0.5f * tileWidth));
        getTiles().get(15).setY(viewPortHeight/2 + (0.75f * tileHeight));

        getTiles().get(16).setX(viewPortWidth/2 + (1.5f * tileWidth));
        getTiles().get(16).setY(viewPortHeight/2 + (0.75f * tileHeight));

        //fifth row
        getTiles().get(17).setX(viewPortWidth/2 - tileWidth);
        getTiles().get(17).setY(viewPortHeight/2 + (1.5f * tileHeight));

        getTiles().get(18).setX(viewPortWidth/2);
        getTiles().get(18).setY(viewPortHeight/2 + (1.5f * tileHeight));

        getTiles().get(19).setX(viewPortWidth/2 + tileWidth);
        getTiles().get(19).setY(viewPortHeight/2 + (1.5f * tileHeight));

        calculateVertexCoordinates();
        calculateEdges();
        addVerticesToTiles();
        addEdgesToTiles();

    }


    private void calculateVertexCoordinates(){
        for (int i = 1; i < 20; i++) {
            List<Vertex> temp = new ArrayList<>();
            Tile current = getTiles().get(i);
            float cX = current.getX();
            float cY = current.getY();

            Vertex v1 = new Vertex(cX, cY + (0.5f * tileHeight));
            Vertex v2 = new Vertex(cX + (0.5f * tileWidth), cY + (0.25f *tileHeight));
            Vertex v3 = new Vertex(cX + (0.5f * tileWidth), cY - (0.25f * tileHeight));
            Vertex v4 = new Vertex(cX, cY - (0.5f * tileHeight));
            Vertex v5 = new Vertex(cX - (0.5f * tileWidth), cY + (0.25f * tileHeight));
            Vertex v6 = new Vertex(cX - (0.5f * tileWidth), cY - (0.25f * tileHeight));

            temp.add(v1);
            temp.add(v2);
            temp.add(v3);
            temp.add(v4);
            temp.add(v5);
            temp.add(v6);

            for (Vertex tv : temp) {
                boolean add = true;
                for (Vertex v : getVertices()) {
                    if (v.equals(tv)) {
                        add = false;
                        break;
                    }
                }
                if(add)
                    addVertex(tv);
            }

        }
    }

    private void calculateEdges(){
        float r = tileHeight / 2;

        for (Vertex v : getVertices()) {
            for (Vertex v2 : getVertices()) {
                if(v.equals(v2))
                    continue;
                float distance = calculateDistance(v.getX(), v.getY(), v2.getX(), v2.getY());
                if(distance <= r + 5 && distance >= r - 5){
                    Edge edge = new Edge(v, v2);
                    boolean add = true;
                    for (Edge e : getEdges()) {
                        if (e.equals(edge)) {
                            add = false;
                            break;
                        }
                    }
                    if (add)
                        addEdge(edge);
                }
            }
        }
    }

    private float calculateDistance(float x1, float y1, float x2, float y2){
        return (float)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void addVerticesToTiles(){
        float radius = tileHeight / 2;

        for (int i = 1; i < 20; i++) {
            Tile current = getTiles().get(i);
            for (Vertex v : getVertices()) {
                float distance = calculateDistance(current.getX(), current.getY(), v.getX(), v.getY());
                if(distance <= radius + 5 && distance >= radius - 5){
                    boolean add = true;
                    for (Vertex v2 : current.getVertices()) {
                        if (v.equals(v2)) {
                            add = false;
                            break;
                        }
                    }
                    if(add)
                        current.addVertex(v);
                }
            }
        }

    }

    private void addEdgesToTiles(){
        float radius = tileHeight / 2;
        for (int i = 1; i < 20; i++) {
            Tile current = getTiles().get(i);
            for (Edge e : getEdges()) {
                float distance = calculateDistance(current.getX(), current.getY(), e.getCenter().getX(), e.getCenter().getY());
                if(distance <= radius + 10) {
                    boolean add = true;
                    for (Edge e2 : current.getEdges()) {
                        if (e.equals(e2)) {
                            add = false;
                            break;
                        }
                    }
                    if (add)
                        current.addEdge(e);
                }
            }
        }
    }



    public static Board CreateRandomBoard(){
        Board board = new Board();

        List<TileType> types = Tile.getTileTypes();
        Collections.shuffle(types);
        List<NumberChip> chips = NumberChip.getNumberChips();
        Collections.shuffle(chips);

        for (int i = 1; i < 20; i++) {
            if(types.getFirst() != TileType.DESERT){
                board.tiles.put(i, new Tile(i, types.getFirst(), chips.getFirst()));
                types.removeFirst();
                chips.removeFirst();
            }
            else
            {
                board.tiles.put(i, new Tile(i, types.getFirst(), new NumberChip(-1)));
                types.removeFirst();
                board.robber = new Robber();
                board.robber.setCurrentTile(board.tiles.get(i));
            }
        }

        return board;
    }



}
