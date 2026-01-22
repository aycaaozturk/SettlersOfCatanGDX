package models;

import Enums.DevelopmentType;
import Enums.PlayerColor;
import Enums.ResourceType;
import models.BoardPieces.City;
import models.BoardPieces.Road;
import models.BoardPieces.Settlement;
import models.BoardPieces.Structure;
import models.Users.User;
import models.cards.Card;
import models.cards.DevelopmentCard;
import models.cards.ResourceCard;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public User user;


    public static int roadLimit = 15;
    public static int settlementLimit = 5;
    public static int cityLimit = 5;

    private boolean largestArmy = false;
    private boolean longestRoad = false;

    //  private String name;
    private PlayerColor color;

    private List<Card> cards;
    private List<Structure> structures;


    private int knightsPlayed = 0;

    public Player(User user, PlayerColor color) {
        //  this.name = name;
        this.user = user;
        this.color = color;

        this.cards = new ArrayList<>();
        this.structures = new ArrayList<>();
    }

    public String getName() {
        return user.getUsername();
    }

    public User getUser() {
        return user;
    }

    public PlayerColor getColor() {
        return color;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public void playKnightCard() {
        knightsPlayed++;
    }

    public int getKnightsPlayed() {
        return knightsPlayed;
    }

    public boolean hasLargestArmy() {
        if(knightsPlayed<3){
            return false;
        }
        return largestArmy;
    }

    public void setLargestArmy(boolean bool) {
        this.largestArmy = bool;
    }

    public boolean hasLongestRoad() {
        return longestRoad;
    }

    public void setLongestRoad(boolean bool) {

        this.longestRoad = bool;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public int getVictoryPoints() {
        int points = 0;
        points += countSettlements();     // 1 VP
        points += 2 * countCities();      // 2 VP
        if (largestArmy) points += 2;
        if (longestRoad) points += 2;
        return points;
    }

    public int countCards() {
        return cards.size();
    }

    public int countResource(ResourceType type) {
        int count = 0;
        for (Card card : cards) {
            if (card.getClass() == ResourceCard.class) {
                if (((ResourceCard) card).getType() == type)
                    count++;
            }
        }
        return count;
    }

    public int countDevelopment(DevelopmentType type) {
        int count = 0;
        for (Card card : cards) {
            if(card.getClass() == DevelopmentCard.class){
                if(((DevelopmentCard) card).getType() == type)
                    count++;
            }
        }
        return count;
    }

    public int countRoads() {
        int count = 0;
        for (Structure structure : structures) {
            if (structure.getClass() == Road.class) {
                count++;
            }
        }
        return count;
    }

    public int countSettlements() {
        int count = 0;
        for (Structure structure : structures) {
            if (structure.getClass() == Settlement.class) {
                count++;
            }
        }
        return count;
    }

    public int countCities() {
        int count = 0;
        for (Structure structure : structures) {
            if (structure.getClass() == City.class) {
                count++;
            }
        }
        return count;
    }

    public void takeResources(Structure structure) {
        int orePaid = 0;
        int woodPaid = 0;
        int clayPaid = 0;
        int woolPaid = 0;
        int grainPaid = 0;

        List<Card> cardsToRemove = new ArrayList<>();

        while (orePaid < structure.getOreCost()) {
            for (Card card : cards) {
                if (orePaid >= structure.getOreCost())
                    continue;
                if (card instanceof ResourceCard cardResource) {
                    if (cardResource.getType() == ResourceType.ORE) {
                        cardsToRemove.add(card);
                        orePaid++;
                    }
                }
            }
        }

        while (woodPaid < structure.getWoodCost()) {
            for (Card card : cards) {
                if (woodPaid >= structure.getWoodCost())
                    continue;
                if (card instanceof ResourceCard cardResource) {
                    if (cardResource.getType() == ResourceType.WOOD) {
                        cardsToRemove.add(card);
                        woodPaid++;
                    }
                }
            }
        }

        while (woolPaid < structure.getWoolCost()) {
            for (Card card : cards) {
                if (woolPaid >= structure.getWoolCost())
                    continue;
                if (card instanceof ResourceCard cardResource) {
                    if (cardResource.getType() == ResourceType.WOOL) {
                        cardsToRemove.add(card);
                        woolPaid++;
                    }
                }
            }
        }

        while (clayPaid < structure.getClayCost()) {
            for (Card card : cards) {
                if (clayPaid >= structure.getClayCost())
                    continue;
                if (card instanceof ResourceCard cardResource) {
                    if (cardResource.getType() == ResourceType.CLAY) {
                        cardsToRemove.add(card);
                        clayPaid++;
                    }
                }
            }
        }

        while (grainPaid < structure.getGrainCost()) {
            for (Card card : cards) {
                if (grainPaid >= structure.getGrainCost())
                    continue;
                if (card instanceof ResourceCard cardResource) {
                    if (cardResource.getType() == ResourceType.GRAIN) {
                        cardsToRemove.add(card);
                        grainPaid++;
                    }
                }
            }
        }

        cards.removeAll(cardsToRemove);
    }

    public boolean canBuildRoad() {
        if (countRoads() >= Player.roadLimit) {
            return false;
        }
        if (countResource(ResourceType.WOOD) < Road.WoodCost)
            return false;
        if (countResource(ResourceType.WOOL) < Road.WoolCost)
            return false;
        if (countResource(ResourceType.CLAY) < Road.ClayCost)
            return false;
        if (countResource(ResourceType.GRAIN) < Road.GrainCost)
            return false;
        if (countResource(ResourceType.ORE) < Road.OreCost)
            return false;

        return true;
    }

    public boolean canBuildSettlement() {
        if (countSettlements() >= Player.settlementLimit) {
            return false;
        }
        if (countResource(ResourceType.WOOD) < Settlement.WoodCost)
            return false;
        if (countResource(ResourceType.WOOL) < Settlement.WoolCost)
            return false;
        if (countResource(ResourceType.CLAY) < Settlement.ClayCost)
            return false;
        if (countResource(ResourceType.GRAIN) < Settlement.GrainCost)
            return false;
        if (countResource(ResourceType.ORE) < Settlement.OreCost)
            return false;

        return true;
    }

    public boolean canBuildCity() {
        if (countCities() >= Player.cityLimit) {
            return false;
        }
        if (countResource(ResourceType.WOOD) < City.WoodCost)
            return false;
        if (countResource(ResourceType.WOOL) < City.WoolCost)
            return false;
        if (countResource(ResourceType.CLAY) < City.ClayCost)
            return false;
        if (countResource(ResourceType.GRAIN) < City.GrainCost)
            return false;
        if (countResource(ResourceType.ORE) < City.OreCost)
            return false;

        return true;
    }


}
