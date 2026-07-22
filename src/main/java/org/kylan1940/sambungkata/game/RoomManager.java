package org.kylan1940.sambungkata.game;

import org.bukkit.entity.Player;

import java.util.*;

public class RoomManager {

    private final Map<UUID, Room> rooms = new HashMap<>();

    public Room create(Player host, int timer){
        Room room = new Room(host, timer);
        rooms.put(host.getUniqueId(), room);
        return room;
    }

    public Room getByHost(Player player){
        return rooms.get(player.getUniqueId());
    }

    public Room get(Player player){
        for(Room room : rooms.values()){
            for(RoomPlayer rp : room.getPlayers()){
                if(rp.getUuid().equals(player.getUniqueId()))
                    return room;
            }
        }
        return null;
    }

    public boolean isPlaying(Player player){
        return get(player) != null;
    }

    public boolean isHost(Player player){
        return rooms.containsKey(player.getUniqueId());
    }

    public void remove(Room room){
        rooms.remove(room.getHost());
    }

    public Collection<Room> getRooms(){
        return rooms.values();
    }
}