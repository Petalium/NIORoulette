package Roulette.rooms;

import Roulette.Managers;
import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Player;
import Roulette.rooms.lobby.Lobby;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomManager {
    public List<AbstractRoom> rooms;

    public RoomManager() {
        this.rooms = new ArrayList<>();
    }

    public AbstractRoom newRoom(AbstractRoom abstractRoom) {
        rooms.add(abstractRoom);
        return abstractRoom;
    }

    public void closeRoom(@NotNull AbstractRoom abstractRoom) {
        Lobby lobby = (Lobby) Managers.roomManager.findRoom(IOHandlingProperties.LOBBY_NAME);
        CopyOnWriteArrayList<Player> players = new CopyOnWriteArrayList<>(abstractRoom.getPlayers());

        for (Player player : players) {
            player.serverWriteToPlayer("Room closing; Rejoining lobby...");

            if (lobby != null)
                Managers.playerManager.joinRoom(player, lobby);
        }

        rooms.remove(abstractRoom);
        abstractRoom.closeRoom();
    }

    public @Nullable AbstractRoom findRoom(String roomKey) {
        for (AbstractRoom room : rooms) {
            if (room.getRoomKey().equals(roomKey)) {
                return room;
            }
        }

        return null;
    }

    public @NotNull String getRoomInfo(@NotNull AbstractRoom room) {
        StringBuilder roomInfo = new StringBuilder("Room Info\n");
        roomInfo.append("Room Key: ").append(room.getDisplayRoomKey()).append("\n");
        roomInfo.append("Players: (");

        for (Player player : room.getPlayers())
            roomInfo.append(player.getDisplayName()).append(", ");

        roomInfo.append("\b\b)");
        return roomInfo.toString();
    }
}