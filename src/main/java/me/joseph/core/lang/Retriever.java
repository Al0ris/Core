package me.joseph.core.lang;

import me.joseph.core.Core;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Retriever {

    private final YamlConfiguration config;

    public Retriever(Core main) {
        File file = new File(main.getDataFolder(),"lang.yml");
        if (!file.exists()) {
            main.saveResource("lang.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public String getMessage(String path) {
        return config.getString(path);
    }
}
