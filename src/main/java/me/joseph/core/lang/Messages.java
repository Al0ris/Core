package me.joseph.core.lang;

import me.joseph.core.Core;
import me.joseph.core.Utils;

public class Messages {

    // COMMANDS
    private final String NO_PERMISSION;
    private final String INVALID_SYNTAX;
    private final String NO_PLAYER_FOUND;

    //GAMEPLAY
    private final String TOOL_NOT_GOOD_ENOUGH;
    private final String LEVEL_NOT_HIGH_ENOUGH;

    public Messages(Core main) {
        NO_PERMISSION = Utils.colorize(main.getRetriever().getMessage("Commands.noPermission"));
        INVALID_SYNTAX = Utils.colorize(main.getRetriever().getMessage("Commands.invalidSyntax"));
        NO_PLAYER_FOUND = Utils.colorize(main.getRetriever().getMessage("Commands.playerNotFound"));
        TOOL_NOT_GOOD_ENOUGH = Utils.colorize(main.getRetriever().getMessage("Gameplay.toolNotGoodEnough"));
        LEVEL_NOT_HIGH_ENOUGH = Utils.colorize(main.getRetriever().getMessage("Gameplay.levelNotHighEnough"));
    }

    public String getNO_PERMISSION() {
        return NO_PERMISSION;
    }

    public String getINVALID_SYNTAX() {
        return INVALID_SYNTAX;
    }

    public String getNO_PLAYER_FOUND() {
        return NO_PLAYER_FOUND;
    }

    public String getTOOL_NOT_GOOD_ENOUGH() {
        return TOOL_NOT_GOOD_ENOUGH;
    }

    public String getLEVEL_NOT_HIGH_ENOUGH() {
        return LEVEL_NOT_HIGH_ENOUGH;
    }
}
