package org.kylan1940.sambungkata.game;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Game {

    private final UUID player;

    private final Set<String> usedWords = new HashSet<>();

    private String lastWord;

    private GameState state;

    public Game(Player player) {

        this.player = player.getUniqueId();

        this.state = GameState.PLAYING;
    }

    public UUID getPlayer() {
        return player;
    }

    public Set<String> getUsedWords() {
        return usedWords;
    }

    public String getLastWord() {
        return lastWord;
    }

    public void setLastWord(String lastWord) {
        this.lastWord = lastWord;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}