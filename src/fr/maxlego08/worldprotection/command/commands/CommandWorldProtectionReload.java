package fr.maxlego08.worldprotection.command.commands;

import fr.maxlego08.worldprotection.WorldProtectionPlugin;
import fr.maxlego08.worldprotection.command.VCommand;
import fr.maxlego08.worldprotection.zcore.enums.Message;
import fr.maxlego08.worldprotection.zcore.enums.Permission;
import fr.maxlego08.worldprotection.zcore.utils.commands.CommandType;

public class CommandWorldProtectionReload extends VCommand {

	public CommandWorldProtectionReload(WorldProtectionPlugin plugin) {
		super(plugin);
		this.setPermission(Permission.WORLDPROTECTION_RELOAD);
		this.addSubCommand("reload", "rl");
		this.setDescription(Message.DESCRIPTION_RELOAD);
	}

	@Override
	protected CommandType perform(WorldProtectionPlugin plugin) {
		
		plugin.reloadFiles();
		message(sender, Message.RELOAD);
		
		return CommandType.SUCCESS;
	}

}
