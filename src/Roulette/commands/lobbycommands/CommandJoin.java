package Roulette.commands.lobbycommands;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.rooms.AbstractRoom;
import Roulette.rooms.game.GameRoom;

public class CommandJoin {
    public static void runCommand(Player player, String arg) {
        AbstractRoom room = Managers.roomManager.findRoom(arg);

        if (room instanceof GameRoom)
            Managers.playerManager.joinRoom(player, room);
        else
            player.serverWriteToPlayer("Room not found.");
    }
}
