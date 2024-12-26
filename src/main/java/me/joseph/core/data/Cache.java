package me.joseph.core.data;

import me.joseph.core.Core;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cache {

    private final Map<UUID, CorePlayer> currentPlayers = new HashMap<>();

    private final Core main;

    public Cache(Core main) {
        this.main = main;
    }

    public Map<UUID, CorePlayer> getCurrentPlayers() {
        return currentPlayers;
    }

    public void saveCache() {
        this.currentPlayers.values().forEach(corePlayer -> main.getPlayerDataDIO().saveToDatabase(corePlayer));
    }

    public CorePlayer getCorePlayer(Player player) {
        return currentPlayers.get(player.getUniqueId());
    }
}
