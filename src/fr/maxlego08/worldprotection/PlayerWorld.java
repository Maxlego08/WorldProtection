package fr.maxlego08.worldprotection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerWorld {

    private final String worldName;
    private final UUID uniqueId; // UUID of owner
    private final List<UUID> allowedPlayers = new ArrayList<>();

    public PlayerWorld(String worldName, UUID uniqueId) {
        this.worldName = worldName;
        this.uniqueId = uniqueId;
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
}
