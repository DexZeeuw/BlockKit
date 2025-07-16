package com.blockkit.config.core;

import com.blockkit.config.api.ConfigFile;
import com.blockkit.fs.log.FsLogger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Core‐implementatie van ConfigFile voor YAML‐bestanden.
 */
public class YamlConfig implements ConfigFile {
    private final Plugin plugin;
    private final String fileName;
    private File file;
    private YamlConfiguration config;

    public YamlConfig(Plugin plugin, String fileName) {
        this.plugin   = plugin;
        this.fileName = fileName;
        this.file     = new File(plugin.getDataFolder(), fileName);
    }

    @Override
    public void load() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
            FsLogger.debug("Created data folder for " + plugin.getName());
        }
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
            FsLogger.debug("Saved default resource: " + fileName);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void save() {
        if (config == null) {
            FsLogger.error("Cannot save " + fileName + ": not loaded");
            return;
        }
        try {
            config.save(file);
            FsLogger.debug("Saved config: " + fileName);
        } catch (IOException e) {
            FsLogger.error("Failed to save " + fileName + ": " + e.getMessage());
        }
    }

    @Override
    public FileConfiguration getConfiguration() {
        return config;
    }
}
