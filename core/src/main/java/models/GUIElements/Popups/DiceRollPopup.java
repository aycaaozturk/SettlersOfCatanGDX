package models.GUIElements.Popups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import models.BoardPieces.Dice;
import models.GUIElements.Button;
import models.Vertex;

public class DiceRollPopup implements PopUp {

    private Vertex origin;
    private float width;
    private float height;
    private Dice dice;
    private Texture background;

    private Texture diceNumber1;
    private Texture diceNumber2;
    private Texture diceNumber3;
    private Texture diceNumber4;
    private Texture diceNumber5;
    private Texture diceNumber6;

    private Texture buttonTexture;
    private Texture buttHoverTexture;

    private Texture render1;
    private Texture render2;

    private int number1;
    private int number2;

    Button okButton;



    public DiceRollPopup(Vertex origin, float size) {
        this.origin = origin;
        this.width = 400 * size;
        this.height = 300 * size;
        dice = new Dice(6);
        LoadTextures();
        okButton = new Button(
            new Vertex(origin.getX(), origin.getY() - 100),
            100,
            50,
            buttonTexture,
            buttHoverTexture
        );

        rollDice();
    }

    public boolean hit(float x, float y){
        if(okButton.hit(x,y)){
            okButton.setHover(true);
            return true;
        }

        okButton.setHover(false);
        return false;
    }

    private void rollDice() {
        number1 = dice.rollDice();
        number2 = dice.rollDice();

        switch (number1){
            case 1:
                render1 = diceNumber1;
                break;
            case 2:
                render1 = diceNumber2;
                break;
            case 3:
                render1 = diceNumber3;
                break;
            case 4:
                render1 = diceNumber4;
                break;
            case 5:
                render1 = diceNumber5;
                break;
            case 6:
                render1 = diceNumber6;
                break;
        }

        switch (number2){
            case 1:
                render2 = diceNumber1;
                break;
            case 2:
                render2 = diceNumber2;
                break;
            case 3:
                render2 = diceNumber3;
                break;
            case 4:
                render2 = diceNumber4;
                break;
            case 5:
                render2 = diceNumber5;
                break;
            case 6:
                render2 = diceNumber6;
                break;
        }
    }

    private void LoadTextures(){
        diceNumber1 = new Texture("Dice/dice1.png");
        diceNumber2 = new Texture("Dice/dice2.png");
        diceNumber3 = new Texture("Dice/dice3.png");
        diceNumber4 = new Texture("Dice/dice4.png");
        diceNumber5 = new Texture("Dice/dice5.png");
        diceNumber6 = new Texture("Dice/dice6.png");

        buttonTexture = new Texture("GUIElements/okButton.png");
        buttHoverTexture = new Texture("GUIElements/okHover.png");

        background = new Texture("Dice/diceBackground.png");
    }

    public void Draw(SpriteBatch batch) {
        batch.draw(
            background,
            origin.getX() - 0.5f * width,
            origin.getY() - 0.5f * height,
            width,
            height
        );

        batch.draw(
            render1,
            origin.getX() - 150,
            origin.getY(),
            100,
            100
        );

        batch.draw(
            render2,
            origin.getX() + 50,
            origin.getY(),
            100,
            100
        );

        batch.draw(
            okButton.getTexture(),
            okButton.getTextureOrigin().getX(),
            okButton.getTextureOrigin().getY(),
            okButton.getWidth(),
            okButton.getHeight()
        );


    }
}
