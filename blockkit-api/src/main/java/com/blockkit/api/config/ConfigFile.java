package com.blockkit.api.config;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents a YAML configuration file that can be loaded, saved, and queried.
 */
public interface ConfigFile {

    /**
     * Loads or reloads this configuration file from disk.
     */
    void load();

    /**
     * Saves this configuration file to disk.
     */
    void save();

    /**
     * Retrieves the underlying Bukkit FileConfiguration.
     *
     * @return the FileConfiguration instance representing this YAML file
     */
    FileConfiguration getConfiguration();

    /**
     * Retrieves the relative path (including .yml) within the plugin data folder.
     *
     * @return the relative file path, e.g., "config.yml" or "presets/2d/preset.yml"
     */
    String getFileName();
}
