package fr.maxlego08.worldprotection.command.commands;

import fr.maxlego08.worldprotection.WorldProtectionPlugin;
import fr.maxlego08.worldprotection.command.VCommand;
import fr.maxlego08.worldprotection.zcore.enums.Permission;
import fr.maxlego08.worldprotection.zcore.utils.commands.CommandType;

public class CommandWorldProtection extends VCommand {

	public CommandWorldProtection(WorldProtectionPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.WORLDPROTECTION_USE);
		this.addSubCommand(new CommandWorldProtectionReload(plugin));
		this.addSubCommand(new CommandWorldProtectionCreateWorld(plugin));
		this.addSubCommand(new CommandWorldProtectionTeleport(plugin));
		this.addSubCommand(new CommandWorldProtectionAdd(plugin));
		this.addSubCommand(new CommandWorldProtectionRemove(plugin));
		this.addSubCommand(new CommandWorldProtectionList(plugin));
	}

	@Override
	protected CommandType perform(WorldProtectionPlugin plugin) {
		syntaxMessage();
		return CommandType.SUCCESS;
	}

}
