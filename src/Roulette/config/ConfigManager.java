package Roulette.config;

import Roulette.Managers;
import Roulette.config.properties.GameProperties;
import Roulette.config.properties.GeneralChattingProperties;
import Roulette.config.properties.IOHandlingProperties;
import Roulette.config.properties.ServerSocketProperties;
import Roulette.config.properties.ranks.*;
import Roulette.player.Permissions;
import Roulette.utils.PrintUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ConfigManager {
    private final Path path;
    private final AsynchronousFileChannel fileChannel;

    public ConfigManager(String path) {
        this.path = Paths.get(path);

        try {
            if (Files.notExists(this.path)) {
                System.out.println("Config not found; Creating " + path);
                Files.createFile(this.path);
                fileChannel = AsynchronousFileChannel.open(this.path, StandardOpenOption.WRITE, StandardOpenOption.READ);

                writeToConfig();
            } else {
                fileChannel = AsynchronousFileChannel.open(this.path, StandardOpenOption.WRITE, StandardOpenOption.READ);
                loadFromConfig();
            }
        } catch (IOException e) {
            throw PrintUtils.throwRunTimeException("Fatal error encountered trying to create " + path, e);
        }
    }

    private void writeToConfig() {
        StringBuilder stringBuilder = new StringBuilder("#" + path + "\n");

        stringBuilder.append(mapToString(Managers.propertiesManager.genMap(), 0))
                .deleteCharAt(stringBuilder.lastIndexOf("\n")).append("#end of ").append(path);
        ByteBuffer byteBuffer = ByteBuffer.wrap(stringBuilder.toString().getBytes());

        fileChannel.write(byteBuffer, 0, byteBuffer, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("Successfully wrote data to " + path);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Failed to write data to " + path);
            }
        });
    }

    public void loadFromConfig() {
        List<String> configLines = readFromConfig();
        List<Permissions> perms = new ArrayList<>();
        String key;
        String value;
        String currentRank = null;
        boolean lastLineWasPerm = false;

        for (String line : configLines) {
            if (line.trim().startsWith("-")) {
                perms.add(Permissions.permissionOf(
                        line.substring(line.indexOf("-")).replaceAll("-", "").trim()));
                lastLineWasPerm = true;
                continue;

            } else if (lastLineWasPerm) {
                key = "load_permission_" + currentRank;
                lastLineWasPerm = false;

                switch (key) {
                    case "load_permission_admin" -> {
                        AdminProperties.ADMIN_PERMISSIONS = new ArrayList<>(perms);
                        perms.clear();
                    }
                    case "load_permission_host" -> {
                        HostProperties.HOST_PERMISSIONS = new ArrayList<>(perms);
                        perms.clear();
                    }
                    case "load_permission_mod" -> {
                        ModProperties.MOD_PERMISSIONS = new ArrayList<>(perms);
                        perms.clear();
                    }
                    case "load_permission_player" -> {
                        PlayerProperties.PLAYER_PERMISSIONS = new ArrayList<>(perms);
                        perms.clear();
                    }
                    case "load_permission_mute" -> {
                        MuteProperties.MUTE_PERMISSIONS = new ArrayList<>(perms);
                        perms.clear();
                    }
                }
            }

            if (line.isBlank() || line.trim().startsWith("#"))
                continue;

            key = line.substring(0, line.indexOf(":")).trim();
            value = line.substring(line.indexOf(":") + 1).trim().replaceAll("'", "");

            if (key.equals("prefix"))
                key = "prefix_" + currentRank;

            if (key.equals("rank_level"))
                key = "rank_level_" + currentRank;

            switch (key) {
                case "buffer_size" -> IOHandlingProperties.BUFFER_SIZE = Integer.parseInt(value);
                case "maximum_username_length" -> IOHandlingProperties.MAX_USERNAME_LENGTH = Integer.parseInt(value);
                case "admin_password" -> IOHandlingProperties.ADMIN_PASSWORD = value;
                case "insufficient_permissions" -> IOHandlingProperties.INSUFFICIENT_PERMS = value;
                case "lobby_name" -> IOHandlingProperties.LOBBY_NAME = value;

                case "port" -> ServerSocketProperties.PORT = Integer.parseInt(value);
                case "hostname" -> ServerSocketProperties.HOSTNAME = value;
                case "welcome_message" -> ServerSocketProperties.WELCOME_MESSAGE = value;
                case "print_connected_players" -> ServerSocketProperties.PRINT_CONNECTED_PLAYERS = Boolean.parseBoolean(value);

                case "message_format" -> GeneralChattingProperties.MESSAGE_FORMAT = value;
                case "server_username" -> GeneralChattingProperties.SERVER_USERNAME = value;
                case "server_prefix" -> GeneralChattingProperties.SERVER_PREFIX = value;
                case "room_key_length" -> GeneralChattingProperties.ROOM_KEY_LENGTH = Integer.parseInt(value);
                case "send_previous_chat" -> GeneralChattingProperties.SEND_PREVIOUS_CHAT = Boolean.parseBoolean(value);

                case "starting_balance" -> GameProperties.STARTING_BALANCE = Integer.parseInt(value);
                case "maximum" -> GameProperties.MAXIMUM = Integer.parseInt(value);
                case "minimum" -> GameProperties.MINIMUM = Integer.parseInt(value);
                case "multiplier" -> GameProperties.MULTIPLIER = Integer.parseInt(value);
                case "numbers_eliminated" -> GameProperties.NUMBERS_ELIMINATED = Integer.parseInt(value);
                case "normal_symbol" -> GameProperties.NORMAL_SYMBOL = value;
                case "random_symbol" -> GameProperties.RANDOM_SYMBOL = value;

                case "admin" -> currentRank = "admin";
                case "prefix_admin" -> AdminProperties.ADMIN_PREFIX = value;
                case "rank_level_admin" -> AdminProperties.ADMIN_RANK_LEVEL = Integer.parseInt(value);

                case "host" -> currentRank = "host";
                case "prefix_host" -> HostProperties.HOST_PREFIX = value;
                case "rank_level_host" -> HostProperties.HOST_RANK_LEVEL = Integer.parseInt(value);

                case "mod" -> currentRank = "mod";
                case "prefix_mod" -> ModProperties.MOD_PREFIX = value;
                case "rank_level_mod" -> ModProperties.MOD_RANK_LEVEL = Integer.parseInt(value);

                case "player" -> currentRank = "player";
                case "prefix_player" -> PlayerProperties.PLAYER_PREFIX = value;
                case "rank_level_player" -> PlayerProperties.PLAYER_RANK_LEVEL = Integer.parseInt(value);

                case "mute" -> currentRank = "mute";
                case "prefix_mute" -> MuteProperties.MUTE_PREFIX = value;
                case "rank_level_mute" -> MuteProperties.MUTE_RANK_LEVEL = Integer.parseInt(value);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private @NotNull String mapToString(@NotNull Map<String, Object> map, int level) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            switch (entry.getValue()) {
                case Map<?, ?> ignored -> stringBuilder.append(" ".repeat(level * 2)).append(entry.getKey())
                        .append(":\n").append(mapToString((Map<String, Object>) entry.getValue(), level + 1))
                        .append("\n");

                case List<?> list -> stringBuilder.append(" ".repeat(level * 2)).append(entry.getKey())
                        .append(":\n").append(listToString((list), level * 3));

                case Integer ignored -> stringBuilder.append(" ".repeat(level * 2)).append(entry.getKey())
                        .append(": ").append(entry.getValue()).append("\n");

                default -> stringBuilder.append(" ".repeat(level * 2)).append(entry.getKey()).append(": ")
                        .append("'").append(entry.getValue()).append("'").append("\n");
            }
        }

        return stringBuilder.toString();
    }

    private @NotNull String listToString(@NotNull List<?> list, int level) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Object element : list)
            stringBuilder.append(" ".repeat(level)).append("- ").append(element).append("\n");

        return stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n")).toString();
    }

    private List<String> readFromConfig() {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw PrintUtils.throwRunTimeException("Fatal error encountered trying to read " + path, e);
        }
    }
}