package org.kylan1940.sambungkata.message;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.game.Room;
import org.kylan1940.sambungkata.game.RoomPlayer;

import java.util.List;

public class MessageUtil {


    public static void send(Player player, String path, String... placeholders) {

        List<String> messages = SambungKata.getInstance().getConfig().getStringList(path);

        for (String message : messages) {

            for (int i = 0; i < placeholders.length; i += 2) {
                message = message.replace(placeholders[i], placeholders[i + 1]);
            }

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }


    public static void broadcast(Room room, String path, String... placeholders) {

        for (RoomPlayer rp : room.getPlayers()) {

            Player player = SambungKata.getInstance().getServer().getPlayer(rp.getUuid());

            if (player != null) {
                send(player, path, placeholders);
            }
        }

    }

    public static void updateActionBar(Room room){

        for(RoomPlayer rp : room.getPlayers()){

            Player player = Bukkit.getPlayer(rp.getUuid());

            if(player == null)
                continue;

            player.sendActionBar(
                    Component.text(
                            "§ePoints §f"
                                    + rp.getPoints()

                                    + " §8| "

                                    + "§cKesalahan §f"
                                    + rp.getMistakes()
                                    + "/"
                                    + SambungKata.getInstance().getMaxMistakes()

                                    + " §8| "

                                    + "§aTimer §f"
                                    + room.getTime()
                    )
            );

        }

    }
}