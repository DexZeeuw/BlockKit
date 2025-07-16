package com.blockkit.config.core;

import com.blockkit.config.api.ConfigFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Beheert meerdere ConfigFile‐instanties, laadt en slaat ze centraal op.
 */
public class ConfigService {
    private final Map<String, ConfigFile> registry = new HashMap<>();

    /**
     * Register a ConfigFile, which will be loaded immediately.
     *
     * @param configFile the config file to register
     */
    public void register(ConfigFile configFile) {
        configFile.load();
        // use filename as key
        String name = ((YamlConfig) configFile).getFileName();
        registry.put(name, configFile);
    }

    /**
     * Retrieve a previously registered config by its filename.
     *
     * @param fileName the name of the YAML file
     * @return the ConfigFile instance, or null if not found
     */
    public ConfigFile get(String fileName) {
        return registry.get(fileName);
    }
}
