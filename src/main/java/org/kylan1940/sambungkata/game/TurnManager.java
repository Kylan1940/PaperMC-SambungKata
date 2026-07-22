package org.kylan1940.sambungkata.game;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {

    public static RoomPlayer current(Room room) {
        List<RoomPlayer> players = new ArrayList<>(room.getPlayers());

        return players.get(room.getCurrentTurn());
    }



    public static void next(Room room) {

        if (alivePlayers(room) <= 1) return;

        List<RoomPlayer> players = new ArrayList<>(room.getPlayers());

        int index = room.getCurrentTurn();

        do {
            index++;

            if (index >= players.size())
                index = 0;

        } while (!players.get(index).isAlive());
        room.setCurrentTurn(index);

    }

    public static int alivePlayers(Room room) {

        int alive = 0;

        for (RoomPlayer player : room.getPlayers()) {
            if (player.isAlive()) alive++;
        }
        return alive;
    }

    public static RoomPlayer winner(Room room) {

        for (RoomPlayer player : room.getPlayers()) {

            if (player.isAlive()) return player;
        }
        return null;
    }
}