package fr.maxlego08.worldprotection.command.commands;

import fr.maxlego08.worldprotection.WorldProtectionPlugin;
import fr.maxlego08.worldprotection.command.VCommand;
import fr.maxlego08.worldprotection.zcore.enums.Message;
import fr.maxlego08.worldprotection.zcore.enums.Permission;
import fr.maxlego08.worldprotection.zcore.utils.commands.CommandType;
import org.bukkit.OfflinePlayer;

public class CommandWorldProtectionCreateWorld extends VCommand {

    public CommandWorldProtectionCreateWorld(WorldProtectionPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.WORLDPROTECTION_CREATE);
        this.setDescription(Message.DESCRIPTION_CREATE);
        this.addSubCommand("create");
        this.addRequireArg("player");
    }

    @Override
    protected CommandType perform(WorldProtectionPlugin plugin) {

        if (!canUse()) return CommandType.DEFAULT;

        OfflinePlayer offlinePlayer = this.argAsOfflinePlayer(0);
        plugin.getWorldManager().createWorld(sender, offlinePlayer);

        return CommandType.SUCCESS;
    }

}
