package Roulette.commands.hybridcomamnds;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.rooms.AbstractRoom;
import Roulette.rooms.game.GameRoom;
import org.jetbrains.annotations.NotNull;

public class CommandClose {
    public static void runCommand(@NotNull Player player, @NotNull String arg) {
        AbstractRoom room;

        if (arg.equals("this"))
            room = player.getCurrentRoom();
        else
            room = Managers.roomManager.findRoom(arg);

        if (room == null)
            player.serverWriteToPlayer("Room not found.");
        else if (room instanceof GameRoom) {
            player.serverWriteToPlayer("Closing room " + room.getDisplayRoomKey() + "...");
            Managers.roomManager.closeRoom(room);
        } else
            player.serverWriteToPlayer("You can not close the lobby.");
    }
}
