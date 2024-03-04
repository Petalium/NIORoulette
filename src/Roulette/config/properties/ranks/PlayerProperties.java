package Roulette.config.properties.ranks;

import Roulette.player.Permissions;
import Roulette.utils.ConfigUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlayerProperties {
    public static String PLAYER_PREFIX = "Player";
    public static int PLAYER_RANK_LEVEL = 1;
    public static List<Permissions> PLAYER_PERMISSIONS = new ArrayList<>
            (List.of(Permissions.CHAT, Permissions.CREATE, Permissions.JOIN, Permissions.INHERIT_MUTE));

    public @NotNull Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("prefix", PLAYER_PREFIX);
        map.put("rank_level", PLAYER_RANK_LEVEL);
        map.put("permissions", ConfigUtils.permissionsByReferenceName(PLAYER_PERMISSIONS));
        return map;
    }
}
