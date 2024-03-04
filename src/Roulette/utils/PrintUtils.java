package Roulette.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PrintUtils {

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RuntimeException throwRunTimeException(String message, Exception e) {
        return new RuntimeException(message + " | " + e);
    }
}
