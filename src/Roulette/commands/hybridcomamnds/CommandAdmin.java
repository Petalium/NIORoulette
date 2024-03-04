package Roulette.commands.hybridcomamnds;

import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Player;
import Roulette.player.PlayerRanks;
import org.jetbrains.annotations.NotNull;

public class CommandAdmin {
    public static void runCommand(@NotNull Player player, @NotNull String arg) {
        if (player.getRank().equals(PlayerRanks.ADMIN))
            player.serverWriteToPlayer("You are already an admin.");
        else if (!arg.equals(IOHandlingProperties.ADMIN_PASSWORD))
            player.serverWriteToPlayer("Invalid password");
        else {
            player.setRank(PlayerRanks.ADMIN);
            System.out.println(player.getDisplayName() + " has escalated their rank to " + player.getRank().prefix);
            player.serverWriteToPlayer("Rank successfully escalated to " + player.getRank().prefix);
        }
    }
}