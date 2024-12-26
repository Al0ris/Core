package me.joseph.core.commands;

import me.joseph.core.Core;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CoreBaseCommand implements CoreCommand{

    private final Core main;
    public CoreBaseCommand(Core main) {
        this.main = main;
    }

    public final List<CoreCommand> subCommands = new ArrayList<>();

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(main.getMessages().getINVALID_SYNTAX() + this.usage());
            return;
        }
        for (CoreCommand subCommand : subCommands) {
            if (!subCommand.name().equalsIgnoreCase(args[0])) continue;

            if (!sender.hasPermission(subCommand.permission())) {
                sender.sendMessage(main.getMessages().getNO_PERMISSION());
                return;
            }
            if (args.length != subCommand.args()) {
                sender.sendMessage(main.getMessages().getINVALID_SYNTAX() + subCommand.usage());
                return;
            }
            if (sender instanceof Player player) {
                subCommand.execute(player, args);
                return;
            }
            subCommand.execute(sender, args);
        }
    }

    @Override
    public void execute(Player sender, String[] args) {
        execute((CommandSender) sender, args);
    }
}
