package Roulette;

import Roulette.actions.ActionManager;
import Roulette.commands.RouletteCommandManager;
import Roulette.config.ConfigManager;
import Roulette.config.properties.PropertiesManager;
import Roulette.player.PlayerManager;
import Roulette.rooms.RoomManager;

public class Managers {
    public static PropertiesManager propertiesManager;
    public static ConfigManager configManager;
    public static RoomManager roomManager;
    public static RouletteCommandManager rouletteCommandManager;
    public static ActionManager actionManager;
    public static PlayerManager playerManager;

    public static void initialize() {
        propertiesManager = new PropertiesManager();
        configManager = new ConfigManager("config.yml");
        roomManager = new RoomManager();
        rouletteCommandManager = new RouletteCommandManager();
        actionManager = new ActionManager();
        playerManager = new PlayerManager();

        System.out.println("Managers initialized.");
    }
}
