package fr.maxlego08.worldprotection;

import fr.maxlego08.worldprotection.command.commands.CommandWorldProtection;
import fr.maxlego08.worldprotection.placeholder.LocalPlaceholder;
import fr.maxlego08.worldprotection.save.Config;
import fr.maxlego08.worldprotection.save.MessageLoader;
import fr.maxlego08.worldprotection.save.PlayerWorlds;
import fr.maxlego08.worldprotection.world.VoidGenerator;
import fr.maxlego08.worldprotection.zcore.ZPlugin;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

/**
 * System to create your plugins very simply Projet:
 * <a href="https://github.com/Maxlego08/TemplatePlugin">https://github.com/Maxlego08/TemplatePlugin</a>
 *
 * @author Maxlego08
 */
public class WorldProtectionPlugin extends ZPlugin {


    private final WorldManager worldManager = new WorldManager(this);

    @Override
    public void onEnable() {

        LocalPlaceholder placeholder = LocalPlaceholder.getInstance();
        placeholder.setPrefix("worldprotection");

        this.preEnable();

        this.registerCommand("worldprotection", new CommandWorldProtection(this), "wp");
        this.addListener(this.worldManager);

        this.addSave(Config.getInstance());
        this.addSave(new MessageLoader(this));

        this.loadFiles();
        PlayerWorlds.getInstance().load(this.getPersist());

        PlayerWorlds.worlds.forEach(playerWorld -> {
            WorldCreator worldCreator = new WorldCreator(playerWorld.getWorldName());
            worldCreator.generator(new VoidGenerator());

            Bukkit.createWorld(worldCreator);
        });

        this.postEnable();
    }

    @Override
    public void onDisable() {

        this.preDisable();

        this.saveFiles();
        this.savePlayers();

        this.postDisable();
    }

    public void savePlayers() {
        PlayerWorlds.getInstance().save(this.getPersist());
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}

