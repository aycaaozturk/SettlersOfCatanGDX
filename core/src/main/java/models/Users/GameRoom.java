package models.Users;

import Enums.PlayerColor;
import models.Player;
import models.Users.User;
import java.util.*;

public class GameRoom {


    private final String roomName;
    private final User host;

    private boolean gameStarted;
    private int numberOfPlayers;

    private final List<User> players;
    private List<Player> gamePlayers;


    public GameRoom(User host, String roomName, int numberOfPlayers) {
        if(numberOfPlayers!=3 && numberOfPlayers!=4){
            throw new IllegalArgumentException("3 or 4 players needed for Catan");
        }
        this.numberOfPlayers=numberOfPlayers;

        this.host = host;
        this.players = new ArrayList<>();
        this.gamePlayers = new ArrayList<>();
        this.players.add(host);
        this.gameStarted = false;

        this.roomName=(roomName == null || roomName.trim().isEmpty()) ? host.getUsername() + "'s Room" : roomName;
    }


    public String getRoomName(){
        return roomName;
    }

    public List<User> getPlayers() {
        return new ArrayList<>(players);
    }

    public List<Player> getGamePlayers() {
        return gamePlayers;
    }

    public boolean addPlayer(User user) {
        if (!gameStarted && !players.contains(user) && players.size() < 4) {
            players.add(user);
            return true;
        }
        return false;
    }

    public void removePlayer(User user) {
        if (!gameStarted) {
            players.remove(user);
        }
    }

    public boolean isFull() {
        return players.size() >= numberOfPlayers;
    }

    public boolean isStarted() {
        return gameStarted;
    }


        public void startGame(List<User> users) {
            if (players.size() == numberOfPlayers) {
                for (User user : users) {
                    PlayerColor color = user.getPlayerColor();
                    gamePlayers.add(new Player(user, color));
                }
                gameStarted = true;
            } else {
                throw new IllegalStateException("Not enough players to start.");
            }
        }


    public User getHost() {
        return host;
    }

    @Override
    public String toString() {
        String playerNames = players.stream()
                .map(User::getUsername)
                .reduce((a, b) -> a + ", " + b)
                .orElse("No players");
        return "Room " + roomName +
                " | Players: " + players.size() +
                "/" + numberOfPlayers +
                " (" + playerNames + ")" +
                " | Started: " + gameStarted;
    }


}
