package Roulette.rooms;

import Roulette.Colors;
import Roulette.config.properties.GeneralChattingProperties;
import Roulette.config.properties.ServerSocketProperties;
import Roulette.player.Player;
import Roulette.server.ServerRanks;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRoom {
    protected List<Player> players;
    protected List<String> prevChat;
    protected String roomKey;
    protected String displayRoomKey;

    public AbstractRoom(String roomKey) {
        players = new ArrayList<>();
        prevChat = new ArrayList<>();
        this.roomKey = roomKey;
        displayRoomKey = Colors.CYAN.ansiCode + roomKey + Colors.RESET.ansiCode;
    }

    public void closeRoom() {
        players = null;
        prevChat = null;
        roomKey = null;
        displayRoomKey = null;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.serverWriteToPlayer("Connected to room: " + getDisplayRoomKey());

        if (ServerSocketProperties.PRINT_CONNECTED_PLAYERS)
            writeConnectedPlayers(player);

        if (GeneralChattingProperties.SEND_PREVIOUS_CHAT && !prevChat.isEmpty())
            player.serverWriteToPlayer(getPreviousChat());
    }

    public void removePlayer(@NotNull Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public String getDisplayRoomKey() {
        return displayRoomKey;
    }

    private @NotNull String getPreviousChat() {
        StringBuilder prevChatMessages = new StringBuilder().append("Previous Chat:\n");

        for (String message : prevChat) {
            prevChatMessages.append(message).append("\n");
        }

        return prevChatMessages.append("\b-----").toString();
    }

    public void playerWriteToRoom(@NotNull Player player, String message) {
        String formattedMessage = GeneralChattingProperties.MESSAGE_FORMAT
                .replaceAll("%s", player.getRank().prefix)
                .replaceAll("%p", player.getDisplayName())
                .replaceAll("%m", message);
        ByteBuffer messageBuffer = ByteBuffer.wrap((formattedMessage + "\n").getBytes());
        prevChat.add(formattedMessage);

        for (Player receivingPlayer : players) {
            SocketChannel clientChannel = receivingPlayer.getClientChannel();

            if (clientChannel.isConnected() && receivingPlayer != player) {
                try {
                    clientChannel.write(messageBuffer);
                    messageBuffer.rewind();
                } catch (IOException e) {
                    System.out.printf("Error writing to clients. | %s\n", e);
                }
            }
        }
    }

    public void broadcastToRoom(String message) {
        String formattedMessage = GeneralChattingProperties.MESSAGE_FORMAT
                .replaceAll("%s", ServerRanks.SERVER.prefix)
                .replaceAll("%p", ServerRanks.SERVER.username)
                .replaceAll("%m", message);
        ByteBuffer messageBuffer = ByteBuffer.wrap((formattedMessage + "\n").getBytes());
        prevChat.add(formattedMessage);

        for (Player receivingPlayer : players) {
            SocketChannel clientChannel = receivingPlayer.getClientChannel();

            if (clientChannel.isConnected()) {
                try {
                    clientChannel.write(messageBuffer);
                    messageBuffer.rewind();
                } catch (IOException e) {
                    System.out.printf("Error writing to clients. | %s\n", e);
                }
            }
        }
    }

    public void writeConnectedPlayers(Player player) {
        StringBuilder connectedPlayers = new StringBuilder("Connected Players: (");

        for (Player playerConnected : players)
            connectedPlayers.append(playerConnected.getDisplayName()).append(", ");

        connectedPlayers.append("\b\b)");
        ByteBuffer messageBuffer = ByteBuffer.wrap((connectedPlayers + "\n").getBytes());
        SocketChannel clientChannel = player.getClientChannel();

        if (clientChannel.isConnected()) {
            try {
                clientChannel.write(messageBuffer);
            } catch (IOException e) {
                System.out.printf("Error writing to client | %s | ", e);
            }
        }
    }
}