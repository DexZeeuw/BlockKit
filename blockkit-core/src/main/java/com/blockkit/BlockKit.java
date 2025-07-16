package com.blockkit;

import org.bukkit.plugin.Plugin;

import com.blockkit.chat.core.ChatService;
import com.blockkit.config.core.ConfigService;
import com.blockkit.menu.core.MenuManager;
import com.blockkit.time.core.DefaultDurationFormatter;
import com.blockkit.time.core.TimeService;

/**
 * Central entry point for BlockKit.
 * Initialize once from your JavaPlugin#onEnable() and access all sub‐systems via static getters.
 */
public final class BlockKit {

    private static Plugin plugin;
    private static ChatService chatService;
    private static ConfigService configService;
    private static MenuManager menuManager;
    private static TimeService timeService;

    private BlockKit() {
        // prevent instantiation
    }

    /**
     * Initialize all BlockKit services. Call this in your plugin’s onEnable().
     *
     * @param pl your JavaPlugin instance
     * @throws IllegalStateException if already initialized
     */
    public static void init(Plugin pl) {
        if (plugin != null) {
            throw new IllegalStateException("BlockKit is already initialized");
        }
        plugin = pl;

        // initialize core services
        chatService   = new ChatService(plugin);
        configService = new ConfigService();
        menuManager   = new MenuManager();
        timeService   = new TimeService(new DefaultDurationFormatter());
    }

    /** @return the plugin that initialized BlockKit */
    public static Plugin getPlugin() {
        return plugin;
    }

    /** @return ChatService for sending formatted messages */
    public static ChatService getChatService() {
        return chatService;
    }

    /** @return ConfigService for loading & saving YAML configs */
    public static ConfigService getConfigService() {
        return configService;
    }

    /** @return MenuManager for registering menu inventories & click handlers */
    public static MenuManager getMenuManager() {
        return menuManager;
    }

    /** @return TimeService for formatting durations and countdowns */
    public static TimeService getTimeService() {
        return timeService;
    }
}
