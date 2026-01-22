package models.cards;


import Enums.AchievementType;
import com.badlogic.gdx.graphics.Texture;

public class AchievementCard implements Card{
    private AchievementType type;

    public AchievementCard(AchievementType type) {
        this.type = type;
    }

    public AchievementType getType() {
        return type;
    }

    @Override
    public Texture getTexture() {
        return null;
    }

}
