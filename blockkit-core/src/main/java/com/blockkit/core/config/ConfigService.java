package com.blockkit.core.config;

import com.blockkit.api.config.ConfigFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages multiple ConfigFile instances, loading and saving them centrally.
 */
public class ConfigService {
    private final Map<String, ConfigFile> registry = new HashMap<>();

    /**
     * Registers a ConfigFile, which is loaded immediately.
     *
     * @param configFile the configuration file to register
     */
    public void register(ConfigFile configFile) {
        configFile.load();
        // Use the relative path (including subfolders) as the key
        String key = configFile.getFileName();
        registry.put(key, configFile);
    }

    /**
     * Retrieves a previously registered configuration by its relative path.
     *
     * @param relativePath the relative path of the YAML file,
     *                     e.g., "config.yml" or "presets/2d/preset.yml"
     * @return the corresponding ConfigFile instance, or null if not registered
     */
    public ConfigFile get(String relativePath) {
        return registry.get(relativePath);
    }
}
