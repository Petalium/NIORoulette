package Roulette.commands.hybridcomamnds;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.rooms.AbstractRoom;

public class CommandRoomsInfo {
    public static void runCommand(Player player, String arg) {
        AbstractRoom room = Managers.roomManager.findRoom(arg);

        if (room != null)
            player.serverWriteToPlayer(Managers.roomManager.getRoomInfo(room));
        else
            player.serverWriteToPlayer("Room not found.");
    }
}