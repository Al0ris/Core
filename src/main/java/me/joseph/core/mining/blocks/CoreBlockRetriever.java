package me.joseph.core.mining.blocks;

import me.joseph.core.Core;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CoreBlockRetriever {

    private final Core main;
    private final YamlConfiguration config;
    private final Map<Material, CoreBlock> coreBlocks;

    public CoreBlockRetriever(Core main) {
        this.main = main;
        this.coreBlocks = new HashMap<>();
        File file = new File(main.getDataFolder(),"blocks.yml");
        if (!file.exists()) {
            main.saveResource("blocks.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);

        getBlocks();
    }


    private void getBlocks() {
        for (String section : config.getKeys(false)) {
            main.getCoreLogger().logInfo(section);

            if (!config.isConfigurationSection(section)) continue;
            Material type = Material.getMaterial(section);
            double xp = config.getDouble(section + ".xp");
            int respawnTime = config.getInt(section + ".respawnTime");
            int requiredLevel = config.getInt(section + ".requiredLevel");
            int hardness = config.getInt(section + ".hardness");
            int minToolTierRequired = config.getInt(section + ".minimumToolTierRequired");

            HashMap<Material, Integer> drops = new HashMap<>();
            for (String drop : config.getConfigurationSection(section + ".drops").getKeys(false)) {
                drops.put(Material.getMaterial(drop), config.getInt(section + ".drops" + drop));
            }
            coreBlocks.put(type, new CoreBlock(type, hardness, xp, requiredLevel, respawnTime, minToolTierRequired, drops));
        }
    }

    public Map<Material, CoreBlock> getCoreBlocks() {
        return coreBlocks;
    }
}
