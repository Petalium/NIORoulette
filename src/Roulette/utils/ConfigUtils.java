package Roulette.utils;

import Roulette.player.Permissions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConfigUtils {

    public static @NotNull List<String> permissionsByReferenceName(@NotNull List<Permissions> listOfPermissions) {
        List<String> permissionsByName = new ArrayList<>();

        for (Permissions permissions : listOfPermissions)
            permissionsByName.add(permissions.referenceName);

        return permissionsByName;
    }
}
