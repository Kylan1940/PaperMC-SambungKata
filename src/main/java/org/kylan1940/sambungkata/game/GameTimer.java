package org.kylan1940.sambungkata.game;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.message.MessageUtil;

public class GameTimer {

    public static void start(Player player, Game game, GameManager manager) {

        new BukkitRunnable() {

            @Override
            public void run() {
                if (!manager.hasGame(player)) {
                    cancel();
                    return;
                }

                game.reduceTime();
                MessageUtil.updateGameBar(player, game, game.getTime()
                );

                if (game.getTime() <= 0) {
                    MessageUtil.send(player, "messages.game-over", "%score%", String.valueOf(game.getPoints()), "%mistake%", String.valueOf(game.getMistakes()));
                    manager.remove(player);
                    cancel();
                }

            }

        }.runTaskTimer(SambungKata.getInstance(), 20L, 20L);
    }
}