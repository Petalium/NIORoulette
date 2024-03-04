package Roulette.commands.gamecommands;

import Roulette.Managers;
import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Player;
import Roulette.rooms.lobby.Lobby;
import org.jetbrains.annotations.NotNull;

public class CommandLobby {
    public static void runCommand(@NotNull Player player) {
        Lobby lobby = (Lobby) Managers.roomManager.findRoom(IOHandlingProperties.LOBBY_NAME);

        if (lobby != null)
            Managers.playerManager.joinRoom(player, lobby);
        else
            player.serverWriteToPlayer("Error trying to join lobby.");
    }
}
