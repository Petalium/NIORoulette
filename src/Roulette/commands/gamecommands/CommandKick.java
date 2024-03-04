package Roulette.commands.gamecommands;

import Roulette.Managers;
import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Player;
import Roulette.rooms.AbstractRoom;
import Roulette.rooms.lobby.Lobby;
import Roulette.utils.RoomUtils;
import org.jetbrains.annotations.NotNull;

public class CommandKick {
    public static void runCommand(@NotNull Player player, @NotNull String arg) {
        AbstractRoom room = player.getCurrentRoom();
        Lobby lobby = (Lobby) Managers.roomManager.findRoom(IOHandlingProperties.LOBBY_NAME);
        Player kickedPlayer = RoomUtils.checkIfPlayerIsInRoom(room, arg);

        if (player.getUsername().equals(arg))
            player.serverWriteToPlayer("You can not kick yourself.");
        else if (kickedPlayer == null)
            player.serverWriteToPlayer("Player does not exist.");
        else {
            if (player.getRank().rankLevel <= kickedPlayer.getRank().rankLevel)
                player.serverWriteToPlayer("You do not have sufficient permissions to kick this player.");
            else {
                if (lobby == null)
                    player.serverWriteToPlayer("Error kicking player.");
                else {
                    kickedPlayer.serverWriteToPlayer("You've been kicked by " + player.getDisplayName());
                    Managers.playerManager.joinRoom(kickedPlayer, lobby);
                    player.serverWriteToPlayer(kickedPlayer.getDisplayName() + " Was successfully kicked.");
                }
            }
        }
    }
}
