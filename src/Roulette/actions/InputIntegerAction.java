package Roulette.actions;

import Roulette.player.Player;
import Roulette.rooms.game.GameRoom;
import org.jetbrains.annotations.NotNull;

public class InputIntegerAction {
    public static void runAction(@NotNull Player player, String message) {
        try {
            int input = Integer.parseInt(message);
            GameRoom gameRoom = (GameRoom) player.getCurrentRoom();
            gameRoom.getRouletteGame().processIntegerInput(player, input);
        } catch (NumberFormatException ignored) {
            player.serverWriteToPlayer("Input must be an integer.");
        }
    }
}