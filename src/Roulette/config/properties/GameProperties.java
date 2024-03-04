package Roulette.config.properties;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameProperties implements PropertiesInterface {
    public static int STARTING_BALANCE = 500;
    public static int MAXIMUM = 10;
    public static int MINIMUM = 1;
    public static int MULTIPLIER = 2;
    public static int NUMBERS_ELIMINATED = 5;
    public static String NORMAL_SYMBOL = "O";
    public static String RANDOM_SYMBOL = "X";

    public Map<String, Object> genMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("starting_balance", STARTING_BALANCE);
        map.put("maximum", MAXIMUM);
        map.put("minimum", MINIMUM);
        map.put("multiplier", MULTIPLIER);
        map.put("numbers_eliminated", NUMBERS_ELIMINATED);
        map.put("normal_symbol", NORMAL_SYMBOL);
        map.put("random_symbol", RANDOM_SYMBOL);
        return map;
    }
}
