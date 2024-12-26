package me.joseph.core.mining;

import me.joseph.core.Core;
import me.joseph.core.events.custom.XpGainEvent;
import me.joseph.core.mining.blocks.CoreBlock;
import me.joseph.core.mining.blocks.CoreBlockRetriever;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiningManager {

    private final Core main;

    private final Map<UUID, Location> currentlyMining;
    private final Map<UUID, Integer> currentDamage;
    private final Map<UUID, BukkitTask> miningProgress;
    private final Map<UUID, Long> lastUpdateTime;

    public MiningManager(Core main) {
        this.main = main;
        this.currentlyMining = new HashMap<>();
        this.currentDamage = new HashMap<>();
        this.miningProgress = new HashMap<>();
        this.lastUpdateTime = new HashMap<>();
    }

    public boolean isMining(Player player) {
        return currentlyMining.containsKey(player.getUniqueId());
    }

    /*
    @param player: The player breaking the block
    @param block: the block being broken

    * Adds the player to the various maps required to store the data for handling mining,
    * Then initiates the mining task
     */
    public void startMining(Player player, Block block) {
        if (!canMine(player, block)) return;
        UUID uuid = player.getUniqueId();
        currentlyMining.put(uuid, block.getLocation());
        currentDamage.put(uuid, 0);
        lastUpdateTime.put(uuid, System.currentTimeMillis());

        mine(player);
    }
    /*
    @param player: the player who aborted mining

    * Cancels the ask they were running for mining
    * then removes them from the various maps required for mining and resets their block damage so that
    * Ghost damage isn't left on the block
     */
    public void abort(Player player) {
        UUID uuid = player.getUniqueId();
        miningProgress.get(uuid).cancel();
        player.sendBlockDamage(currentlyMining.get(uuid), 0, -1);
        currentlyMining.remove(uuid);
        currentDamage.remove(uuid);
    }

    /*
    @param player: The player who broke the block

    * Cancels the mining event so that a random task is not being repeated for no reason
    * Replicates the vanilla block break effect without using it
     */
    public void successfulBreak(Player player) {
        UUID uuid = player.getUniqueId();
        miningProgress.get(uuid).cancel();
        Block block = currentlyMining.get(uuid).getBlock();
        if (isCoreBlock(block.getType())) {
            // HANDLE
            CoreBlock coreBlock = getCoreBlock(block.getType());
            Bukkit.getPluginManager().callEvent(new XpGainEvent(player, coreBlock.getXp()));
            coreBlock.getDrops().keySet().forEach(material -> block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(material, 1)));
            return;
        } else {
            block.getDrops().forEach(drop -> block.getWorld().dropItemNaturally(block.getLocation(), drop));
        }
        player.playSound(player,block.getBlockSoundGroup().getBreakSound(), 1, 1);
        block.setType(Material.AIR);
    }

    /*
    @param player: the player mining

    * Calculates the players mining speed based on the block, the tool, the efficiency level, and the players level
    * Starts a task that runs repeatedly that first checks if the current time is past the players calculated mining speed
    * ticks the players block damage and sends it
     */
    public void mine(Player player) {
        UUID uuid = player.getUniqueId();
        int miningSpeed = (int) main.getSpeedCalculator().calculate(player, currentlyMining.get(uuid).getBlock());
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(main, () -> {
            if (System.currentTimeMillis() >= (lastUpdateTime.get(uuid) + miningSpeed)) {
                lastUpdateTime.put(uuid, System.currentTimeMillis());
                int currentProgress = currentDamage.get(uuid);
                currentDamage.put(uuid, currentProgress + 1);
                if ((currentProgress + 1) >= 10) {
                    Bukkit.getPluginManager().callEvent(new BlockBreakEvent(currentlyMining.get(uuid).getBlock(),player));
                    return;
                }
                float convertedCurrentProgress = (float) currentProgress / 10;
                float newProgress = (float) (convertedCurrentProgress + 0.1);
                main.getCoreLogger().logInfo("Current progress: " + newProgress);
                player.sendBlockDamage(currentlyMining.get(uuid), newProgress, -1);
            }
        }, 0, 0);
        miningProgress.put(player.getUniqueId(), task);
    }

    /*
    @param player: the player attempting to break a block
    @param block: the block attempted to be broken

    * Checks if the block is one of the specialty blocks
    * if it is it checks the players level and pickaxe in use to see if the player can break it
    * if not a specialty block it returns true
     */
    private boolean canMine(Player player, Block block) {
        for (Material type : main.getCoreBlockRetriever().getCoreBlocks().keySet()) {
            if (!block.getType().equals(type)) continue;
            CoreBlock coreBlock = main.getCoreBlockRetriever().getCoreBlocks().get(block.getType());
            if (coreBlock.getMinimumToolTierRequired() > getToolTier(player)) {
                player.sendMessage(main.getMessages().getTOOL_NOT_GOOD_ENOUGH());
                return false;
            }
            if (coreBlock.getRequiredlevel() > main.getCache().getCorePlayer(player).getLevel()) {
                player.sendMessage(main.getMessages().getLEVEL_NOT_HIGH_ENOUGH());
                return false;
            }
            return true;
        }

        return true;
    }

    /*
    @param player: the player to be checked

    * Loops through all tools and returns their tier
     */
    private int getToolTier(Player player) {
        for(ToolTier tier : ToolTier.values()) {
            if (!tier.getType().equals(player.getInventory().getItemInMainHand().getType())) continue;
            return tier.getTier();
        }
        return 0;
    }
    private boolean isCoreBlock(Material material) {
        for (Material type : main.getCoreBlockRetriever().getCoreBlocks().keySet()) {
            if (!type.equals(material)) continue;
            return true;
        }
        return false;
    }

    private CoreBlock getCoreBlock(Material material) {
        if (!isCoreBlock(material)) return null;
        return main.getCoreBlockRetriever().getCoreBlocks().get(material);
    }
}
