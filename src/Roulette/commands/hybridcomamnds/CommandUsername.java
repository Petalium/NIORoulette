package Roulette.commands.hybridcomamnds;

import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Player;
import Roulette.utils.PlayerUtils;
import org.jetbrains.annotations.NotNull;

public class CommandUsername {
    public static void runCommand(Player player, @NotNull String arg) {
        if (arg.length() > IOHandlingProperties.MAX_USERNAME_LENGTH)
            player.serverWriteToPlayer("Username can not be longer than " +
                    IOHandlingProperties.MAX_USERNAME_LENGTH + " characters.");
        else if (player.getUsername().equals(arg))
            player.serverWriteToPlayer("Your username is already: " + player.getDisplayName());
        else if (PlayerUtils.checkIfOtherPlayerExists(arg))
            player.serverWriteToPlayer("Username already taken.");
        else {
            System.out.println(player.getDisplayName() + " has updated their username to: " + arg);
            player.setUsername(arg);
            player.serverWriteToPlayer("Username successfully changed to: " + player.getDisplayName());
        }
    }
}
