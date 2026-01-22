package models.cards;

import Enums.ResourceType;
import com.badlogic.gdx.graphics.Texture;

public class ResourceCard implements Card {
    private final ResourceType type;
    private Texture texture;

    public static Texture WoodTexture = new Texture("Cards/WoodCard.png");
    public static Texture GrainTexture = new Texture("Cards/GrainCard.png");
    public static Texture OreTexture = new Texture("Cards/OreCard.png");
    public static Texture WoolTexture = new Texture("Cards/WoolCard.png");
    public static Texture ClayTexture = new Texture("Cards/ClayCard.png");


    public ResourceCard(ResourceType type) {
        this.type = type;
        loadTexture();
    }

    private void loadTexture() {
        switch (type) {
            case WOOL -> {
                texture = new Texture("Cards/WoolCard.png");
            }
            case GRAIN -> {
                texture = new Texture("Cards/GrainCard.png");
            }
            case CLAY -> {
                texture = new Texture("Cards/ClayCard.png");
            }
            case ORE -> {
                texture = new Texture("Cards/OreCard.png");
            }
            case WOOD -> {
                texture = new Texture("Cards/WoodCard.png");
            }
        }
    }

    public String getName() {
        switch (type) {
            case WOOL -> {
                return "Wool";
            }
            case GRAIN -> {
                return "Grain";
            }
            case CLAY -> {
                return "Clay";
            }
            case ORE -> {
                return "Ore";
            }
            case WOOD -> {
                return "Wood";
            }
        }
        return "";
    }

    public Texture getTexture() {
        return texture;
    }

    public ResourceType getType() {
        return type;
    }

}
