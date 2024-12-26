package me.joseph.core;

import me.joseph.core.commands.CoreCommandExecutor;
import me.joseph.core.commands.admin.AdminBaseCommand;
import me.joseph.core.data.Cache;
import me.joseph.core.data.ConnectionManager;
import me.joseph.core.data.PlayerDataDIO;
import me.joseph.core.events.MiningEvents;
import me.joseph.core.events.PlayerJoinLeave;
import me.joseph.core.events.custom.XpGainListener;
import me.joseph.core.lang.Messages;
import me.joseph.core.lang.Retriever;
import me.joseph.core.mining.MiningManager;
import me.joseph.core.mining.SpeedCalculator;
import me.joseph.core.mining.blocks.CoreBlockRetriever;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class Core extends JavaPlugin {

    private final CoreLogger logger = new CoreLogger();
    private PlayerDataDIO playerDataDIO;
    private ConnectionManager connectionManager;
    private Cache cache;
    private CoreCommandExecutor coreCommandExecutor;
    private Retriever retriever;
    private Messages messages;

    private MiningManager miningManager;
    private SpeedCalculator speedCalculator;
    private CoreBlockRetriever coreBlockRetriever;

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            saveResource("config.yml", false);
        }
        this.connectionManager = new ConnectionManager(this);
        connectionManager.connect();
        this.playerDataDIO = new PlayerDataDIO(connectionManager);
        this.cache = new Cache(this);
        this.coreCommandExecutor = new CoreCommandExecutor(this);
        this.retriever = new Retriever(this);
        this.messages = new Messages(this);

        this.miningManager = new MiningManager(this);
        this.speedCalculator = new SpeedCalculator(this);
        this.coreBlockRetriever = new CoreBlockRetriever(this);

        registerEvents();
        registerCommands();

        logger.logInfo("Startup Successful");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getCache().saveCache();
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinLeave(this), this);
        getServer().getPluginManager().registerEvents(new MiningEvents(this), this);
        getServer().getPluginManager().registerEvents(new XpGainListener(this), this);
    }
    public void registerCommands() {
        coreCommandExecutor.register(new AdminBaseCommand(this));
    }

    @NotNull
    public CoreLogger getCoreLogger() {
        return logger;
    }

    @NotNull
    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public PlayerDataDIO getPlayerDataDIO() {
        return playerDataDIO;
    }

    public Cache getCache() {
        return cache;
    }

    public Retriever getRetriever() {
        return retriever;
    }

    public Messages getMessages() {
        return messages;
    }

    public MiningManager getMiningManager() {
        return miningManager;
    }

    public SpeedCalculator getSpeedCalculator() {
        return speedCalculator;
    }

    public CoreBlockRetriever getCoreBlockRetriever() {
        return coreBlockRetriever;
    }
}
