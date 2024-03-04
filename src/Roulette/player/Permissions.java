package Roulette.player;

import org.jetbrains.annotations.NotNull;

public enum Permissions {
    CREATE("create"),
    CHAT("chat"),
    HELP("help"),
    ROOMS("rooms"),
    LOBBY("lobby"),
    ROOMSINFO("roomsinfo"),
    CLOSE("close"),
    USERNAME("username"),
    JOIN("join"),
    PROMOTE("promote"),
    DEMOTE("demote"),
    KICK("kick"),
    START("start"),
    STOP("stop"),
    RELOAD("reload"),
    SHUTDOWN("shutdown"),
    INHERIT_MUTE("mute.permissions"),
    INHERIT_PLAYER("player.permissions"),
    INHERIT_MOD("mod.permissions"),
    INHERIT_HOST("host.permissions");

    public final String referenceName;

    Permissions(String referenceName) {
        this.referenceName = referenceName;
    }

    public static Permissions permissionOf(@NotNull String permission) {
        switch (permission) {
            case "mute.permissions" -> {
                return INHERIT_MUTE;
            }
            case "player.permissions" -> {
                return INHERIT_PLAYER;
            }
            case "mod.permissions" -> {
                return INHERIT_MOD;
            }
            case "host.permissions" -> {
                return INHERIT_HOST;
            }
            default -> {
                return Permissions.valueOf(permission.toUpperCase());
            }
        }
    }
}