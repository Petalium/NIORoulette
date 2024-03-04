package Roulette.config.properties;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class IOHandlingProperties implements PropertiesInterface {
    public static int BUFFER_SIZE = 1024;
    public static int MAX_USERNAME_LENGTH = 16;
    public static String ADMIN_PASSWORD = "password";
    public static String INSUFFICIENT_PERMS = "You do not have sufficient permissions to execute this command.";
    public static String LOBBY_NAME = "Lobby";

    public @NotNull Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("buffer_size", BUFFER_SIZE);
        map.put("maximum_username_length", MAX_USERNAME_LENGTH);
        map.put("admin_password", ADMIN_PASSWORD);
        map.put("insufficient_permissions", INSUFFICIENT_PERMS);
        map.put("lobby_name", LOBBY_NAME);
        return map;
    }
}
