package org.kylan1940.sambungkata;

import org.bukkit.plugin.java.JavaPlugin;
import org.kylan1940.sambungkata.command.SambungCommand;
import org.kylan1940.sambungkata.game.GameManager;
import org.kylan1940.sambungkata.listener.ChatListener;
import org.kylan1940.sambungkata.word.WordManager;

import java.io.File;

public class SambungKata extends JavaPlugin {

    private static SambungKata instance;
    private WordManager wordManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        File words = new File(getDataFolder(), "words.txt");
        if (!words.exists())
            saveResource("words.txt", false);

        wordManager = new WordManager(this);
        wordManager.load();

        GameManager gameManager = new GameManager();

        getCommand("sambung").setExecutor(new SambungCommand(gameManager));

        getServer().getPluginManager().registerEvents(new ChatListener(gameManager), this);

        getLogger().info("Loaded " + wordManager.size() + " words");
    }

    public static SambungKata getInstance() {
        return instance;
    }

    public WordManager getWordManager() {
        return wordManager;
    }

    public int getGameTimer() {
        return getConfig().getInt("game.timer", 15);
    }

    public int getMaxMistakes() {
        return getConfig().getInt("game.max-mistakes", 3);
    }
}