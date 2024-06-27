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

public class CommandWorldProtectionRemove extends VCommand {

    public CommandWorldProtectionRemove(WorldProtectionPlugin plugin) {
        super(plugin);
        this.setPermission(Permission.WORLDPROTECTION_REMOVE);
        this.setDescription(Message.DESCRIPTION_REMOVE);
        this.addSubCommand("remove");
        this.addRequireArg("player", (a, b) -> Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).filter(Objects::nonNull).collect(Collectors.toList()));
        this.onlyPlayers();
    }

    @Override
    protected CommandType perform(WorldProtectionPlugin plugin) {

        OfflinePlayer offlinePlayer = this.argAsOfflinePlayer(0);
        plugin.getWorldManager().removePlayer(player, offlinePlayer);

        return CommandType.SUCCESS;
    }

}
