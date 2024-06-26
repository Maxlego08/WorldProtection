package fr.maxlego08.worldprotection.save;

import fr.maxlego08.worldprotection.PlayerWorld;
import fr.maxlego08.worldprotection.zcore.utils.storage.Persist;
import fr.maxlego08.worldprotection.zcore.utils.storage.Savable;

import java.util.ArrayList;
import java.util.List;

public class PlayerWorlds implements Savable {

    public static List<PlayerWorld> worlds = new ArrayList<>();

    /**
     * static Singleton instance.
     */
    private static volatile PlayerWorlds instance;


    /**
     * Private constructor for singleton.
     */
    private PlayerWorlds() {
    }

    /**
     * Return a singleton instance of Config.
     */
    public static PlayerWorlds getInstance() {
        // Double lock for thread safety.
        if (instance == null) {
            synchronized (PlayerWorlds.class) {
                if (instance == null) {
                    instance = new PlayerWorlds();
                }
            }
        }
        return instance;
    }

    public void save(Persist persist) {
        persist.save(getInstance());
    }

    public void load(Persist persist) {
        persist.loadOrSaveDefault(getInstance(), PlayerWorlds.class);
    }

}
