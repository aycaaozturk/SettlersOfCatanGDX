package models.cards;

import Enums.DevelopmentType;
import com.badlogic.gdx.graphics.Texture;

public class DevelopmentCard implements Card {
    private DevelopmentType type;

    public DevelopmentCard(DevelopmentType type) {
        this.type = type;
    }

    public DevelopmentType getType() {
        return type;
    }

    @Override
    public Texture getTexture() {
        return null;
    }
}
