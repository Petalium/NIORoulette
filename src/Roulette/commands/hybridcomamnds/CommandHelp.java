package Roulette.commands.hybridcomamnds;

import Roulette.Managers;
import Roulette.player.Permissions;
import Roulette.player.Player;
import Roulette.rooms.lobby.Lobby;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandHelp {
    public static void runCommand(@NotNull Player player) {
        StringBuilder permissionsList = new StringBuilder("Available actions: \n");
        List<String> availablePlayerActions = new ArrayList<>();
        List<String> availableRoomActions = new ArrayList<>
                (List.of(Managers.rouletteCommandManager.lobbyCommandManager.hybridCommands));

        if (player.getCurrentRoom() instanceof Lobby)
            availableRoomActions.addAll(List.of(Managers.rouletteCommandManager.lobbyCommandManager.lobbyCommands));
        else
            availableRoomActions.addAll(List.of(Managers.rouletteCommandManager.gameCommandManager.gameCommands));

        for (Permissions permission : player.getRank().permissions)
            availablePlayerActions.add(permission.referenceName);

        availablePlayerActions.retainAll(availableRoomActions);

        for (String action : availablePlayerActions)
            permissionsList.append("- ").append(action).append("\n");

        player.serverWriteToPlayer(permissionsList.deleteCharAt(permissionsList.lastIndexOf("\n")).toString());
    }
}
