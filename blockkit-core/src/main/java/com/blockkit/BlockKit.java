package com.blockkit;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import com.blockkit.api.chat.ChatMessenger;
import com.blockkit.chat.core.ChatConfig;
import com.blockkit.chat.core.ChatFormatterImpl;
import com.blockkit.chat.core.ChatMessengerImpl;

import com.blockkit.api.item.ItemBuilder;
import com.blockkit.item.builder.ItemBuilderImpl;

import com.blockkit.api.menu.MenuBuilder;
import com.blockkit.api.menu.MenuManager;
import com.blockkit.menu.builder.MenuBuilderImpl;
import com.blockkit.menu.core.MenuManagerImpl;

import com.blockkit.config.core.ConfigService;

import com.blockkit.time.core.DefaultDurationFormatter;
import com.blockkit.time.core.TimeService;

/**
 * Central entry point for BlockKit.
 * Initialize once in your JavaPlugin#onEnable() and access all subsystems
 * via the static getters and builders.
 */
public final class BlockKit {

    private static Plugin plugin;
    private static ChatMessenger chat;
    private static ConfigService config;
    private static MenuManager menuManager;
    private static TimeService timeService;

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
        menuManager = MenuManagerImpl.getInstance();

        // Time subsystem
        timeService = new TimeService(new DefaultDurationFormatter());
    }

    /** @return the plugin that initialized BlockKit */
    public static Plugin getPlugin() {
        return plugin;
    }

    /** @return ChatMessenger for sending formatted messages */
    public static ChatMessenger getChat() {
        return chat;
    }

    /** @return a new ItemBuilder for creating ItemStacks fluently */
    public static ItemBuilder itemBuilder(Material material) {
        return new ItemBuilderImpl(material);
    }

    /** @return a new MenuBuilder for building interactive menus */
    public static MenuBuilder menuBuilder(String title, int rows) {
        return new MenuBuilderImpl(title, rows);
    }

    /** @return MenuManager for registering menus and click handlers */
    public static MenuManager getMenuManager() {
        return menuManager;
    }

    /** @return ConfigService for loading & saving YAML configs */
    public static ConfigService getConfigService() {
        return config;
    }

    /** @return TimeService for formatting durations and countdowns */
    public static TimeService getTimeService() {
        return timeService;
    }
}
