package Roulette.rooms.game;

import Roulette.player.Player;
import Roulette.player.PlayerRanks;
import Roulette.rooms.AbstractRoom;
import Roulette.utils.RoomUtils;
import org.jetbrains.annotations.NotNull;

public class GameRoom extends AbstractRoom {
    private final RouletteGame rouletteGame;

    public GameRoom() {
        super(RoomUtils.genKey());
        rouletteGame = new RouletteGame(this);
    }

    public RouletteGame getRouletteGame() {
        return rouletteGame;
    }

    public void addPlayer(@NotNull Player player) {
        super.addPlayer(player);
        super.broadcastToRoom(player.getDisplayName() + " Has joined the room.");
    }

    public void removePlayer(@NotNull Player player) {
        super.removePlayer(player);
        super.broadcastToRoom(player.getDisplayName() + " Has left the room.");
    }

    private boolean checkOtherHosts() {
        for (Player player : super.getPlayers()) {
            if (player.getRank().equals(PlayerRanks.HOST))
                return false;
        }

        return true;
    }
}