package Roulette.commands;

import Roulette.commands.gamecommands.GameCommandManager;
import Roulette.commands.lobbycommands.LobbyCommandManager;
import Roulette.player.Player;
import Roulette.rooms.lobby.Lobby;
import org.jetbrains.annotations.NotNull;

public class RouletteCommandManager {
    public final LobbyCommandManager lobbyCommandManager;
    public final GameCommandManager gameCommandManager;

    public RouletteCommandManager() {
        this.lobbyCommandManager = new LobbyCommandManager();
        this.gameCommandManager = new GameCommandManager();
    }

    public void handleCommand(@NotNull Player player, @NotNull String message) {
        String[] args = message.substring(1).split(" ");

        if (player.getCurrentRoom() instanceof Lobby)
            lobbyCommandManager.run(player, args);
        else
            gameCommandManager.run(player, args);
    }
}
