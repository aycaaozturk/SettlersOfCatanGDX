package models.BoardPieces;

import java.util.ArrayList;
import java.util.List;

public class NumberChip {
    private int number = 0;

    public NumberChip(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public static List<NumberChip> getNumberChips() {
        List<NumberChip> numberChips = new ArrayList<NumberChip>();
        numberChips.add(new NumberChip(2));
        numberChips.add(new NumberChip(3));
        numberChips.add(new NumberChip(3));
        numberChips.add(new NumberChip(4));
        numberChips.add(new NumberChip(4));
        numberChips.add(new NumberChip(5));
        numberChips.add(new NumberChip(5));
        numberChips.add(new NumberChip(6));
        numberChips.add(new NumberChip(6));
        numberChips.add(new NumberChip(8));
        numberChips.add(new NumberChip(8));
        numberChips.add(new NumberChip(9));
        numberChips.add(new NumberChip(9));
        numberChips.add(new NumberChip(10));
        numberChips.add(new NumberChip(10));
        numberChips.add(new NumberChip(11));
        numberChips.add(new NumberChip(11));
        numberChips.add(new NumberChip(12));

        return numberChips;
    }

}
