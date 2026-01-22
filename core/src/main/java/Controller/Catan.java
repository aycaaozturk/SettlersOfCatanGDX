package Controller;

import models.Bank;
import models.BoardPieces.Structure;
import models.Player;
import models.cards.Card;
import models.cards.ResourceCard;

import java.util.List;

public class Catan {

    private List<Player> players;
    private Player currentPlayer;
    private Bank bank;

    public Catan(List<Player> players, Player currentPlayer, Bank bank) {
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.bank = bank;
    }

    // Checks for winning condition
    public boolean winCheck(){
        return currentPlayer.getVictoryPoints() == 10;
    }

    // returns player of current turn
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    // distributes resources to all players
    public void distributeResources(){
        for (Player player : players) {
            List<Structure> playerStructures = player.getStructures();
            //TODO how does someone look up, which and how many resources someone has to get?
        }
    }

    // Trade between currentPlayer and the target. Checks, if Players are eligible to trade.
    public void tradeP2P(Player currentPlayer, Player target, List<Card> currentPlayerResourceTrade, List<Card> targetResourceTrade){
        if (currentPlayer.getCards() == currentPlayerResourceTrade && target.getCards() == targetResourceTrade){
            //currentPlayer.removeResourceCards(currentPlayerResourceTrade);
            //currentPlayer.addResourceCards(targetResourceTrade);
            //target.removeResourceCards(targetResourceTrade);
            //target.addResourceCards(currentPlayerResourceTrade);
        }
    }
    // Trade with stock (4:1)
    public void tradeP2S(Player player, ResourceCard target, ResourceCard tradeCard){
        if(target.equals(tradeCard)){
            throw new IllegalArgumentException("You can not trade the same card");
        }
        trade(player, target, tradeCard, 4);
    }

    // trade with harbor (3:1)
    public void tradeP2HGeneral(Player player, ResourceCard target, ResourceCard tradeCard){
        //TODO chekcen ob eine city oder settlement am Hafen ist
        trade(player, target, tradeCard, 3);
    }

    // trade with harbor (2:1)
    public void tradeP2HSpecific(Player player, ResourceCard target, ResourceCard tradeCard){
        //TODO chekcen ob eine city oder settlement am Hafen ist und ob er die die tradeCard handelt oder nicht
        trade(player, target, tradeCard, 2);
    }

    // a general trade, where you can select a cardLimit (amount of equal cards needed) you want to trade. Then the player trades with the bank
    public void trade(Player player, ResourceCard target, ResourceCard tradeCard, int cardLimit){
        int cardAmount = 0;
        for(Card card : player.getCards()){
            if(tradeCard.equals(card)){
                cardAmount++;
            }
        }
        if(cardAmount >= cardLimit){
            for (int i = 0; i < cardLimit; i++) {
                //player.removeResourceCard(tradeCard);
                //bank.addCard(tradeCard);
            }
            //bank.removeResourceCard(tradeCard);
            //player.addResourceCard(tradeCard);
        }
    }





    //TODO trade with Bank (Seehandel mit der Bank per Regelwerk in verschiedenen Verhältnissen wie 3:1, 4:1)


}
