package com.blockkit.config.api;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents a YAML configuration file that can be loaded, saved, and queried.
 */
public interface ConfigFile {

    /**
     * Load the configuration from disk (or create defaults).
     */
    void load();

    /**
     * Save the current in-memory state back to disk.
     */
    void save();

    /**
     * Get the underlying FileConfiguration for queries.
     *
     * @return the Bukkit FileConfiguration instance
     */
    FileConfiguration getConfiguration();
}
