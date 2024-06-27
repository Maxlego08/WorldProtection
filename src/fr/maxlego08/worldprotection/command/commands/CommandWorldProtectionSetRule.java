package fr.maxlego08.worldprotection.command.commands;

import fr.maxlego08.worldprotection.WorldProtectionPlugin;
import fr.maxlego08.worldprotection.command.VCommand;
import fr.maxlego08.worldprotection.rules.Rules;
import fr.maxlego08.worldprotection.rules.WorldRules;
import fr.maxlego08.worldprotection.zcore.enums.Message;
import fr.maxlego08.worldprotection.zcore.enums.Permission;
import fr.maxlego08.worldprotection.zcore.utils.commands.CommandType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandWorldProtectionSetRule extends VCommand {

    public CommandWorldProtectionSetRule(WorldProtectionPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.WORLDPROTECTION_SET_RULE);
        this.setDescription(Message.DESCRIPTION_SET_RULE);
        this.addSubCommand("setrules");
        this.addRequireArg("rules", (a, b) -> Arrays.stream(WorldRules.values()).map(Enum::name).collect(Collectors.toList()));
        this.addRequireArg("action", (a, b) -> Arrays.stream(Rules.values()).map(Enum::name).collect(Collectors.toList()));
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(WorldProtectionPlugin plugin) {

        WorldRules worldRules = WorldRules.valueOf(this.argAsString(0));
        Rules rules = Rules.valueOf(this.argAsString(1));

        plugin.getWorldManager().setWorldRules(player, worldRules, rules);

        return CommandType.SUCCESS;
    }

}
