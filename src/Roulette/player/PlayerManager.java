package Roulette.player;

import Roulette.Managers;
import Roulette.rooms.AbstractRoom;
import Roulette.rooms.game.GameRoom;
import Roulette.rooms.game.States;
import Roulette.rooms.lobby.Lobby;
import Roulette.utils.PlayerUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    public List<Player> players;

    public PlayerManager() {
        players = new ArrayList<>();
    }

    public Player newPlayer(@NotNull SocketChannel clientChannel) throws IOException {
        String username = clientChannel.getRemoteAddress().toString();
        username = "User_" + username.substring(username.lastIndexOf(":") + 1);

        Player player = new Player(clientChannel, username);
        Managers.playerManager.players.add(player);

        return player;
    }

    public void promotePlayer(@NotNull Player player) {
        player.setRank(PlayerUtils.getRankFromLevel(player.getRank().rankLevel + 1));
        player.genDisplayName();
    }

    public void demotePlayer(@NotNull Player player) {
        player.setRank(PlayerUtils.getRankFromLevel(player.getRank().rankLevel - 1));
        player.genDisplayName();
    }

    public void joinRoom(@NotNull Player player, @NotNull AbstractRoom newRoom) {
        player.getCurrentRoom().removePlayer(player);
        player.setState(States.PLAYER_CHATTING);

        if (newRoom instanceof Lobby &&
                !(player.getRank().equals(PlayerRanks.PLAYER) || player.getRank().equals(PlayerRanks.ADMIN)))
            player.setRank(PlayerRanks.PLAYER);
        else if (newRoom instanceof GameRoom && newRoom.getPlayers().isEmpty() && player.getRank() != PlayerRanks.ADMIN)
            player.setRank(PlayerRanks.HOST);

        newRoom.addPlayer(player);
        player.setCurrentRoom(newRoom);
    }
}
