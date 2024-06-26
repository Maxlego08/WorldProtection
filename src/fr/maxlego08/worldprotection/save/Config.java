package fr.maxlego08.worldprotection.save;

import fr.maxlego08.worldprotection.zcore.utils.storage.Persist;
import fr.maxlego08.worldprotection.zcore.utils.storage.Savable;

import java.util.Arrays;
import java.util.List;

public class Config implements Savable {

    public static boolean enableDebug = true;
    public static boolean enableDebugTime = false;

    public static List<String> bypassPlayers = Arrays.asList("Maxlego08", "SMV_FR"); // Bypass player's name
    public static List<String> blacklistCommands = Arrays.asList("op", "deop", "/calc", "/solve");

    public static String defaultWorld = "world"; // Default world name

    /**
     * static Singleton instance.
     */
    private static volatile Config instance;


    /**
     * Private constructor for singleton.
     */
    private Config() {
    }

    /**
     * Return a singleton instance of Config.
     */
    public static Config getInstance() {
        // Double lock for thread safety.
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    public void save(Persist persist) {
        persist.save(getInstance());
    }

    public void load(Persist persist) {
        persist.loadOrSaveDefault(getInstance(), Config.class);
    }

}
