package org.kylan1940.sambungkata.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kylan1940.sambungkata.game.Game;
import org.kylan1940.sambungkata.game.GameManager;
import org.kylan1940.sambungkata.word.WordValidator;

public class ChatListener implements Listener {

    private final GameManager manager;

    public ChatListener(GameManager manager){

        this.manager = manager;

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){

        Player player = event.getPlayer();

        if(!manager.hasGame(player))
            return;

        event.setCancelled(true);

        Game game = manager.get(player);

        String word = event.getMessage().toLowerCase();

        if(!WordValidator.validate(game, word)){

            if (game.getLastWord() == null) {

                player.sendMessage("§cKata tidak ada di kamus.");

            } else {

                char next = game.getLastWord().charAt(game.getLastWord().length() - 1);

                player.sendMessage("§cKata tidak valid!");
                player.sendMessage("§7Harus dimulai dengan: §6" + Character.toUpperCase(next));

            }

            return;

        }

        game.getUsedWords().add(word);

        game.setLastWord(word);

        char next = word.charAt(word.length() - 1);

        player.sendMessage("");
        player.sendMessage("§a✔ Kata diterima!");
        player.sendMessage("§7Kata: §f" + word);
        player.sendMessage("§e➜ Kata berikutnya harus dimulai dengan: §6" + Character.toUpperCase(next));
        player.sendMessage("");

    }

}