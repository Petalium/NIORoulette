package Roulette.rooms.lobby;

import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Player;
import Roulette.rooms.AbstractRoom;
import org.jetbrains.annotations.NotNull;

public class Lobby extends AbstractRoom {
    public Lobby() {
        super(IOHandlingProperties.LOBBY_NAME);
    }

    public void addPlayer(@NotNull Player player) {
        super.addPlayer(player);
        super.broadcastToRoom(player.getDisplayName() + " Has joined the lobby.");
    }

    public void removePlayer(@NotNull Player player) {
        super.removePlayer(player);
        super.broadcastToRoom(player.getDisplayName() + " Has left the lobby.");
    }
}