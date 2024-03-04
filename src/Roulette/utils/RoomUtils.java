package Roulette.utils;

import Roulette.Managers;
import Roulette.config.properties.GeneralChattingProperties;
import Roulette.player.Player;
import Roulette.rooms.AbstractRoom;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class RoomUtils {
    public static @NotNull String genKey() {
        StringBuilder key = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        key.append(Managers.roomManager.rooms.size());

        for (int i = 0; i < GeneralChattingProperties.ROOM_KEY_LENGTH - 1; i++)
            key.append(chars.charAt(genRandomNumber(0, chars.length() - 1)));

        if (Managers.roomManager.findRoom(key.toString()) == null)
            return key.toString();
        return genKey();
    }

    public static @Nullable Player checkIfPlayerIsInRoom(@NotNull AbstractRoom room, String arg) {
        for (Player player : room.getPlayers()) {
            if (player.getUsername().equals(arg))
                return player;
        }

        return null;
    }

    public static int genRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
