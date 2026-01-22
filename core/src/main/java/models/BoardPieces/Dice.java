package models.BoardPieces;

import models.Vertex;

import java.util.Random;

public class Dice {
    int maxValue;
    private final Random rand = new Random();

    public Dice(int max) {
        maxValue = max;

    }

    public int rollDice() {
        return rand.nextInt(1, maxValue);
    }

    public int getDiceValue() {
        return maxValue;
    }

}
