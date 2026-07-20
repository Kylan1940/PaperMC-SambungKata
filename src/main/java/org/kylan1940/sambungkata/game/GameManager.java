package org.kylan1940.sambungkata.game;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {

    private final Map<UUID, Game> games = new HashMap<>();

    public void create(Player player){
        games.put(player.getUniqueId(), new Game(player));
    }

    public boolean hasGame(Player player){
        return games.containsKey(player.getUniqueId());
    }

    public Game get(Player player){
        return games.get(player.getUniqueId());
    }

    public void remove(Player player){
        games.remove(player.getUniqueId());
    }

}