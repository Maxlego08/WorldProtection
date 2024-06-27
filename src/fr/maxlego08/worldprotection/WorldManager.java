package fr.maxlego08.worldprotection;

import fr.maxlego08.worldprotection.listener.ListenerAdapter;
import fr.maxlego08.worldprotection.save.Config;
import fr.maxlego08.worldprotection.save.PlayerWorlds;
import fr.maxlego08.worldprotection.world.VoidGenerator;
import fr.maxlego08.worldprotection.zcore.enums.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class WorldManager extends ListenerAdapter {

    private final WorldProtectionPlugin plugin;

    public WorldManager(WorldProtectionPlugin plugin) {
        this.plugin = plugin;
    }

    public void createWorld(CommandSender sender, @NotNull OfflinePlayer offlinePlayer) {

        if (getWorld(offlinePlayer).isPresent()) {
            message(sender, Message.CREATE_ERROR_ALREADY);
            return;
        }

        String worldName = offlinePlayer.getName() + "_" + offlinePlayer.getUniqueId().toString().substring(0, 6);
        PlayerWorld world = new PlayerWorld(worldName, offlinePlayer.getUniqueId());
        PlayerWorlds.worlds.add(world);

        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generator(new VoidGenerator());

        World newWorld = Bukkit.createWorld(worldCreator);
        if (newWorld != null) {
            newWorld.getBlockAt(newWorld.getSpawnLocation()).getRelative(BlockFace.DOWN).setType(Material.BEDROCK);
        }

        message(sender, Message.CREATE_CREATE, "%player%", offlinePlayer.getName());

        this.plugin.savePlayers();
    }

    public Optional<PlayerWorld> getWorld(OfflinePlayer offlinePlayer) {
        return PlayerWorlds.worlds.stream().filter(playerWorld -> playerWorld.getUniqueId().equals(offlinePlayer.getUniqueId())).findFirst();
    }

    @Override
    protected void onWorldChange(PlayerChangedWorldEvent event, Player player, World from, World world) {

        String worldName = world.getName();
        if (Config.defaultWorld.equals(worldName)) return; // Default world, do nothing

        if (isNotAllowed(worldName, player)) {
            message(player, Message.WORLD_NO_PERMISSION);
            player.teleport(from.getSpawnLocation());
        }
    }

    private void cancel(Cancellable cancellable, Player player) {
        String worldName = player.getWorld().getName();
        if (isNotAllowed(worldName, player)) {
            message(player, Message.WORLD_NO_PERMISSION);
            cancellable.setCancelled(true);

            World world = Bukkit.getWorld(Config.defaultWorld);
            if (world != null) {
                player.teleport(world.getSpawnLocation());
            }
        }
    }

    @Override
    protected void onBlockPlace(BlockPlaceEvent event, Player player) {
        cancel(event, player);
    }

    @Override
    protected void onBlockBreak(BlockBreakEvent event, Player player) {
        cancel(event, player);
    }

    @Override
    protected void onInteract(PlayerInteractEvent event, Player player) {
        cancel(event, player);
    }

    @Override
    protected void onCommand(PlayerCommandPreprocessEvent event, Player player, String message) {
        cancel(event, player);

        if (event.isCancelled()) return;

        if (Config.bypassPlayers.contains(player.getName())) return;

        try {
            String command = message.toLowerCase().split(" ")[0];

            if (Config.blacklistCommands.contains(command) || command.contains(":")) {
                event.setCancelled(true);
                message(player, Message.COMMAND_NO_PERMISSION);
            }
        } catch (Exception ignored) {
        }
    }

    public List<PlayerWorld> getWorlds(UUID uuid) {
        return PlayerWorlds.worlds.stream().filter(playerWorld -> playerWorld.getUniqueId().equals(uuid) || playerWorld.getAllowedPlayers().contains(uuid)).collect(Collectors.toList());
    }

    public List<String> getAllowedWorlds(CommandSender sender) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        Player player = (Player) sender;
        List<String> worldNames = getWorlds(player.getUniqueId()).stream().map(PlayerWorld::getWorldName).collect(Collectors.toList());
        worldNames.add(Config.defaultWorld);

        return worldNames;
    }

    public boolean isNotAllowed(String worldName, Player player) {
        if (Config.bypassPlayers.contains(player.getName())) return false;
        return !getAllowedWorlds(player).contains(worldName);
    }

    public void teleport(Player player, String world) {

        if (isNotAllowed(world, player)) {
            message(player, Message.WORLD_NO_PERMISSION);
            return;
        }

        World bukkitWorld = Bukkit.getWorld(world);
        if (bukkitWorld == null) {
            message(player, Message.WORLD_DOESNT_EXIST, "%world%", world);
            return;
        }

        player.teleport(bukkitWorld.getSpawnLocation());
    }

    private boolean doestHaveWorld(Player player) {

        Optional<PlayerWorld> optional = plugin.getWorldManager().getWorld(player);
        if (!optional.isPresent()) {
            message(player, Message.WORLD_ERROR);
            return true;
        }

        return false;
    }

    public void addPlayer(Player player, OfflinePlayer offlinePlayer) {

        if (doestHaveWorld(player)) return;

        if (player == offlinePlayer) {
            message(player, Message.WORLD_ADD_YOU, "%player%", offlinePlayer.getName());
            return;
        }

        PlayerWorld playerWorld = plugin.getWorldManager().getWorld(player).get();

        if (playerWorld.contains(offlinePlayer.getUniqueId())) {
            message(player, Message.WORLD_ADD_ERROR, "%player%", offlinePlayer.getName());
            return;
        }

        playerWorld.getAllowedPlayers().add(offlinePlayer.getUniqueId());
        message(player, Message.WORLD_ADD_SUCCESS, "%player%", offlinePlayer.getName());

        this.plugin.savePlayers();
    }

    public void removePlayer(Player player, OfflinePlayer offlinePlayer) {

        if (doestHaveWorld(player)) return;

        PlayerWorld playerWorld = plugin.getWorldManager().getWorld(player).get();

        if (!playerWorld.contains(offlinePlayer.getUniqueId())) {
            message(player, Message.WORLD_REMOVE_ERROR, "%player%", offlinePlayer.getName());
            return;
        }

        playerWorld.getAllowedPlayers().remove(offlinePlayer.getUniqueId());
        message(player, Message.WORLD_REMOVE_SUCCESS, "%player%", offlinePlayer.getName());

        if (offlinePlayer.isOnline()) {
            Player targetPlayer = offlinePlayer.getPlayer();
            if (targetPlayer != null && targetPlayer.getWorld().getName().equals(playerWorld.getWorldName())) {
                World world = Bukkit.getWorld(Config.defaultWorld);
                if (world != null) {
                    player.teleport(world.getSpawnLocation());
                }
            }
        }

        this.plugin.savePlayers();

    }

    public void sendList(Player player) {

        if (doestHaveWorld(player)) return;

        PlayerWorld playerWorld = plugin.getWorldManager().getWorld(player).get();
        if (playerWorld.getAllowedPlayers().isEmpty()) {

            message(player, Message.WORLD_LIST_EMPTY);
        } else {

            message(player, Message.WORLD_LIST, "%players%", toList(playerWorld.getAllowedPlayers().stream().map(Bukkit::getOfflinePlayer).map(OfflinePlayer::getName).collect(Collectors.toList())), "§f", "§7");
        }
    }
}
