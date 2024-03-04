package Roulette.player;

import Roulette.Colors;
import Roulette.config.properties.ranks.*;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public enum PlayerRanks {
    ADMIN(AdminProperties.ADMIN_RANK_LEVEL, Colors.RED, AdminProperties.ADMIN_PREFIX, AdminProperties.ADMIN_PERMISSIONS),
    HOST(HostProperties.HOST_RANK_LEVEL, Colors.GOLD, HostProperties.HOST_PREFIX, HostProperties.HOST_PERMISSIONS),
    MOD(ModProperties.MOD_RANK_LEVEL, Colors.GREEN, ModProperties.MOD_PREFIX, ModProperties.MOD_PERMISSIONS),
    PLAYER(PlayerProperties.PLAYER_RANK_LEVEL, Colors.YELLOW, PlayerProperties.PLAYER_PREFIX, PlayerProperties.PLAYER_PERMISSIONS),
    MUTE(MuteProperties.MUTE_RANK_LEVEL, Colors.GRAY, MuteProperties.MUTE_PREFIX, MuteProperties.MUTE_PERMISSIONS);

    public final int rankLevel;
    public final Colors prefixColor;
    public final String prefix;
    public final List<Permissions> permissions;

    PlayerRanks(int rankLevel, @NotNull Colors prefixColor, String prefix, List<Permissions> permissions) {
        this.rankLevel = rankLevel;
        this.prefixColor = prefixColor;
        this.prefix = prefixColor.ansiCode + prefix + Colors.RESET.ansiCode;    // TODO: Dont hardcode colors
        this.permissions = inheritPermissions(permissions);
    }

    private @NotNull List<Permissions> inheritPermissions(@NotNull List<Permissions> permissions) {
        List<Permissions> newPermissionsList = new LinkedList<>();

        for (Permissions permission : permissions) {
            switch (permission) {
                case Permissions.INHERIT_HOST ->
                    newPermissionsList.addAll(inheritPermissions(HostProperties.HOST_PERMISSIONS));
                case Permissions.INHERIT_MOD ->
                    newPermissionsList.addAll(inheritPermissions(ModProperties.MOD_PERMISSIONS));
                case Permissions.INHERIT_PLAYER ->
                    newPermissionsList.addAll(inheritPermissions(PlayerProperties.PLAYER_PERMISSIONS));
                case Permissions.INHERIT_MUTE ->
                    newPermissionsList.addAll(inheritPermissions(MuteProperties.MUTE_PERMISSIONS));
                default -> newPermissionsList.add(permission);
            }
        }

        return newPermissionsList;
    }
}
