package models.GUIElements.Popups;

import CatanLib.GlobalSettings;
import com.badlogic.gdx.graphics.Texture;
import models.BoardPieces.City;
import models.BoardPieces.Road;
import models.BoardPieces.Settlement;
import models.BoardPieces.Structure;
import models.GUIElements.HitBoxes.RectangleHitBox;
import models.Vertex;

public class BuildPopup implements PopUp {


    private Vertex screenOrigin;

    private float width;
    private float height;

    private String title;
    private Structure sourceStructure;

    private Texture backgroundTexture;

    private Texture structureIcon;

    private Texture confirmButton;
    private Texture cancelButton;

    private float buttonWidth;
    private float buttonHeight;

    private RectangleHitBox buildHitBox;
    private RectangleHitBox cancelHitBox;

    private int originMargin = 20;
    private int margin = 20;


    public BuildPopup(Structure sourceStructure) {
        this.sourceStructure = sourceStructure;

        switch (this.sourceStructure) {
            case Road road -> this.screenOrigin = road.getEdge().getCenter();
            case City city -> this.screenOrigin = city.getVertex();
            case Settlement settlement -> this.screenOrigin = settlement.getVertex();
            case null, default -> this.screenOrigin = new Vertex(0, 0);
        }

        width = (float) Math.floor(GlobalSettings.SCREEN_WIDTH / 3f);
        height = (float) Math.floor(GlobalSettings.SCREEN_HEIGHT / 3f);


        if(sourceStructure.getClass() == City.class)
            title = "City";
        else if(sourceStructure.getClass() == Settlement.class)
            title = "Settlement";
        else if(sourceStructure.getClass() == Road.class)
            title = "Road";
        else
            title = "UNKNOWN";

        loadTextures();
        generateHitBoxes();
    }

    public String getTitle() {
        return title;
    }

    public Structure getSourceStructure() {
        return sourceStructure;
    }

    private void generateHitBoxes() {
        float buildX = screenOrigin.getX() + width + originMargin - margin - 0.5f * buttonWidth;
        float buildY = screenOrigin.getY() + 0.5f * height - margin - 0.5f * buttonHeight;
        buildHitBox = new RectangleHitBox(new Vertex(buildX, buildY), buttonWidth, buttonHeight, 0f, this);

        float cancelX = screenOrigin.getX() + originMargin + margin + 0.5f * buttonWidth;
        float cancelY = screenOrigin.getY() + 0.5f * height - margin - 0.5f * buttonHeight;
        cancelHitBox = new RectangleHitBox(new Vertex(cancelX, cancelY), buttonWidth, buttonHeight, 0f, this);
    }

    public Vertex getBackgroundOrigin() {
        return new Vertex(screenOrigin.getX() + originMargin, screenOrigin.getY() + 0.5f * height);
    }

    public Vertex getConfirmButtonOrigin() {
        float x = screenOrigin.getX() + width + originMargin - margin - buttonWidth;
        float y = screenOrigin.getY() + 0.5f * height - margin;
        return new Vertex(x, y);
    }

    public Vertex getCancelButtonOrigin() {
        float x = screenOrigin.getX() + originMargin + margin;
        float y = screenOrigin.getY() + 0.5f * height - margin;
        return new Vertex(x, y);
    }

    public String BuildOrCancelHit(float x, float y) {
        if(buildHitBox.hit(x,y))
            return "confirm";
        if(cancelHitBox.hit(x,y))
            return "cancel";
        return "missed";
    }

    public void changeHitBoxStatus(boolean status, String type){
        if(type == "confirm")
            buildHitBox.active = status;
        else if (type == "cancel")
            cancelHitBox.active = status;
    }

    public boolean getHitBoxStatus(String type) {
        if(type == "confirm")
            return buildHitBox.active;
        else if (type == "cancel")
            return cancelHitBox.active;
        return false;
    }

    private void loadTextures(){
        confirmButton = new Texture("GUIElements/ConfirmButton.png");
        cancelButton = new Texture("GUIElements/CancelButton.png");
        backgroundTexture = new Texture("GUIElements/StructureBackground.png");

        buttonWidth = (float) Math.floor(cancelButton.getWidth() / 3f);
        buttonHeight = (float) Math.floor(cancelButton.getHeight() / 3f);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getButtonWidth() {
        return buttonWidth;
    }

    public float getButtonHeight() {
        return buttonHeight;
    }

    public Vertex getScreenOrigin() {
        return screenOrigin;
    }

    public Texture getConfirmButtonTexture(){
        return confirmButton;
    }
    public Texture getCancelButtonTexture(){
        return cancelButton;
    }
    public Texture getStructureIcon(){
        return structureIcon;
    }
    public Texture getBackgroundTexture(){
        return backgroundTexture;
    }



    public int getWoodCost(){
        return sourceStructure.getWoodCost();
    }

    public int getGrainCost(){
        return sourceStructure.getGrainCost();
    }

    public int getOreCost(){
        return sourceStructure.getOreCost();
    }

    public int getWoolCost(){
        return sourceStructure.getWoolCost();
    }

    public int getClayCost(){
        return sourceStructure.getClayCost();
    }
}
