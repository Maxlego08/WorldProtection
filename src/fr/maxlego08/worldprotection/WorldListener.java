package fr.maxlego08.worldprotection;

import fr.maxlego08.worldprotection.rules.Rules;
import fr.maxlego08.worldprotection.rules.WorldRules;
import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.world.StructureGrowEvent;

import java.util.Optional;

public class WorldListener implements Listener {

    private final WorldManager worldManager;

    public WorldListener(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        cancelEvent(event, event.getEntity().getWorld(), WorldRules.EXPLOSION);
    }

    @EventHandler
    public void onExplode(BlockExplodeEvent event) {
        cancelEvent(event, event.getBlock().getWorld(), WorldRules.EXPLOSION);
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        cancelEvent(event, event.getBlock().getWorld(), WorldRules.BLOCK_PHYSIC);
    }

    @EventHandler
    public void onBlock(BlockIgniteEvent event) {
        cancelEvent(event, event.getBlock().getWorld(), WorldRules.BLOCK_BURN);
    }

    @EventHandler
    public void onBlock(BlockBurnEvent event) {
        cancelEvent(event, event.getBlock().getWorld(), WorldRules.BLOCK_BURN);
    }

    @EventHandler
    public void onLeave(LeavesDecayEvent event) {
        cancelEvent(event, event.getBlock().getWorld(), WorldRules.LEAVES_DECAY);
    }

    @EventHandler
    public void onGrow(StructureGrowEvent event) {
        cancelEvent(event, event.getWorld(), WorldRules.STRUCTURE_GROW);
    }

    private void cancelEvent(Cancellable cancellable, World world, WorldRules worldRules) {
        Optional<PlayerWorld> optional = worldManager.getWorld(world);
        if (optional.isPresent()) {
            PlayerWorld playerWorld = optional.get();
            if (playerWorld.getRule(worldRules) == Rules.DENIED) {
                cancellable.setCancelled(true);
            }
        }
    }
}
