package Roulette.config.properties;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class GeneralChattingProperties implements PropertiesInterface {
    public static String MESSAGE_FORMAT = "[%s] %p: %m"; //%s = PREFIX | %p = DISPLAY NAME | %m = MESSAGE
    public static String SERVER_USERNAME = "SERVER"; //%b = No username
    public static String SERVER_PREFIX = "@";
    public static int ROOM_KEY_LENGTH = 6;
    public static boolean SEND_PREVIOUS_CHAT = true;

    public @NotNull Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message_format", MESSAGE_FORMAT);
        map.put("server_username", SERVER_USERNAME);
        map.put("server_prefix", SERVER_PREFIX);
        map.put("room_key_length", ROOM_KEY_LENGTH);
        map.put("send_previous_chat", SEND_PREVIOUS_CHAT);
        return map;
    }
}
