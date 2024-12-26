package me.joseph.core.commands;

import me.joseph.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoreCommandExecutor implements CommandExecutor {

    private final List<CoreCommand> coreCommands = new ArrayList<>();

    private final Core main;

    public CoreCommandExecutor(Core main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        for (CoreCommand coreCommand : coreCommands) {
            if (!coreCommand.name().equalsIgnoreCase(command.getName())) continue;

            if (!commandSender.hasPermission(coreCommand.permission())) {
                commandSender.sendMessage(main.getMessages().getNO_PERMISSION());
                return false;
            }

            if (strings.length != coreCommand.args() && coreCommand.args() != -1) {
                commandSender.sendMessage(main.getMessages().getINVALID_SYNTAX() + coreCommand.usage());
                return false;
            }
            if (commandSender instanceof Player player) {
                coreCommand.execute(player, strings);
                return false;
            }
            coreCommand.execute(commandSender, strings);
        }
        return false;
    }

    public void register(@NotNull CoreCommand coreCommand) {
        for (CoreCommand com : coreCommands) {
            if (com == coreCommand) {
                return;
            }
        }

        coreCommands.add(coreCommand);
        main.getCommand(coreCommand.name()).setExecutor(this);
    }
}
