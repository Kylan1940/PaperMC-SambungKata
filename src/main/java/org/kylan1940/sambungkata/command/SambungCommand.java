package org.kylan1940.sambungkata.command;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.kylan1940.sambungkata.game.GameManager;

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

        player.sendMessage("");
        player.sendMessage("§6§l=== SAMBUNG KATA ===");
        player.sendMessage("§aGame berhasil dimulai!");
        player.sendMessage("§aGame by: github.com/Kylan1940");
        player.sendMessage("§7• Kata pertama bebas");
        player.sendMessage("§7• Ketik kata melalui chat");
        player.sendMessage("");
        player.sendMessage("§eSilakan masukkan kata pertama:");

        return true;
    }
}