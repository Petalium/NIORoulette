package Roulette.player;

import Roulette.Colors;
import Roulette.Managers;
import Roulette.config.properties.GeneralChattingProperties;
import Roulette.config.properties.IOHandlingProperties;
import Roulette.rooms.AbstractRoom;
import Roulette.rooms.game.States;
import Roulette.server.ServerRanks;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Player {
    private final SocketChannel clientChannel;
    private PlayerRanks rank;
    private String username;
    private String displayName;
    private AbstractRoom currentRoom;
    private States state;

    public Player(SocketChannel clientChannel, String username) {
        this.rank = PlayerRanks.PLAYER;
        this.clientChannel = clientChannel;
        this.username = username;
        this.genDisplayName();
        this.currentRoom = Managers.roomManager.findRoom(IOHandlingProperties.LOBBY_NAME);

        if (currentRoom != null)
            currentRoom.addPlayer(this);
    }

    public String getDisplayName() {
        return displayName;
    }

    public synchronized void genDisplayName() {
        displayName = rank.prefixColor.ansiCode + username + Colors.RESET.ansiCode;
    }

    public String getUsername() {
        return username;
    }

    public synchronized void setUsername(String username) {
        this.username = username;
        this.genDisplayName();
    }

    public PlayerRanks getRank() {
        return rank;
    }

    public synchronized void setRank(PlayerRanks rank) {
        this.rank = rank;
        this.genDisplayName();
    }

    public SocketChannel getClientChannel() {
        return clientChannel;
    }

    public AbstractRoom getCurrentRoom() {
        return currentRoom;
    }

    public States getState() {
        return state;
    }

    public synchronized void setState(States state) {
        this.state = state;
    }

    public synchronized void setCurrentRoom(AbstractRoom newRoom) {
        currentRoom = newRoom;
    }

    public void serverWriteToPlayer(String message) {
        String formattedMessage = GeneralChattingProperties.MESSAGE_FORMAT
                .replaceAll("%s", ServerRanks.SERVER_DIRECT.prefix)
                .replaceAll("%p", ServerRanks.SERVER_DIRECT.username)
                .replaceAll("%m", message);
        ByteBuffer messageBuffer = ByteBuffer.wrap((formattedMessage + "\n").getBytes());
        SocketChannel clientChannel = this.getClientChannel();

        if (clientChannel.isConnected()) {
            try {
                clientChannel.write(messageBuffer);
            } catch (IOException e) {
                System.out.printf("Error writing to client connection. | %s\n ", e);
            }
        }
    }
}