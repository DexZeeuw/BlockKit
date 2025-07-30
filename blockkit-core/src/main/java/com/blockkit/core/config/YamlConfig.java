package com.blockkit.core.config;

import com.blockkit.api.config.ConfigFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Core implementation of ConfigFile for YAML files.
 */
public class YamlConfig implements ConfigFile {
    private final Plugin plugin;
    private final String relativePath;
    private File file;
    private YamlConfiguration config;

    /**
     * Constructs a YamlConfig for a file within the plugin's data folder.
     *
     * @param plugin       the owning plugin instance
     * @param relativePath the file path relative to the data folder
     *                     (e.g., "config.yml" or "presets/2d/preset.yml")
     */
    public YamlConfig(Plugin plugin, String relativePath) {
        this.plugin = plugin;
        this.relativePath = relativePath;
        this.file = new File(plugin.getDataFolder(), relativePath);
    }

    /**
     * Loads or reloads the YAML configuration from disk.
     * Creates parent directories if they do not exist.
     * If the file does not exist, an empty file is created.
     */
    @Override
    public void load() {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO: log an error or handle failure
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Saves the current configuration state to disk.
     * If the configuration is not loaded, this method does nothing.
     */
    @Override
    public void save() {
        if (config == null) {
            return;
        }
        try {
            config.save(file);
        } catch (IOException e) {
            // TODO: log save failure
        }
    }

    /**
     * Returns the loaded FileConfiguration instance.
     *
     * @return the Bukkit FileConfiguration representing the YAML contents
     */
    @Override
    public FileConfiguration getConfiguration() {
        return config;
    }

    /**
     * Returns the relative file path used as the registry key.
     *
     * @return the relative path including subfolders and ".yml"
     */
    @Override
    public String getFileName() {
        return relativePath;
    }
}