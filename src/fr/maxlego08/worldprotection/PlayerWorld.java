package fr.maxlego08.worldprotection;

import fr.maxlego08.worldprotection.rules.Rules;
import fr.maxlego08.worldprotection.rules.WorldRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerWorld {

    private final String worldName;
    private final UUID uniqueId; // UUID of owner
    private final List<UUID> allowedPlayers = new ArrayList<>();
    private final Map<WorldRules, Rules> rules = new HashMap<>();

    public PlayerWorld(String worldName, UUID uniqueId) {
        this.worldName = worldName;
        this.uniqueId = uniqueId;

        // Default rules
        this.rules.put(WorldRules.BLOCK_PHYSIC, Rules.DENIED);
        this.rules.put(WorldRules.EXPLOSION, Rules.DENIED);
        this.rules.put(WorldRules.LEAVES_DECAY, Rules.DENIED);
        this.rules.put(WorldRules.BLOCK_BURN, Rules.DENIED);
        this.rules.put(WorldRules.STRUCTURE_GROW, Rules.ALLOWED);
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public List<UUID> getAllowedPlayers() {
        return allowedPlayers;
    }

    public String getWorldName() {
        return worldName;
    }

    public boolean contains(UUID uniqueId) {
        return this.allowedPlayers.contains(uniqueId);
    }

    public Map<WorldRules, Rules> getRules() {
        return rules;
    }

    public Rules getRule(WorldRules worldRules) {
        return this.rules.getOrDefault(worldRules, Rules.ALLOWED);
    }
}
