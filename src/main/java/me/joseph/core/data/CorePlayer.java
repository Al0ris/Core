package me.joseph.core.data;

import org.bson.Document;
import org.bukkit.entity.Player;

import java.rmi.server.UID;
import java.util.UUID;

public class CorePlayer {
    private final UUID uuid;
    private int level;
    private double xp;

    public CorePlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.level = 1;
        this.xp = 0;
    }
    public CorePlayer() {
        this.uuid = UUID.randomUUID();
        this.level = 1;
    }
    public CorePlayer(UUID uuid, int level, double xp) {
        this.uuid = uuid;
        this.level = level;
        this.xp = xp;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public Document toDocument() {
        Document document = new Document();
        document.put("playerUUID", this.getUuid().toString());
        document.put("level", this.getLevel());
        document.put("xp", this.getXp());
        return document;
    }
}
