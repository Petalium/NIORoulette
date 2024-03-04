package Roulette.server;

import Roulette.Managers;
import Roulette.player.Player;
import org.jetbrains.annotations.NotNull;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOHandler {
    private final ExecutorService vExecutorService = Executors.newVirtualThreadPerTaskExecutor();

    public void handleMessage(@NotNull String message, SelectionKey key) {
        vExecutorService.execute(() -> {
            Player player = (Player) key.attachment();

            if (message.startsWith("/"))
                Managers.rouletteCommandManager.handleCommand(player, message);
            else
                Managers.actionManager.handleAction(player, message);
        });
    }
}