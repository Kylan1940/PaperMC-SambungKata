package org.kylan1940.sambungkata.game;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarManager {

    public static void create(Room room) {

        BossBar bossBar = Bukkit.createBossBar(
                "§eSambung Kata",
                BarColor.YELLOW,
                BarStyle.SOLID
        );

        room.setBossBar(bossBar);

        for (RoomPlayer rp : room.getPlayers()) {

            Player player = Bukkit.getPlayer(rp.getUuid());

            if (player != null) {
                bossBar.addPlayer(player);
            }

        }

    }


    public static void update(Room room) {

        BossBar bossBar = room.getBossBar();

        if (bossBar == null)
            return;

        RoomPlayer current = TurnManager.current(room);

        Player player = Bukkit.getPlayer(
                current.getUuid()
        );

        String name = "Unknown";

        if (player != null) {
            name = player.getName();
        }

        bossBar.setTitle("§eGiliran: §f" + name + " §8| §aTimer: §f" + room.getTime() + "s");

        double progress = room.getTime() / (double) org.kylan1940.sambungkata.SambungKata.getInstance().getGameTimer();

        bossBar.setProgress(Math.max(0, Math.min(1, progress)));

    }


    public static void addPlayer(Room room, Player player) {

        BossBar bossBar = room.getBossBar();

        if (bossBar != null) {
            bossBar.addPlayer(player);
        }

    }


    public static void remove(Room room) {

        BossBar bossBar = room.getBossBar();

        if (bossBar != null) {
            bossBar.removeAll();
            room.setBossBar(null);
        }

    }

}