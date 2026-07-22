package org.kylan1940.sambungkata.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.game.*;
import org.kylan1940.sambungkata.message.MessageUtil;
import org.kylan1940.sambungkata.word.ValidationResult;
import org.kylan1940.sambungkata.word.WordValidator;


public class ChatListener implements Listener {

    private final RoomManager manager;

    public ChatListener(RoomManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        Room room = manager.get(player);

        if(room == null)
            return;

        if(!room.isStarted())
            return;

        RoomPlayer roomPlayer = null;

        for (RoomPlayer rp : room.getPlayers()) {
            if (rp.getUuid().equals(player.getUniqueId())) {
                roomPlayer = rp;
                break;
            }
        }

        if (roomPlayer == null)
            return;

        RoomPlayer current = TurnManager.current(room);
        if (!current.getUuid().equals(player.getUniqueId())) {
            event.setCancelled(true);
            player.sendMessage("§cBukan giliran kamu!");
            return;
        }

        event.setCancelled(true);
        String word = event.getMessage().trim().toLowerCase();
        ValidationResult result = WordValidator.validate(room, word);

        switch (result) {

            case WORD_NOT_FOUND -> {
                roomPlayer.addMistake();
                BossBarManager.update(room);
                MessageUtil.updateActionBar(room);

                MessageUtil.send(
                        player, "messages.not-found",
                        "%word%", word,
                        "%mistake%", String.valueOf(roomPlayer.getMistakes()),
                        "%max_mistakes%", String.valueOf(SambungKata.getInstance().getMaxMistakes()
                        )
                );
                checkLose(room, roomPlayer);
            }

            case WORD_ALREADY_USED -> {
                roomPlayer.addMistake();
                BossBarManager.update(room);
                MessageUtil.updateActionBar(room);

                MessageUtil.send(
                        player, "messages.already-used",
                        "%word%", word,
                        "%mistake%", String.valueOf(roomPlayer.getMistakes()),
                        "%max_mistakes%", String.valueOf(SambungKata.getInstance().getMaxMistakes())
                );

                checkLose(room, roomPlayer);
            }

            case INVALID_PREFIX -> {
                roomPlayer.addMistake();
                BossBarManager.update(room);
                MessageUtil.updateActionBar(room);

                String next = room.getLastWord().substring(room.getLastWord().length() - 1).toUpperCase();

                MessageUtil.send(
                        player, "messages.invalid-prefix",
                        "%word%", word,
                        "%mistake%", String.valueOf(roomPlayer.getMistakes()),
                        "%max_mistakes%", String.valueOf(SambungKata.getInstance().getMaxMistakes()),
                        "%next%", next
                );

                checkLose(room, roomPlayer);
            }

            case VALID -> {

                room.getUsedWords().add(word);
                room.setLastWord(word);
                String next = room.getLastWord().substring(room.getLastWord().length() - 1).toUpperCase();

                for (RoomPlayer rp : room.getPlayers()) {
                    Player target = Bukkit.getPlayer(rp.getUuid());
                    if (target != null) {
                        target.sendMessage("§e" + player.getName() + " §8» §f" + word);
                    }
                }

                MessageUtil.send(
                        player, "messages.accepted",
                        "%word%", word,
                        "%mistake%", String.valueOf(roomPlayer.getMistakes()),
                        "%max_mistakes%", String.valueOf(SambungKata.getInstance().getMaxMistakes()),
                        "%next%", next,
                        "%score%", String.valueOf(roomPlayer.getPoints())
                );

                roomPlayer.addPoint();
                roomPlayer.resetMistakes();
                room.resetTime(SambungKata.getInstance().getGameTimer());

                TurnManager.next(room);
                BossBarManager.update(room);
                MessageUtil.updateActionBar(room);
            }
        }
    }

    private void checkLose(Room room, RoomPlayer player) {

        if (player.getMistakes() < SambungKata.getInstance().getMaxMistakes())
            return;

        if (room.getPlayers().size() == 1) {

            Player target = Bukkit.getPlayer(player.getUuid());

            if (target != null) {
                MessageUtil.send(
                        target,
                        "messages.game-over",
                        "%mistake%", String.valueOf(player.getMistakes()),
                        "%score%", String.valueOf(player.getPoints())
                );
            }

            GameTimer.stop(room);
            BossBarManager.remove(room);
            room.setStarted(false);
            manager.remove(room);
            return;
        }

        player.setAlive(false);

        Player target = Bukkit.getPlayer(player.getUuid());

        if (target != null) {
            Bukkit.getScheduler().runTask(
                    SambungKata.getInstance(),
                    () -> target.setGameMode(GameMode.SPECTATOR)
            );
        }

        TurnManager.next(room);
        BossBarManager.update(room);
        MessageUtil.updateActionBar(room);

        checkWinner(room);
    }

    private void checkWinner(Room room) {

        if (TurnManager.alivePlayers(room) != 1)
            return;

        RoomPlayer winner = TurnManager.winner(room);

        Player player = Bukkit.getPlayer(winner.getUuid());

        if (player != null) {
            Bukkit.broadcastMessage("§6" + player.getName() + " memenangkan permainan!");
        }

        GameTimer.stop(room);
        room.setStarted(false);
        manager.remove(room);
        BossBarManager.remove(room);

    }

}