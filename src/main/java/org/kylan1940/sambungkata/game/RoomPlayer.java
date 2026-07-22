package org.kylan1940.sambungkata.game;

import java.awt.event.MouseEvent;
import java.util.UUID;

public class RoomPlayer {

    private final UUID uuid;

    private int points;
    private int mistakes;

    private boolean alive = true;

    public RoomPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void addMistake() {
        mistakes++;
    }

    public void resetMistakes() {
        mistakes = 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}