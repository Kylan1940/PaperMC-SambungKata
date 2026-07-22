package org.kylan1940.sambungkata.game;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import java.util.Collection;

import java.util.*;

public class Room {

    private final UUID host;

    private final LinkedHashMap<UUID, RoomPlayer> players = new LinkedHashMap<>();

    private final Set<String> usedWords = new HashSet<>();

    private String lastWord;

    private int currentTurn;

    private boolean started;

    private int time;

    private BukkitTask timerTask;

    private BossBar bossBar;

    public Room(Player host, int timer) {

        this.host = host.getUniqueId();
        this.time = timer;

        players.put(host.getUniqueId(), new RoomPlayer(host.getUniqueId()));

    }

    public UUID getHost() {
        return host;
    }

    public Collection<RoomPlayer> getPlayers() {
        return players.values();
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

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getTime() {
        return time;
    }

    public void resetTime(int seconds) {
        time = seconds;
    }

    public void reduceTime() {
        time--;
    }

    public BukkitTask getTimerTask() {
        return timerTask;
    }

    public void setTimerTask(BukkitTask timerTask) {
        this.timerTask = timerTask;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
    }

    public RoomPlayer getPlayer(UUID uuid) {
        return players.get(uuid);
    }
}