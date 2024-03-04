package Roulette.actions;

import Roulette.player.Permissions;
import Roulette.player.Player;
import org.jetbrains.annotations.NotNull;

public class ChatAction {
    public static void runAction(@NotNull Player player, String message) {
        if (player.getRank().permissions.contains(Permissions.CHAT))
            player.getCurrentRoom().playerWriteToRoom(player, message);
        else
            player.serverWriteToPlayer("You are muted.");
    }
}
