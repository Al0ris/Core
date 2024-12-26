package me.joseph.core.commands.admin;

import me.joseph.core.Core;
import me.joseph.core.commands.CoreBaseCommand;
import me.joseph.core.commands.admin.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdminBaseCommand extends CoreBaseCommand implements TabCompleter {

    public AdminBaseCommand(Core main) {
        super(main);
        this.subCommands.add(new SetLevel(main));
        this.subCommands.add(new GetLevel(main));
        this.subCommands.add(new GetXp(main));
        this.subCommands.add(new SetXp(main));
        this.subCommands.add(new GetLoadedBlocks(main));

        main.getCommand("admin").setTabCompleter(this);
    }

    @Override
    public String name() {
        return "admin";
    }

    @Override
    public int args() {
        return -1;
    }

    @Override
    public String usage() {
        return "/admin <command>";
    }

    @Override
    public String permission() {
        return "admin";
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            List<String> subCommandNames = new ArrayList<>();
            subCommands.forEach(subCommand -> subCommandNames.add(subCommand.name()));
            return subCommandNames;
        }
        return null;
    }
}
