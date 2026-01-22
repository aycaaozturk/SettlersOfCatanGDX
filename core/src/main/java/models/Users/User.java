package models.Users;

import Enums.PlayerColor;
import com.badlogic.gdx.Game;

import java.util.UUID;

public class User {

        private String username;
        private GameRoom gameRoom;
        private PlayerColor playerColor;
        private boolean isInRoom;


    public User(String username) {
        this.username = username;
        this.playerColor=null;
        this.gameRoom=null;
        this.isInRoom=false;

        }

        public String getUsername() {
        return username;
    }

        public GameRoom getGameRoom(){ return gameRoom;}

        public PlayerColor getPlayerColor(){return playerColor;
        }

        public boolean getIsInRoom(){ return isInRoom;}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username); // equals if usernames r same
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }



    public void joinRoom(GameRoom room) {
        this.gameRoom = room;
        isInRoom=true;
    }

    public void leaveRoom() {
        this.gameRoom = null;
        isInRoom=false;
    }


    public void setPlayerColor(PlayerColor color) {
        this.playerColor = color;
    }





}
