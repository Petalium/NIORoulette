package Roulette.config.properties.ranks;

import Roulette.player.Permissions;
import Roulette.utils.ConfigUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModProperties {
    public static String MOD_PREFIX = "Mod";
    public static int MOD_RANK_LEVEL = 2;
    public static List<Permissions> MOD_PERMISSIONS = new ArrayList<>
            (List.of(Permissions.KICK, Permissions.DEMOTE, Permissions.PROMOTE, Permissions.INHERIT_PLAYER));

    public @NotNull Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("prefix", MOD_PREFIX);
        map.put("rank_level", MOD_RANK_LEVEL);
        map.put("permissions", ConfigUtils.permissionsByReferenceName(MOD_PERMISSIONS));
        return map;
    }
}
