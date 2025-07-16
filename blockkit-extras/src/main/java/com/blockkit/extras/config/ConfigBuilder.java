package com.blockkit.config.extras;

import com.blockkit.config.api.ConfigFile;
import com.blockkit.core.config.ConfigService;
import com.blockkit.core.config.YamlConfig;
import org.bukkit.plugin.Plugin;

/**
 * DSL voor eenvoudig aanmaken en registreren van YAML‐configuraties.
 */
public class ConfigBuilder {
    private final Plugin plugin;
    private String fileName;
    private boolean autoSave = true;

    private ConfigBuilder(Plugin plugin) {
        this.plugin = plugin;
    }

    /** Start de builder met je JavaPlugin. */
    public static ConfigBuilder of(Plugin plugin) {
        return new ConfigBuilder(plugin);
    }

    /** Geef de bestandsnaam op (bijv. "config.yml"). */
    public ConfigBuilder fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /** Bepaal of op shutdown auto opgeslagen wordt. */
    public ConfigBuilder autoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }

    /**
     * Maak een YamlConfig, registreer hem en return de instance.
     *
     * @param service de core ConfigService
     * @return de geregistreerde ConfigFile
     */
    public ConfigFile register(ConfigService service) {
        YamlConfig cfg = new YamlConfig(plugin, fileName);
        service.register(cfg);
        if (autoSave) {
            Runtime.getRuntime().addShutdownHook(new Thread(cfg::save));
        }
        return cfg;
    }
}
