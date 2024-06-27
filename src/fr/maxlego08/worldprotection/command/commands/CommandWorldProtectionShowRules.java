package fr.maxlego08.worldprotection.command.commands;

import fr.maxlego08.worldprotection.WorldProtectionPlugin;
import fr.maxlego08.worldprotection.command.VCommand;
import fr.maxlego08.worldprotection.zcore.enums.Message;
import fr.maxlego08.worldprotection.zcore.enums.Permission;
import fr.maxlego08.worldprotection.zcore.utils.commands.CommandType;

public class CommandWorldProtectionShowRules extends VCommand {

    public CommandWorldProtectionShowRules(WorldProtectionPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.WORLDPROTECTION_RULES);
        this.setDescription(Message.DESCRIPTION_RULES);
        this.addSubCommand("rules");
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(WorldProtectionPlugin plugin) {

        plugin.getWorldManager().sendRules(player);

        return CommandType.SUCCESS;
    }

}
