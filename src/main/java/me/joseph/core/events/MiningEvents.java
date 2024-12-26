package me.joseph.core.events;

import me.joseph.core.Core;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class MiningEvents implements Listener {

    private final Core main;

    public MiningEvents(Core main) {
        this.main = main;
    }

    @EventHandler
    public void blockDamage(BlockDamageEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        main.getMiningManager().startMining(e.getPlayer(), e.getBlock());
    }

    @EventHandler
    public void abortBlockBreak(BlockDamageAbortEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        if (!main.getMiningManager().isMining(e.getPlayer())) return;
        main.getMiningManager().abort(e.getPlayer());
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        main.getMiningManager().successfulBreak(e.getPlayer());
        e.setCancelled(true);
    }

    @EventHandler
    public void milkBucket(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() != Material.MILK_BUCKET) return;
        e.setCancelled(true);
    }
}
