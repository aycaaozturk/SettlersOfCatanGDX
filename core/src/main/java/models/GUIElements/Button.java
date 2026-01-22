package models.GUIElements;

import com.badlogic.gdx.graphics.Texture;
import models.GUIElements.HitBoxes.RectangleHitBox;
import models.Vertex;

public class Button {

    private RectangleHitBox hitBox;
    private Texture texture;
    private Texture hoverTexture;
    private Vertex textureOrigin;
    private Vertex hitBoxPivot;
    private float width;
    private float height;
    private boolean hover = false;


    public Button(Vertex hitBoxPivot, float width, float height, Texture texture, Texture hoverTexture) {
        this.hitBoxPivot = hitBoxPivot;
        this.hoverTexture = hoverTexture;
        this.texture = texture;
        this.width = width;
        this.height = height;
        setTextureOrigin();
        createHitBox();
    }




    public void createHitBox() {
        hitBox = new RectangleHitBox(
            hitBoxPivot,
            width,
            height,
            0,
            this
        );
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    private void setTextureOrigin() {
        this.textureOrigin = new Vertex(hitBoxPivot.getX() - 0.5f * width, hitBoxPivot.getY() - 0.5f * height);
    }


    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Texture getTexture() {
        if(hover)
            return texture;
        return hoverTexture;
    }

    public boolean hit(float x, float y) {
        return hitBox.hit(x, y);
    }


    public Vertex getHitBoxPivot() {
        return hitBoxPivot;
    }

    public Vertex getTextureOrigin() {
        return textureOrigin;
    }
}
