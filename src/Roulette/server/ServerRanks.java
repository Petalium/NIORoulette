package Roulette.server;

import Roulette.Colors;
import Roulette.config.properties.GeneralChattingProperties;

public enum ServerRanks {
    SERVER(GeneralChattingProperties.SERVER_PREFIX, GeneralChattingProperties.SERVER_USERNAME),
    SERVER_DIRECT(GeneralChattingProperties.SERVER_PREFIX + "->You", GeneralChattingProperties.SERVER_USERNAME);

    public final String prefix;
    public final String username;

    ServerRanks(String prefix, String username) {
        this.prefix = Colors.PINK.ansiCode + prefix + Colors.RESET.ansiCode;
        this.username = Colors.PINK.ansiCode + Colors.ITALIC.ansiCode + username + Colors.RESET.ansiCode;
    }
}
