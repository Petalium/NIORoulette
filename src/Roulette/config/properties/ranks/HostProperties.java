package Roulette.config.properties.ranks;

import Roulette.player.Permissions;
import Roulette.utils.ConfigUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HostProperties {
    public static String HOST_PREFIX = "Host";
    public static int HOST_RANK_LEVEL = 3;
    public static List<Permissions> HOST_PERMISSIONS = new ArrayList<>
            (List.of(Permissions.START, Permissions.STOP, Permissions.INHERIT_MOD));

    public @NotNull Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("prefix", HOST_PREFIX);
        map.put("rank_level", HOST_RANK_LEVEL);
        map.put("permissions", ConfigUtils.permissionsByReferenceName(HOST_PERMISSIONS));
        return map;
    }
}
