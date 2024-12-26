package me.joseph.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class XpGainEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private final Player player;
    private double xp;

    public XpGainEvent(Player player, double xp) {
        this.player = player;
        this.xp = xp;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }
}
