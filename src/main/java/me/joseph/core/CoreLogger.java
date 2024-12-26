package me.joseph.core;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

public class CoreLogger {
    public void logInfo(String message) {
        Component component = Component.text("[INFO] ", NamedTextColor.GREEN)
                        .append(Component.text(message, NamedTextColor.WHITE));
        log(component);
    }
    public void logWarning(String message) {
        Component component = Component.text("[WARNING] ", NamedTextColor.GOLD)
                .append(Component.text(message, NamedTextColor.WHITE));
        log(component);
    }
    public void logError(String message) {
        Component component = Component.text("[ERROR] ", NamedTextColor.RED)
                .append(Component.text(message, NamedTextColor.WHITE));
        log(component);
    }

    private void log(Component message) {
        Bukkit.getServer().getConsoleSender().sendMessage(message);
    }
}
