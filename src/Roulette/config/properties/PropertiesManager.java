package Roulette.config.properties;

import Roulette.config.properties.ranks.*;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class PropertiesManager implements PropertiesInterface {
    private Map<String, Object> propertiesMap;
    private final IOHandlingProperties ioHandlingProperties;
    private final ServerSocketProperties serverSocketProperties;
    private final GeneralChattingProperties generalChattingProperties;
    private final GameProperties gameProperties;
    private final AdminProperties adminProperties;
    private final HostProperties hostProperties;
    private final ModProperties modProperties;
    private final PlayerProperties playerProperties;
    private final MuteProperties muteProperties;

    public PropertiesManager() {
        propertiesMap = new LinkedHashMap<>();
        ioHandlingProperties = new IOHandlingProperties();
        serverSocketProperties = new ServerSocketProperties();
        generalChattingProperties = new GeneralChattingProperties();
        gameProperties = new GameProperties();
        adminProperties = new AdminProperties();
        hostProperties = new HostProperties();
        modProperties = new ModProperties();
        playerProperties = new PlayerProperties();
        muteProperties = new MuteProperties();
        propertiesMap = genMap();
    }

    public Map<String, Object> genMap() {
        propertiesMap.put("IOHandling", ioHandlingProperties.genMap());
        propertiesMap.put("ServerSocket", serverSocketProperties.genMap());
        propertiesMap.put("GeneralChatting", generalChattingProperties.genMap());
        propertiesMap.put("Game", gameProperties.genMap());
        propertiesMap.put("Ranks", genRankMap());
        return propertiesMap;
    }

    private @NotNull Map<String, Object> genRankMap() {
        Map<String, Object> ranks = new LinkedHashMap<>();
        ranks.put("admin", adminProperties.genMap());
        ranks.put("host", hostProperties.genMap());
        ranks.put("mod", modProperties.genMap());
        ranks.put("player", playerProperties.genMap());
        ranks.put("mute", muteProperties.genMap());
        return ranks;
    }
}
