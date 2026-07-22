package org.kylan1940.sambungkata.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.kylan1940.sambungkata.SambungKata;
import org.kylan1940.sambungkata.game.*;
import org.kylan1940.sambungkata.game.RoomPlayer;
import org.kylan1940.sambungkata.game.BossBarManager;

public class SambungCommand implements CommandExecutor {

    private final RoomManager manager;

    public SambungCommand(RoomManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player))
            return true;

        if (args.length == 0) {

            if (manager.isPlaying(player)) {
                player.sendMessage("§cKamu sudah berada di room.");
                return true;
            }

            Room room = manager.create(player, SambungKata.getInstance().getGameTimer());
            player.sendMessage("§aRoom berhasil dibuat.");
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "join" -> {
                if (args.length < 2) {
                    player.sendMessage("§cGunakan /sambung join <host>");
                    return true;
                }
                Player host = Bukkit.getPlayer(args[1]);

                if (host == null) {
                    player.sendMessage("§cHost tidak ditemukan.");
                    return true;
                }
                Room room = manager.getByHost(host);

                if (room == null) {
                    player.sendMessage("§cRoom tidak ditemukan.");
                    return true;
                }
                room.getPlayers().add(new RoomPlayer(player.getUniqueId()));
                player.sendMessage("§aBerhasil bergabung.");

            }

            case "start" -> {
                Room room = manager.getByHost(player);

                if (room == null) {
                    player.sendMessage("§cKamu bukan host.");
                    return true;
                }

                if (room.isStarted()) {
                    player.sendMessage("§cGame sudah dimulai.");
                    return true;
                }

                room.setStarted(true);

                BossBarManager.create(room);
                GameTimer.start(room);

                Bukkit.broadcastMessage("§aGame Sambung Kata dimulai!");
            }

            case "leave" -> {

                Room room = manager.get(player);

                if (room == null) {
                    player.sendMessage("§cKamu tidak ada di room.");
                    return true;
                }

                if (room.getHost().equals(player.getUniqueId())) {

                    player.sendMessage(
                            "§cHost tidak bisa leave. Gunakan cancel room."
                    );

                    return true;
                }


                room.getPlayers().removeIf(rp -> rp.getUuid().equals(player.getUniqueId()));

                BossBarManager.remove(room);

                for (RoomPlayer rp : room.getPlayers()) {

                    Player target = Bukkit.getPlayer(rp.getUuid());

                    if (target != null) {
                        BossBarManager.addPlayer(room, target);
                    }

                }

                player.sendMessage("§aKamu keluar dari room.");
            }

            case "list" -> {

                if (manager.getRooms().isEmpty()) {

                    player.sendMessage("§cTidak ada room aktif.");

                    return true;
                }


                player.sendMessage("§eRoom aktif:");

                for (Room room : manager.getRooms()) {

                    Player host = Bukkit.getPlayer(room.getHost());

                    String name = "Unknown";

                    if (host != null) {
                        name = host.getName();
                    }

                    player.sendMessage("§7- §f" + name + " §8(" + room.getPlayers().size() + " pemain)");

                }

            }

        }

        return true;

    }

}