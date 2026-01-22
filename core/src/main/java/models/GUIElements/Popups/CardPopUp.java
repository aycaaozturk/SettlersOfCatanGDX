package models.GUIElements.Popups;

import CatanLib.GlobalSettings;
import Enums.ResourceType;
import com.badlogic.gdx.graphics.Texture;
import models.Vertex;

public class CardPopUp implements PopUp {
    private Vertex origin;
    private String title;
    private String description;
    private Texture texture;

    public static int CardPopUpWidth = 200;
    public static int CardPopUpHeight = 200;

    public CardPopUp(){
        loadTexture();
    }

    public CardPopUp(Vertex origin, String title, String description) {
        this.setOrigin(origin);
        this.title = title;
        this.description = description;
        loadTexture();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    private void loadTexture(){
        texture = new Texture("GUIElements/CardPopUpBackground.png");
    }

    public Texture getTexture() {
        return texture;
    }

    public Vertex getOrigin() {
        return origin;
    }

    public void setOrigin(Vertex origin) {
        this.origin = new Vertex(
            origin.getX() - 0.4f * GlobalSettings.CARD_WIDTH,
            origin.getY() + 0.4f * GlobalSettings.CARD_HEIGHT
        );
    }

    public static CardPopUp getResourcePopUp(Vertex origin, ResourceType type, int amount) {
        CardPopUp cardPopUp = new CardPopUp();
        cardPopUp.setOrigin(origin);

        switch (type){
            case WOOL -> {
                cardPopUp.title = "Wool";
                if(amount == 1)
                    cardPopUp.description = "You have " + amount + " Wool Card";
                else
                    cardPopUp.description = "You have " + amount + " Wool Cards";
            }
            case GRAIN -> {
                cardPopUp.title = "Grain";
                if(amount == 1)
                    cardPopUp.description = "You have " + amount + " Grain Card";
                else
                    cardPopUp.description = "You have " + amount + " Grain Cards";
            }
            case CLAY -> {
                cardPopUp.title = "Clay";
                if(amount == 1)
                    cardPopUp.description = "You have " + amount + " Clay Card";
                else
                    cardPopUp.description = "You have " + amount + " Clay Cards";
            }
            case ORE -> {
                cardPopUp.title = "Ore";
                if(amount == 1)
                    cardPopUp.description = "You have " + amount + " Ore Card";
                else
                    cardPopUp.description = "You have " + amount + " Ore Cards";
            }
            case WOOD -> {
                cardPopUp.title = "Wood";
                if(amount == 1)
                    cardPopUp.description = "You have " + amount + " Wood Card";
                else
                    cardPopUp.description = "You have " + amount + " Wood Cards";
            }
        }
        return cardPopUp;
    }
}
