package com.blockkit.config.api;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents a YAML configuration file that can be loaded, saved, and queried.
 */
public interface ConfigFile {

    /** Laadt of herlaadt dit config‐bestand */
    void load();

    /** Slaat het bestand op schijf */
    void save();

    /** Geeft de Bukkit‐wrapper terug */
    FileConfiguration getConfiguration();

    /** Haalt de bestandsnaam (incl. .yml) op */
    String getFileName();
}
