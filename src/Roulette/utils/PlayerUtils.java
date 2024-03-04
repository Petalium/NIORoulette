package Roulette.utils;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.player.PlayerRanks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerUtils {
    public static boolean checkIfOtherPlayerExists(@NotNull String arg) {
        for (Player player : Managers.playerManager.players) {
            if (player.getUsername().equals(arg))
                return true;
        }

        return false;
    }

    @Contract(pure = true)
    public static @Nullable PlayerRanks getRankFromLevel(int level) {
        for (PlayerRanks playerRank : PlayerRanks.values()) {
            if (playerRank.rankLevel == level)
                return playerRank;
        }

        return null;
    }
}
