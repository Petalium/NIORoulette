package Roulette.commands.gamecommands;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.player.PlayerRanks;
import Roulette.utils.RoomUtils;
import org.jetbrains.annotations.NotNull;

public class CommandPromote {
    public static void runCommand(@NotNull Player player, String arg) {
        Player promotedPlayer = RoomUtils.checkIfPlayerIsInRoom(player.getCurrentRoom(), arg);

        if (player.getUsername().equals(arg))
            player.serverWriteToPlayer("You can not promote yourself.");
        else if (promotedPlayer == null)
            player.serverWriteToPlayer("Player does not exist.");
        else {
            PlayerRanks playerRank = player.getRank();
            PlayerRanks promotedPlayerRank = promotedPlayer.getRank();

            if (promotedPlayerRank.rankLevel >= 3)
                player.serverWriteToPlayer("Player can not be promoted any further.");
            else if (playerRank.rankLevel <= promotedPlayerRank.rankLevel)
                player.serverWriteToPlayer("You do not have sufficient permissions to promote this player.");
            else {
                Managers.playerManager.promotePlayer(promotedPlayer);
                promotedPlayer.serverWriteToPlayer(player.getDisplayName() +
                        " Promoted you to " + promotedPlayer.getRank().prefix);
                player.serverWriteToPlayer(promotedPlayer.getDisplayName() +
                        " Successfully was promoted to: " + promotedPlayer.getRank().prefix);
                System.out.println(player.getDisplayName() + " Promoted " +
                        promotedPlayer.getDisplayName() + " to: " + promotedPlayer.getRank().prefix);
            }
        }
    }
}
