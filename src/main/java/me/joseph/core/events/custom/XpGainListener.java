package me.joseph.core.events.custom;

import me.joseph.core.Core;
import me.joseph.core.data.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class XpGainListener implements Listener {

    private final Core main;
    public XpGainListener(Core main) {
        this.main = main;
    }

    @EventHandler
    public void onXpGain(XpGainEvent e) {
        Player player = e.getPlayer();
        CorePlayer corePlayer = main.getCache().getCorePlayer(player);
        double newXp = corePlayer.getXp() + e.getXp();
        corePlayer.setXp(newXp);
    }
}
