package me.joseph.core.commands.admin.subcommands;

import me.joseph.core.Core;
import me.joseph.core.commands.CoreCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetLoadedBlocks implements CoreCommand {

    private final Core main;
    public GetLoadedBlocks(Core main) {
        this.main = main;
    }
    @Override
    public String name() {
        return "getloadedblocks";
    }

    @Override
    public int args() {
        return 1;
    }

    @Override
    public String usage() {
        return "/admin getloadedblocks";
    }

    @Override
    public String permission() {
        return "admin.getloadedblocks";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        main.getCoreBlockRetriever().getCoreBlocks().keySet().forEach(type -> main.getCoreLogger().logInfo(main.getCoreBlockRetriever().getCoreBlocks().get(type).toString()));
    }

    @Override
    public void execute(Player sender, String[] args) {
        execute((CommandSender) sender, args);
    }
}
