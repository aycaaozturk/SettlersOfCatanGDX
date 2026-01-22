package models.BoardPieces;

import models.Player;
import models.Tile;
import models.cards.Card;

import java.util.Collections;
import java.util.List;


public class Robber {
    private Tile currentTile;

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    // Steal a single random card from the player after a robber has been placed on one of his tiles
    public void stealResource(Player robber, Player robbed) {
        List<Card> playerHand = robbed.getCards();
        Collections.shuffle(playerHand);
        Card stolenCard = playerHand.getFirst();
        robbed.removeCard(stolenCard);
        robber.addCard(stolenCard);
    }

    // Steal half of the players resources in hand (rounded down), if they have more than 7 cards in their hand
    public void stealIfBiggerThanSeven(List<Player> players) {
        for (Player player : players) {
            if (player.getCards().size() >= 7) {
                List<Card> playerHand = player.getCards();
                int initialHandSize = playerHand.size();
                Collections.shuffle(playerHand);
                while (playerHand.size() > initialHandSize / 2) {
                    player.removeCard(playerHand.getFirst());
                }
            }
        }
    }
}
