package Roulette.actions;

import Roulette.player.Player;
import Roulette.rooms.AbstractRoom;
import Roulette.rooms.lobby.Lobby;
import org.jetbrains.annotations.NotNull;

public class ActionManager {
    public void handleAction(@NotNull Player player, String message) {
        AbstractRoom room = player.getCurrentRoom();

        if (room instanceof Lobby)
            ChatAction.runAction(player, message);
        else {
            switch (player.getState()) {
                case PLAYER_CHATTING -> ChatAction.runAction(player, message);
                case PLAYER_INPUTTING_INT -> InputIntegerAction.runAction(player, message);
            }
        }
    }
}