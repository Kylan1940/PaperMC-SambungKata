package org.kylan1940.sambungkata.command;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.kylan1940.sambungkata.game.GameManager;
import org.kylan1940.sambungkata.message.MessageUtil;
import org.kylan1940.sambungkata.game.GameTimer;

public class SambungCommand implements CommandExecutor {

    private final GameManager manager;

    public SambungCommand(GameManager manager){
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player player))
            return true;

        if(manager.hasGame(player)){
            player.sendMessage(ChatColor.RED + "Game sudah dimulai.");
            return true;
        }

        manager.create(player);

        GameTimer.start(player, manager.get(player), manager);

        MessageUtil.send(player, "messages.game-start");

        return true;
    }
}