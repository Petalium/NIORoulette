package Roulette.config.properties.ranks;

import Roulette.player.Permissions;
import Roulette.utils.ConfigUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MuteProperties {
    public static String MUTE_PREFIX = "Mute";
    public static int MUTE_RANK_LEVEL = 0;
    public static List<Permissions> MUTE_PERMISSIONS = new ArrayList<>
            (List.of(Permissions.HELP, Permissions.USERNAME, Permissions.LOBBY));

    public @NotNull Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("prefix", MUTE_PREFIX);
        map.put("rank_level", MUTE_RANK_LEVEL);
        map.put("permissions", ConfigUtils.permissionsByReferenceName(MUTE_PERMISSIONS));
        return map;
    }
}
