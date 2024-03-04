package Roulette.config.properties.ranks;

import Roulette.config.properties.PropertiesInterface;
import Roulette.player.Permissions;
import Roulette.utils.ConfigUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdminProperties implements PropertiesInterface {
    public static String ADMIN_PREFIX = "Admin";
    public static int ADMIN_RANK_LEVEL = 4;
    public static List<Permissions> ADMIN_PERMISSIONS = new ArrayList<>
            (List.of(Permissions.SHUTDOWN, Permissions.RELOAD, Permissions.ROOMS,
                    Permissions.ROOMSINFO, Permissions.CLOSE, Permissions.INHERIT_HOST));

    public @NotNull Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("prefix", ADMIN_PREFIX);
        map.put("rank_level", ADMIN_RANK_LEVEL);
        map.put("permissions", ConfigUtils.permissionsByReferenceName(ADMIN_PERMISSIONS));
        return map;
    }
}