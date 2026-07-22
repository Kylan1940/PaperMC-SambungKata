package org.kylan1940.sambungkata;

import org.bukkit.plugin.java.JavaPlugin;
import org.kylan1940.sambungkata.command.SambungCommand;
import org.kylan1940.sambungkata.game.RoomManager;
import org.kylan1940.sambungkata.game.RoomPlayer;
import org.kylan1940.sambungkata.listener.ChatListener;
import org.kylan1940.sambungkata.word.WordManager;

import java.io.File;

public class SambungKata extends JavaPlugin {

    private static SambungKata instance;

    private WordManager wordManager;
    private RoomManager roomManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        File words = new File(getDataFolder(), "words.txt");
        if (!words.exists()) {
            saveResource("words.txt", false);
        }

        wordManager = new WordManager(this);
        wordManager.load();

        roomManager = new RoomManager();

        getCommand("sambung").setExecutor(
                new SambungCommand(roomManager)
        );

        getServer().getPluginManager().registerEvents(
                new ChatListener(roomManager),
                this
        );

        getLogger().info("Loaded " + wordManager.size() + " words.");
    }

    @Override
    public void onDisable() {

        roomManager.getRooms().forEach(room -> {
            if (room.getTimerTask() != null) {
                room.getTimerTask().cancel();
            }

            if (room.getBossBar() != null) {
                room.getBossBar().removeAll();
            }
        });

    }

    public static SambungKata getInstance() {
        return instance;
    }

    public WordManager getWordManager() {
        return wordManager;
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    public int getGameTimer() {
        return getConfig().getInt("game.timer", 15);
    }

    public int getMaxMistakes() {
        return getConfig().getInt("game.max-mistakes", 3);
    }

}