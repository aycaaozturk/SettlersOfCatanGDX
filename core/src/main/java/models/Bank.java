package models;

import Enums.DevelopmentType;
import Enums.ResourceType;
import models.cards.Card;
import models.cards.DevelopmentCard;
import models.cards.ResourceCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank {

    private List<Card> resourceCards;
    private List<Card> developmentCards;


    public Bank(){
        resourceCards = new ArrayList<>();
        developmentCards = new ArrayList<>();


        for (int i = 0; i < 19; i++) {
            resourceCards.add(new ResourceCard(ResourceType.WOOD));
            resourceCards.add(new ResourceCard(ResourceType.WOOL));
            resourceCards.add(new ResourceCard(ResourceType.GRAIN));
            resourceCards.add(new ResourceCard(ResourceType.CLAY));
            resourceCards.add(new ResourceCard(ResourceType.ORE));


        }

        int knightCount = 14;
        int monopolyCount = 2;
        int roadBuildingCount = 2;
        int yearOfPlentyCount = 2;

        for (int i = 0; i < knightCount; i++)
            developmentCards.add(new DevelopmentCard(DevelopmentType.Knight));
        for (int i = 0; i < monopolyCount; i++)
            developmentCards.add(new DevelopmentCard(DevelopmentType.Monopoly));
        for (int i = 0; i < roadBuildingCount; i++)
            developmentCards.add(new DevelopmentCard(DevelopmentType.RoadBuilding));
        for (int i = 0; i < yearOfPlentyCount; i++)
            developmentCards.add(new DevelopmentCard(DevelopmentType.YearOfPlenty));

        Collections.shuffle(developmentCards);



    }

    public int countResource(ResourceType type) {
        int count = 0;
        for (Card card : resourceCards) {
            if(card.getClass() == ResourceCard.class){
                if(((ResourceCard) card).getType() == type)
                    count++;
            }
        }
        return count;
    }

    public int countDevelopment(DevelopmentType type) {
        int count = 0;
        for (Card card : developmentCards) {
            if(card.getClass() == DevelopmentCard.class){
                if(((DevelopmentCard) card).getType() == type)
                    count++;
            }
        }
        return count;
    }

    public void addResourceCard(Card card){
        resourceCards.add(card);
    }

    public void addDevelopmentCard(Card card){
        developmentCards.add(card);
    }

    public Card removeResourceCard(ResourceType resourceType){
        for (Card card : resourceCards) {
            ResourceCard rCard = (ResourceCard) card;
            if(rCard.getType() == resourceType){
                resourceCards.remove(rCard);
                return rCard;
            }
        }
        return null;
    }

    public Card removeDevelopmentCard(DevelopmentType developmentType){
        for (Card card : developmentCards) {
            DevelopmentCard dCard = (DevelopmentCard) card;
            if(dCard.getType() == developmentType){
                developmentCards.remove(dCard);
                return dCard;
            }
        }
        return null;
    }

    public Card drawRandomDevelopmentCard() {
        if (developmentCards.isEmpty()) return null;
        return developmentCards.remove(0);  //FIFO
    }

    public boolean hasEnoughResource(ResourceType type, int amount) {
        return countResource(type) >= amount;
    }


    //                           give to the player                 get from player, put in bank
    public boolean tradeWithBank4(ResourceType give, int giveAmount, ResourceType receive) {

        // returns false, if bank doesnt have the resource card
        if (!hasEnoughResource(receive, 1)) return false;

        for (int i = 0; i < 3; i++) {
            resourceCards.add(new ResourceCard(receive)); }
        removeResourceCard(give);
        return true;


    }


    public List<Card> getAvailableResourceCards() {
        return new ArrayList<>(resourceCards);
    }

    public List<Card> getAvailableDevelopmentCards() {
        return new ArrayList<>(developmentCards);
    }


}
