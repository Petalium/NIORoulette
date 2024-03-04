package Roulette.commands.hybridcomamnds;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.rooms.AbstractRoom;

public class CommandRooms {
    public static void runCommand(Player player) {
        StringBuilder rooms = new StringBuilder("Open rooms: (");

        for (AbstractRoom room : Managers.roomManager.rooms)
            rooms.append(room.getDisplayRoomKey()).append(", ");

        rooms.append("\b\b)");

        player.serverWriteToPlayer(rooms.toString());
    }
}
