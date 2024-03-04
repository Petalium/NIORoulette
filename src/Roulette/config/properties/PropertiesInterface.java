package Roulette.config.properties;

import java.util.LinkedHashMap;
import java.util.Map;

public interface PropertiesInterface {
    
    default Map<String, Object> genMap() {
        return new LinkedHashMap<>();
    }
}
