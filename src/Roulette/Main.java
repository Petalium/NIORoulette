package Roulette;

import Roulette.config.properties.IOHandlingProperties;
import Roulette.config.properties.ServerSocketProperties;
import Roulette.server.NIOServer;

public class Main {
    public static void main(String[] args) {
        Managers.initialize();
        NIOServer server = new NIOServer(
                ServerSocketProperties.PORT,
                ServerSocketProperties.HOSTNAME,
                IOHandlingProperties.BUFFER_SIZE,
                ServerSocketProperties.WELCOME_MESSAGE
                        .replaceAll("%h", ServerSocketProperties.HOSTNAME)
                        .replaceAll("%p", String.valueOf(ServerSocketProperties.PORT)));
        server.runServer();
    }
}