package Roulette.commands;

import Roulette.commands.hybridcomamnds.*;
import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Permissions;
import Roulette.player.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand {
    public final String[] hybridCommands = new String[] {
            Permissions.CLOSE.referenceName, Permissions.HELP.referenceName, Permissions.RELOAD.referenceName,
            Permissions.ROOMS.referenceName, Permissions.ROOMSINFO.referenceName, Permissions.SHUTDOWN.referenceName,
            Permissions.USERNAME.referenceName, Permissions.CHAT.referenceName};

    public void run(Player player, String @NotNull [] args) {
        switch (args[0]) {
            case "admin" -> {
                if (args.length < 2)
                    player.serverWriteToPlayer("Expected 1 argument: /admin {PASSWORD}");
                else
                    CommandAdmin.runCommand(player, args[1]);
            }
            case "close" -> {
                if (!player.getRank().permissions.contains(Permissions.CLOSE))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else if (args.length < 2)
                    player.serverWriteToPlayer("Expected 1 argument: /close {ROOM KEY}");
                else
                    CommandClose.runCommand(player, args[1]);
            }
            case "help" -> {
                if (!player.getRank().permissions.contains(Permissions.HELP))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else
                    CommandHelp.runCommand(player);
            }
            case "reload" -> {
                if (!player.getRank().permissions.contains(Permissions.RELOAD))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else
                    CommandReload.runCommand();
            }
            case "rooms" -> {
                if (!player.getRank().permissions.contains(Permissions.ROOMS))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else
                    CommandRooms.runCommand(player);
            }
            case "roomsinfo" -> {
                if (!player.getRank().permissions.contains(Permissions.ROOMSINFO))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else if (args.length < 2)
                    player.serverWriteToPlayer("Expected 1 argument: /roomsinfo {ROOM KEY}");
                else
                    CommandRoomsInfo.runCommand(player, args[1]);
            }
            case "shutdown" -> {
                if (!player.getRank().permissions.contains(Permissions.SHUTDOWN))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else
                    CommandShutdown.runCommand();
            }
            case "username" -> {
                if (!player.getRank().permissions.contains(Permissions.HELP))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else if (args.length < 2)
                    player.serverWriteToPlayer("Expected 1 argument: /username {USERNAME}");
                else
                    CommandUsername.runCommand(player, args[1]);
            }
            default -> player.serverWriteToPlayer("Unknown command; Type /help for a list of commands.");
        }
    }
}
