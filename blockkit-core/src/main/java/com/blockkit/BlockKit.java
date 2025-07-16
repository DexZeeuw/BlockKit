package com.blockkit;

import org.bukkit.plugin.Plugin;

import com.blockkit.api.chat.ChatMessenger;
import com.blockkit.chat.core.ChatConfig;
import com.blockkit.chat.core.ChatFormatterImpl;
import com.blockkit.chat.core.ChatMessengerImpl;
import com.blockkit.config.core.ConfigService;
import com.blockkit.menu.core.MenuManager;
import com.blockkit.time.core.DefaultDurationFormatter;
import com.blockkit.time.core.TimeService;

/**
 * Central entry point for BlockKit.
 * Initialize exactly once in your JavaPlugin#onEnable() and access
 * all subsystems via the static getters.
 */
public final class BlockKit {

    private static Plugin plugin;
    private static ChatMessenger chat;
    private static ConfigService config;
    private static MenuManager menu;
    private static TimeService time;

    private BlockKit() {
        // prevent instantiation
    }

    /**
     * Initialize BlockKit. Call this in your plugin’s onEnable().
     *
     * @param pl your JavaPlugin instance
     * @throws IllegalStateException if already initialized
     */
    public static void init(Plugin pl) {
        if (plugin != null) {
            throw new IllegalStateException("BlockKit has already been initialized");
        }
        plugin = pl;

        // Chat subsystem
        ChatConfig chatConfig = new ChatConfig(plugin);
        chat = new ChatMessengerImpl(new ChatFormatterImpl(chatConfig));

        // Config subsystem
        config = new ConfigService();

        // Menu subsystem
        menu = new MenuManager();

        // Time subsystem
        time = new TimeService(new DefaultDurationFormatter());
    }

    /** @return the plugin that initialized BlockKit */
    public static Plugin getPlugin() {
        return plugin;
    }

    /** @return ChatMessenger for sending formatted messages */
    public static ChatMessenger getChat() {
        return chat;
    }

    /** @return ConfigService for loading and saving YAML configs */
    public static ConfigService getConfigService() {
        return config;
    }

    /** @return MenuManager for registering menus and click handlers */
    public static MenuManager getMenuManager() {
        return menu;
    }

    /** @return TimeService for formatting durations and countdowns */
    public static TimeService getTimeService() {
        return time;
    }
}
