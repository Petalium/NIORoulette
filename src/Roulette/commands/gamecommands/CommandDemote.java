package Roulette.commands.gamecommands;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.player.PlayerRanks;
import Roulette.utils.RoomUtils;
import org.jetbrains.annotations.NotNull;

public class CommandDemote {
    public static void runCommand(@NotNull Player player, String arg) {
        Player demotedPlayer = RoomUtils.checkIfPlayerIsInRoom(player.getCurrentRoom(), arg);

        if (player.getUsername().equals(arg))
            player.serverWriteToPlayer("You can not demote yourself.");
        else if (demotedPlayer == null)
            player.serverWriteToPlayer("Player does not exist.");
        else {
            PlayerRanks playerRank = player.getRank();
            PlayerRanks demotedPlayerRank = demotedPlayer.getRank();

            if (demotedPlayerRank.rankLevel < 1)
                player.serverWriteToPlayer("Player can not be demoted any further.");
            else if (playerRank.rankLevel <= demotedPlayerRank.rankLevel)
                player.serverWriteToPlayer("You do not have sufficient permissions to demote this player.");
            else {
                Managers.playerManager.demotePlayer(demotedPlayer);
                demotedPlayer.serverWriteToPlayer(player.getDisplayName() +
                        " Demoted you to " + demotedPlayer.getRank().prefix);
                player.serverWriteToPlayer(demotedPlayer.getDisplayName() +
                        " Successfully was demoted to: " + demotedPlayer.getRank().prefix);
                System.out.println(player.getDisplayName() + " Demoted " +
                        demotedPlayer.getDisplayName() + " to: " + demotedPlayer.getRank().prefix);
            }
        }
    }
}
