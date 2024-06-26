package fr.maxlego08.worldprotection.command.commands;

import fr.maxlego08.worldprotection.WorldProtectionPlugin;
import fr.maxlego08.worldprotection.command.VCommand;
import fr.maxlego08.worldprotection.zcore.enums.Message;
import fr.maxlego08.worldprotection.zcore.enums.Permission;
import fr.maxlego08.worldprotection.zcore.utils.commands.CommandType;

public class CommandWorldProtectionCreateTeleport extends VCommand {

    public CommandWorldProtectionCreateTeleport(WorldProtectionPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.WORLDPROTECTION_CREATE);
        this.setDescription(Message.DESCRIPTION_CREATE);
        this.addSubCommand("teleport");
        this.addRequireArg("world", (sender, b) -> plugin.getWorldManager().getAllowedWorlds(sender));
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(WorldProtectionPlugin plugin) {

        String world = this.argAsString(0);
        plugin.getWorldManager().teleport(player, world);

        return CommandType.SUCCESS;
    }

}
