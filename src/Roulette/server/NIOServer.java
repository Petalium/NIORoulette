package Roulette.server;

import Roulette.Managers;
import Roulette.player.Player;
import Roulette.rooms.lobby.Lobby;
import Roulette.utils.PrintUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NIOServer {
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;
    private final ByteBuffer WELCOME_BUFFER;
    private final int BUFFER_SIZE;
    private final IOHandler ioHandler;

    public NIOServer(int port, String host, int bufferSize, @NotNull String welcomeBuffer) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            BUFFER_SIZE = bufferSize;
            WELCOME_BUFFER = ByteBuffer.wrap((welcomeBuffer + "\n\n").getBytes());

            ioHandler = new IOHandler();
            Managers.roomManager.newRoom(new Lobby());

            System.out.println("Server initialized: PORT = " + port + " | HOST = " + host);
        } catch (IOException | IllegalArgumentException e) {
            throw PrintUtils.throwRunTimeException("Fatal error encountered during server initialization.", e);
        }
    }

    public void runServer() {
        System.out.println("Server started, waiting for clients...");

        Iterator<SelectionKey> keyIterator;
        SelectionKey key;

        while (serverSocketChannel.isOpen()) {
            try {
                selector.select();
                keyIterator = selector.selectedKeys().iterator();

                while (keyIterator.hasNext()) {
                    key = keyIterator.next();
                    keyIterator.remove();

                    if (!key.isValid()) {
                        closeClient(key);
                        continue;
                    }

                    if (key.isAcceptable())
                        acceptClient(key);
                    else if (key.isReadable())
                        readClient(key);
                }
            } catch (IOException | ClosedSelectorException e) {
                throw PrintUtils.throwRunTimeException("Fatal error encountered when selecting from selector.", e);
            }
        }
    }

    private void readClient(@NotNull SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        StringBuilder messageRead = new StringBuilder();
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

        try {
            int read = clientChannel.read(byteBuffer);

            if (read == -1)
                closeClient(key);

            while (read > 0) {
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.limit()];

                byteBuffer.get(bytes);
                messageRead.append(new String(bytes));

                byteBuffer.clear();
                read = clientChannel.read(byteBuffer);
            }
        } catch (IOException e) {
            System.out.printf("Error reading from client connection. | %s | ", e);
            closeClient(key);
        }

        String parsedMessage = String.valueOf(messageRead).strip().replace("\\", "");

        if (!parsedMessage.isBlank())
            ioHandler.handleMessage(parsedMessage, key);
    }

    private void acceptClient(@NotNull SelectionKey key) {
        try {
            SocketChannel clientChannel = ((ServerSocketChannel) key.channel()).accept();
            clientChannel.configureBlocking(false);

            clientChannel.write(WELCOME_BUFFER);
            WELCOME_BUFFER.rewind();

            Player player = Managers.playerManager.newPlayer(clientChannel);
            clientChannel.register(selector, SelectionKey.OP_READ, player);

            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress() + ": " + player.getDisplayName());
        } catch (IOException e) {
            System.out.printf("Error encountered when accepting client connection. | %s | ", e);
            closeClient(key);
        }
    }

    private void closeClient(@NotNull SelectionKey key) {
        try {
            Player player = (Player) key.attachment();
            Managers.playerManager.players.remove(player);

            key.channel().close();
            key.cancel();

            System.out.println("Client " + player.getDisplayName() + " has been terminated.");
            player.getCurrentRoom().removePlayer(player);
        } catch (IOException e) {
            throw PrintUtils.throwRunTimeException("Fatal error encountered when trying to close client", e);
        }
    }
}