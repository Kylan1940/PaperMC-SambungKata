package org.kylan1940.sambungkata.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.game.Game;
import org.kylan1940.sambungkata.game.GameManager;
import org.kylan1940.sambungkata.message.MessageUtil;
import org.kylan1940.sambungkata.word.ValidationResult;
import org.kylan1940.sambungkata.word.WordValidator;

public class ChatListener implements Listener {

    private final GameManager manager;

    public ChatListener(GameManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (!manager.hasGame(player))
            return;

        event.setCancelled(true);

        Game game = manager.get(player);
        String word = event.getMessage().toLowerCase().trim();

        ValidationResult result = WordValidator.validate(game, word);

        switch (result) {

            case WORD_NOT_FOUND -> {
                game.addMistake();
                MessageUtil.send(player, "messages.not-found", "%word%", word, "%mistake%", String.valueOf(game.getMistakes()), "%max_mistake%", String.valueOf(SambungKata.getInstance().getMaxMistakes()));
                checkGameOver(player, game);
            }

            case WORD_ALREADY_USED -> {
                game.addMistake();
                MessageUtil.send(player, "messages.already-used", "%word%", word, "%mistake%", String.valueOf(game.getMistakes()), "%max_mistake%", String.valueOf(SambungKata.getInstance().getMaxMistakes()));
                checkGameOver(player, game);
            }

            case INVALID_PREFIX -> {
                game.addMistake();
                String next = game.getLastWord().substring(game.getLastWord().length() - 1).toUpperCase();
                MessageUtil.send(player, "messages.invalid-prefix", "%next%", next, "%mistake%", String.valueOf(game.getMistakes()), "%max_mistake%", String.valueOf(SambungKata.getInstance().getMaxMistakes()));
                checkGameOver(player, game);
            }

            case VALID -> {
                game.getUsedWords().add(word);
                game.setLastWord(word);
                game.addPoint();
                game.resetTime();
                game.resetMistakes();
                String next = word.substring(word.length() - 1).toUpperCase();
                MessageUtil.send(player, "messages.accepted", "%word%", word, "%next%", next, "%score%", String.valueOf(game.getPoints()));
            }

        }
    }

    private void checkGameOver(Player player, Game game) {

        if (game.getMistakes() >= SambungKata.getInstance().getMaxMistakes()) {
            MessageUtil.send(player, "messages.game-over", "%score%", String.valueOf(game.getPoints()), "%mistake%", String.valueOf(game.getMistakes()), "%max_mistake%", String.valueOf(SambungKata.getInstance().getMaxMistakes()));
            manager.remove(player);
        }
    }
}