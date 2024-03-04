package Roulette.commands.gamecommands;

import Roulette.commands.AbstractCommand;
import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Permissions;
import Roulette.player.Player;
import org.jetbrains.annotations.NotNull;

public class GameCommandManager extends AbstractCommand {
    public final String[] gameCommands = new String[] {
            Permissions.DEMOTE.referenceName, Permissions.KICK.referenceName, Permissions.LOBBY.referenceName,
            Permissions.PROMOTE.referenceName, Permissions.START.referenceName, Permissions.STOP.referenceName};

    @Override
    public void run(Player player, String @NotNull [] args) {
        switch (args[0]) {
            case "demote" -> {
                if (!player.getRank().permissions.contains(Permissions.DEMOTE))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else if (args.length < 2)
                    player.serverWriteToPlayer("Expected 1 argument: /demote {USERNAME}");
                else
                    CommandDemote.runCommand(player, args[1]);
                return;
            }
            case "kick" -> {
                if (!player.getRank().permissions.contains(Permissions.KICK))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else if (args.length < 2)
                    player.serverWriteToPlayer("Expected 1 argument: /kick {USERNAME}");
                else
                    CommandKick.runCommand(player, args[1]);
                return;
            }
            case "lobby" -> {
                if (!player.getRank().permissions.contains(Permissions.LOBBY))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else
                    CommandLobby.runCommand(player);
                return;
            }
            case "promote" -> {
                if (!player.getRank().permissions.contains(Permissions.PROMOTE))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else if (args.length < 2)
                    player.serverWriteToPlayer("Expected 1 argument: /promote {USERNAME}");
                else
                    CommandPromote.runCommand(player, args[1]);
                return;
            }
            case "start" -> {
                if (!player.getRank().permissions.contains(Permissions.START))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else
                    CommandStart.runCommand(player);
                return;
            }
            case "stop" -> {
                if (!player.getRank().permissions.contains(Permissions.STOP))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else
                    CommandStop.runCommand();
                return;
            }
        }

        super.run(player, args);
    }
}
