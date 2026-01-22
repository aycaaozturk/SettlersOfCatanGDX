package io.github.catan;

import Enums.PlayerColor;
import Enums.ResourceType;
import com.badlogic.gdx.*;
import models.Board;
import models.Player;
import models.Screens.GameScreen;
import models.Users.User;
import models.cards.ResourceCard;

import java.util.ArrayList;
import java.util.List;

public class CatanGame extends Game {
   //this class manages the game life cycyle
   //displays the screens, provides transitions between screens

    private Board board;
    private List<Player> playerList;
    private Player current;
    private CatanGame game;

    @Override
    public void create() {

        Player testPlayer = new Player(new User("p1"), PlayerColor.RED);
        Player testPlayer2 = new Player(new User("p2"), PlayerColor.WHITE);
        Player testPlayer3 = new Player(new User("p3"), PlayerColor.GREEN);
        Player testPlayer4 = new Player(new User("p4"), PlayerColor.BLUE);

        current = testPlayer;
        playerList = new ArrayList<Player>();
        playerList.add(testPlayer);
        playerList.add(testPlayer2);
        playerList.add(testPlayer3);
        playerList.add(testPlayer4);

        testPlayer.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer.addCard(new ResourceCard(ResourceType.WOOL));
        testPlayer.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer.addCard(new ResourceCard(ResourceType.GRAIN));
        testPlayer.addCard(new ResourceCard(ResourceType.GRAIN));
        testPlayer.addCard(new ResourceCard(ResourceType.GRAIN));

        testPlayer2.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer2.addCard(new ResourceCard(ResourceType.WOOL));
        testPlayer2.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer2.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer2.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer2.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer2.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer2.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer2.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer2.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer2.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer2.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer2.addCard(new ResourceCard(ResourceType.GRAIN));
        testPlayer2.addCard(new ResourceCard(ResourceType.GRAIN));
        testPlayer2.addCard(new ResourceCard(ResourceType.GRAIN));

        testPlayer3.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer3.addCard(new ResourceCard(ResourceType.WOOL));
        testPlayer3.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer3.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer3.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer3.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer3.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer3.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer3.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer3.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer3.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer3.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer3.addCard(new ResourceCard(ResourceType.GRAIN));
        testPlayer3.addCard(new ResourceCard(ResourceType.GRAIN));
        testPlayer3.addCard(new ResourceCard(ResourceType.GRAIN));

        testPlayer4.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer4.addCard(new ResourceCard(ResourceType.WOOL));
        testPlayer4.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer4.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer4.addCard(new ResourceCard(ResourceType.ORE));
        testPlayer4.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer4.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer4.addCard(new ResourceCard(ResourceType.CLAY));
        testPlayer4.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer4.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer4.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer4.addCard(new ResourceCard(ResourceType.WOOD));
        testPlayer4.addCard(new ResourceCard(ResourceType.GRAIN));
        testPlayer4.addCard(new ResourceCard(ResourceType.GRAIN));
        testPlayer4.addCard(new ResourceCard(ResourceType.GRAIN));

        this.board = Board.CreateRandomBoard();
        board.prepareBoard();
        this.setScreen(new GameScreen(this, testPlayer, board));
    }

    public void ChangeTurn(Player player){
        this.setScreen(new GameScreen(this, player, board));
    }

    public void ChangeTurn(){
        int index = playerList.indexOf(current);
        if (index + 1 >= playerList.size())
            index = 0;
        else
            index++;
        current = playerList.get(index);
        this.setScreen(new GameScreen(this, playerList.get(index), board));
    }


}
