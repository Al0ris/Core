package me.joseph.core.mining.blocks;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class CoreBlock {

    private final Material type;
    private final int hardness;
    private final double xp;
    private final int requiredlevel;
    private final int respawnTime;
    private final int minimumToolTierRequired;
    private final Map<Material, Integer> drops;

    public CoreBlock(Material type, int hardness, double xp, int requiredlevel, int respawnTime, int minimumToolTierRequired, HashMap<Material, Integer> drops) {
        this.type = type;
        this.hardness = hardness;
        this.xp = xp;
        this.requiredlevel = requiredlevel;
        this.respawnTime = respawnTime;
        this.minimumToolTierRequired = minimumToolTierRequired;
        this.drops = drops;
    }

    public Material getType() {
        return type;
    }

    public int getHardness() {
        return hardness;
    }

    public double getXp() {
        return xp;
    }

    public int getRequiredlevel() {
        return requiredlevel;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    public int getMinimumToolTierRequired() {
        return minimumToolTierRequired;
    }

    public Map<Material, Integer> getDrops() {
        return drops;
    }

    @Override
    public String toString() {
        return "CoreBlock{" +
                "type=" + type +
                ", hardness=" + hardness +
                ", xp=" + xp +
                ", requiredlevel=" + requiredlevel +
                ", respawnTime=" + respawnTime +
                ", minimumToolTierRequired=" + minimumToolTierRequired +
                ", drops=" + drops +
                '}';
    }
}
