package me.joseph.core.commands.admin.subcommands;

import me.joseph.core.Core;
import me.joseph.core.Utils;
import me.joseph.core.commands.CoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetXp implements CoreCommand {

    private final Core main;

    public SetXp(Core main) {
        this.main = main;
    }
    @Override
    public String name() {
        return "setxp";
    }

    @Override
    public int args() {
        return 3;
    }

    @Override
    public String usage() {
        return "/admin setxp <player> <level>";
    }

    @Override
    public String permission() {
        return "admin.setxp";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(main.getMessages().getNO_PLAYER_FOUND() + args[1]);
            return;
        }
        main.getCache().getCorePlayer(player).setXp(Double.parseDouble(args[2]));
        sender.sendMessage(Utils.colorize("&a&l" + player.getName() + "'s Xp has been set to: " + "&6&l" + args[2]));
    }

    @Override
    public void execute(Player sender, String[] args) {
        execute((CommandSender) sender, args);
    }
}
