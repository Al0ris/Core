package me.joseph.core.mining;

import org.bukkit.Material;

public enum ToolTier {

    WOODEN_PICKAXE(1), STONE_PICKAXE(2), GOLDEN_PICKAXE(3), IRON_PICKAXE(4), DIAMOND_PICKAXE(5), NETHERITE_PICKAXE(6);

    final int tier;

    ToolTier(int tier){
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    public Material getType() {
        return Material.getMaterial(this.name());
    }
}
