package me.joseph.core.events;

import me.joseph.core.Core;
import me.joseph.core.data.CorePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerJoinLeave implements Listener {

    private final Core main;

    public PlayerJoinLeave(Core main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        CorePlayer corePlayer = main.getPlayerDataDIO().getPlayerFromDB(e.getPlayer());
        main.getCache().getCurrentPlayers().put(e.getPlayer().getUniqueId(), corePlayer);

        e.getPlayer().getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(0);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        CorePlayer corePlayer = main.getCache().getCurrentPlayers().get(e.getPlayer().getUniqueId());
        main.getCache().getCurrentPlayers().remove(corePlayer.getUuid());
        main.getPlayerDataDIO().saveToDatabase(corePlayer);
    }
}
