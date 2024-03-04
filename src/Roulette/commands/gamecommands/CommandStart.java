package Roulette.commands.gamecommands;

import Roulette.player.Player;
import Roulette.rooms.game.GameRoom;
import Roulette.rooms.game.RouletteGame;
import Roulette.rooms.game.States;
import org.jetbrains.annotations.NotNull;

public class CommandStart {
    public static void runCommand(@NotNull Player player) {
        GameRoom gameRoom = (GameRoom) player.getCurrentRoom();
        RouletteGame rouletteGame = gameRoom.getRouletteGame();

        if (!rouletteGame.getState().equals(States.GAME_IDLING))
            player.serverWriteToPlayer("Game already in progress.");
        else
            rouletteGame.runGame(gameRoom.getPlayers());
    }
}
