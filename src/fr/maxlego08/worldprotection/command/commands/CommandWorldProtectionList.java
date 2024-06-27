package fr.maxlego08.worldprotection.command.commands;

import fr.maxlego08.worldprotection.WorldProtectionPlugin;
import fr.maxlego08.worldprotection.command.VCommand;
import fr.maxlego08.worldprotection.zcore.enums.Message;
import fr.maxlego08.worldprotection.zcore.enums.Permission;
import fr.maxlego08.worldprotection.zcore.utils.commands.CommandType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommandWorldProtectionList extends VCommand {

    public CommandWorldProtectionList(WorldProtectionPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.WORLDPROTECTION_LIST);
        this.setDescription(Message.DESCRIPTION_LIST);
        this.addSubCommand("list");
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(WorldProtectionPlugin plugin) {

        plugin.getWorldManager().sendList(this.player);

        return CommandType.SUCCESS;
    }

}
