package org.kylan1940.sambungkata.message;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.kylan1940.sambungkata.SambungKata;

import java.awt.*;
import java.util.List;

import org.kylan1940.sambungkata.game.Game;

public class MessageUtil {

    public static void send(Player player, String path, String... placeholders) {

        List<String> messages = SambungKata.getInstance().getConfig().getStringList(path);

        for (String message : messages) {
            for (int i = 0; i < placeholders.length; i += 2) {
                message = message.replace(placeholders[i], placeholders[i + 1]);
            }
            player.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', message)
            );
        }
    }

    public static void actionBar(Player player, String message) {
        player.sendActionBar(
                ChatColor.translateAlternateColorCodes('&', message)
        );
    }

    public static void updateGameBar(Player player, Game game, int time) {

        player.sendActionBar(
                Component.text(
                        "§ePoints: §f" + game.getPoints()
                                + " §8| "
                                + "§cKesalahan: §f" + game.getMistakes() + "/" + SambungKata.getInstance().getMaxMistakes()
                                + " §8| "
                                + "§aTimer: §f" + time + "s"
                )
        );

    }

}