package Roulette.config.properties;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class ServerSocketProperties implements PropertiesInterface {
    public static int PORT = 12;
    public static String HOSTNAME = "localhost";
    public static String WELCOME_MESSAGE = "Successfully connected to %h on port %p"; //%h = HOST | %p = PORT
    public static boolean PRINT_CONNECTED_PLAYERS = true;

    public @NotNull Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("port", PORT);
        map.put("hostname", HOSTNAME);
        map.put("welcome_message", WELCOME_MESSAGE);
        map.put("print_connected_players", PRINT_CONNECTED_PLAYERS);
        return map;
    }
}
