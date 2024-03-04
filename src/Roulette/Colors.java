package Roulette;

public enum Colors {
    RESET("\033[0m"),

    GRAY("\033[38;5;242m"),
    RED("\033[38;5;9m"),
    GREEN("\033[38;5;34m"),
    YELLOW("\033[38;5;11m"),
    CYAN("\033[38;5;12m"),
    GOLD("\033[38;5;172m"),
    PINK("\033[38;5;13m"),
    BRIGHT_YELLOW("\033[38;5;191m"),

    ITALIC("\033[3m");

    public final String ansiCode;

    Colors(String ansiCode) {
        this.ansiCode = ansiCode;
    }
}