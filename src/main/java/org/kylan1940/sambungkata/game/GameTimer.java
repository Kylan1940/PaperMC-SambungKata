package org.kylan1940.sambungkata.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.message.MessageUtil;

public class GameTimer {

    public static void start(Room room) {
        stop(room);
        room.setTimerTask(
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!room.isStarted()) {
                            cancel();
                            return;
                        }
                        room.reduceTime();
                        BossBarManager.update(room);
                        MessageUtil.updateActionBar(room);

                        if (room.getTime() > 0)
                            return;
                        RoomPlayer current = TurnManager.current(room);

                        Player player = Bukkit.getPlayer(current.getUuid());
                        current.addMistake();

                        if (player != null) {
                            player.sendMessage("§cWaktu habis!");
                        }

                        if (current.getMistakes() >= SambungKata.getInstance().getMaxMistakes()) {
                            eliminatePlayer(room, current);
                            return;
                        }

                        room.resetTime(SambungKata.getInstance().getGameTimer());
                        TurnManager.next(room);
                    }

                }.runTaskTimer(SambungKata.getInstance(), 20L, 20L)
        );

    }



    public static void stop(Room room) {
        if (room.getTimerTask() != null) {
            room.getTimerTask().cancel();
            room.setTimerTask(null);
        }
    }



    private static void eliminatePlayer(Room room, RoomPlayer roomPlayer
    ) {
        roomPlayer.setAlive(false);
        Player player = Bukkit.getPlayer(roomPlayer.getUuid());

        if (player != null) {
            Bukkit.getScheduler().runTask(SambungKata.getInstance(), () -> {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage("§cKamu kalah!");
            });
        }

        room.resetTime(SambungKata.getInstance().getGameTimer());
        TurnManager.next(room);
    }
}