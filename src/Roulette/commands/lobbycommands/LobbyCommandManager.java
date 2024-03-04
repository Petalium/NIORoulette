package Roulette.commands.lobbycommands;

import Roulette.commands.AbstractCommand;
import Roulette.config.properties.IOHandlingProperties;
import Roulette.player.Permissions;
import Roulette.player.Player;
import org.jetbrains.annotations.NotNull;

public class LobbyCommandManager extends AbstractCommand {
    public final String[] lobbyCommands = new String[] {
            Permissions.CREATE.referenceName, Permissions.JOIN.referenceName};

    @Override
    public void run(Player player, String @NotNull [] args) {
        switch (args[0]) {
            case "create" -> {
                if (!player.getRank().permissions.contains(Permissions.CREATE))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else
                    CommandCreate.runCommand(player);
                return;
            }
            case "join" -> {
                if (!player.getRank().permissions.contains(Permissions.JOIN))
                    player.serverWriteToPlayer(IOHandlingProperties.INSUFFICIENT_PERMS);
                else if (args.length < 2)
                    player.serverWriteToPlayer("Expected 1 argument: /join {ROOM KEY}");
                else
                    CommandJoin.runCommand(player, args[1]);
                return;
            }
        }

        super.run(player, args);
    }
}
