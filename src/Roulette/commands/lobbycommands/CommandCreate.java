package Roulette.commands.lobbycommands;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.rooms.game.GameRoom;
import org.jetbrains.annotations.NotNull;

public class CommandCreate {
    public static void runCommand(@NotNull Player player) {
        GameRoom room = new GameRoom();
        Managers.playerManager.joinRoom(player, Managers.roomManager.newRoom(room));
        System.out.println(player.getDisplayName() + " created room: " + room.getDisplayRoomKey());
    }
}
