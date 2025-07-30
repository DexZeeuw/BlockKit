package com.blockkit.extras.config;

import com.blockkit.api.config.ConfigFile;
import com.blockkit.core.config.ConfigService;
import com.blockkit.core.config.YamlConfig;
import org.bukkit.plugin.Plugin;

/**
 * DSL for easily creating and registering YAML configuration files.
 */
public class ConfigBuilder {
    private final Plugin plugin;
    private String relativePath;
    private boolean autoSave = true;

    private ConfigBuilder(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Starts building a config for the given plugin.
     *
     * @param plugin the plugin instance
     * @return a new ConfigBuilder instance
     */
    public static ConfigBuilder of(Plugin plugin) {
        return new ConfigBuilder(plugin);
    }

    /**
     * Specifies the file path relative to the plugin's data folder.
     * Can include subfolders, e.g., "presets/2d/preset.yml".
     *
     * @param path the relative path within the data folder
     * @return this builder instance
     */
    public ConfigBuilder filePath(String path) {
        this.relativePath = path;
        return this;
    }

    /**
     * (Deprecated) Alias for filePath(String). Sets the relative file path.
     *
     * @param path the relative path within the data folder
     * @return this builder instance
     */
    @Deprecated
    public ConfigBuilder fileName(String path) {
        return filePath(path);
    }

    /**
     * Determines whether the config is automatically saved on JVM shutdown.
     *
     * @param autoSave true to register a shutdown hook for saving
     * @return this builder instance
     */
    public ConfigBuilder autoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }

    /**
     * Creates a new YamlConfig, registers it with the provided service,
     * and returns the instance.
     *
     * @param service the central ConfigService to register with
     * @return the registered ConfigFile instance
     */
    public ConfigFile register(ConfigService service) {
        YamlConfig cfg = new YamlConfig(plugin, relativePath);
        service.register(cfg);
        if (autoSave) {
            Runtime.getRuntime().addShutdownHook(new Thread(cfg::save));
        }
        return cfg;
    }
}