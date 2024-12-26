package me.joseph.core.commands.admin.subcommands;

import me.joseph.core.Core;
import me.joseph.core.Utils;
import me.joseph.core.commands.CoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetXp implements CoreCommand {

    private final Core main;

    public GetXp(Core main) {
        this.main = main;
    }
    @Override
    public String name() {
        return "getxp";
    }

    @Override
    public int args() {
        return 2;
    }

    @Override
    public String usage() {
        return "/admin getxp <player>";
    }

    @Override
    public String permission() {
        return "admin.getxp";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage("Could not find player (" + args[1] + ")");
            return;
        }
        double xp = main.getCache().getCorePlayer(player).getXp();
        sender.sendMessage(Utils.colorize("&a&l" + player.getName() + "'s Xp is: " + "&6&l" + xp));
    }

    @Override
    public void execute(Player sender, String[] args) {
        execute((CommandSender) sender, args);
    }
}
