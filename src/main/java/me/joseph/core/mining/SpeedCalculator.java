package me.joseph.core.mining;

import me.joseph.core.Core;
import me.joseph.core.data.CorePlayer;
import me.joseph.core.mining.blocks.CoreBlock;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SpeedCalculator {

    private final Core main;

    private final int PICKAXE_WEIGHT;
    private final int EFFICIENCY_WEIGHT;
    private final int LEVEL_WEIGHT;
    private final int BLOCK_HARDNESS_WEIGHT;

    public SpeedCalculator(Core main) {
        this.main = main;
        PICKAXE_WEIGHT = main.getConfig().getInt("pickaxeWeight");
        EFFICIENCY_WEIGHT = main.getConfig().getInt("efficiencyWeight");
        LEVEL_WEIGHT = main.getConfig().getInt("levelWeight");
        BLOCK_HARDNESS_WEIGHT = main.getConfig().getInt("blockHardnessWeight");
    }
    public long calculate(Player player, Block block) {
        int pickaxeSpeed = 0;
        for (ToolTier tool : ToolTier.values()) {
            if (!player.getInventory().getItemInMainHand().getType().equals(tool.getType())) continue;
            pickaxeSpeed = tool.getTier();
        }
        int efficiencyLevel = 0;

        CorePlayer corePlayer = main.getCache().getCorePlayer(player);

        int pickaxeFactor = pickaxeSpeed * PICKAXE_WEIGHT;
        int efficiencyFactor = efficiencyLevel * EFFICIENCY_WEIGHT;
        int levelFactor = corePlayer.getLevel() * LEVEL_WEIGHT;


        if (main.getCoreBlockRetriever().getCoreBlocks().containsKey(block.getType())) {
            int blockHardness = main.getCoreBlockRetriever().getCoreBlocks().get(block.getType()).getHardness();
            long speed = 350 + blockHardness - pickaxeFactor - efficiencyFactor - levelFactor;
            main.getCoreLogger().logInfo("Speed: " + speed);
            return speed;
        }
        int blockFactor = Math.round(block.getType().getHardness() * 10 * BLOCK_HARDNESS_WEIGHT);

        long speed = 350 + blockFactor - pickaxeFactor - efficiencyFactor - levelFactor;
        main.getCoreLogger().logInfo("Speed: " + speed);
        return speed;
    }
}
