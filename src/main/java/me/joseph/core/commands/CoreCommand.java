package me.joseph.core.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface CoreCommand {

    String name();
    int args();
    String usage();

    String permission();

    void execute(CommandSender sender, String[] args);
    void execute(Player sender, String[] args);
}
