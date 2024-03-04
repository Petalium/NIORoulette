package Roulette.utils;

import Roulette.Colors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PrintUtils {
    public static void printClientConnected(String message) {

    }

    public static void printClientTerminated(String message) {

    }

    public static void printCommandSuccessful(String message) {

    }

    public static void printActionSuccessful(String message) {

    }

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull RuntimeException throwRunTimeException(String message, Exception e) {
        return new RuntimeException(message + " | " + e);
    }
}
