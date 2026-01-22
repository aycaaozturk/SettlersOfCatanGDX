package models.GUIElements;

import com.badlogic.gdx.graphics.Texture;
import models.GUIElements.HitBoxes.RectangleHitBox;
import models.Vertex;

public class MenuArrow {
    private float width;
    private float height;
    private Texture texture;
    private Vertex origin;
    //true right, false left
    private boolean orientation;
    private RectangleHitBox hitBox;

    public MenuArrow(Vertex origin, String orientation){
        this.origin = origin;

        if(orientation.equals("left"))
            this.orientation = false;
        else this.orientation = orientation.equals("right");

        loadTexture(false);

        width = texture.getWidth();
        height = texture.getHeight();
        createHitBox();
    }

    public void setHover(boolean hover){
        loadTexture(hover);
    }

    private void loadTexture(boolean hover){
        if(orientation && !hover)
            texture = new Texture("GUIElements/RightArrow.png");
        else if (!orientation && !hover)
            texture = new Texture("GUIElements/LeftArrow.png");
        else if (orientation && hover)
            texture = new Texture("GUIElements/RightArrowHover.png");
        else if (!orientation && hover)
            texture = new Texture("GUIElements/LeftArrowHover.png");
    }

    public Texture getTexture(){
        return texture;
    }

    private void createHitBox(){
        hitBox = new RectangleHitBox(
            new Vertex(
                origin.getX() + 0.5f * width,
                origin.getY() + 0.5f * height
            ),
            width,
            height,
            0,
            3
        );
    }

    public boolean hit(float x, float y){
        return hitBox.hit(x, y);
    }

    public Vertex getOrigin(){
        return origin;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }
}
