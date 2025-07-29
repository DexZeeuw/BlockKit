package com.blockkit;

import com.blockkit.api.item.ItemUseService;
import com.blockkit.api.listener.ListenerRegister;
import com.blockkit.api.listener.ListenerService;
import com.blockkit.api.scoreboard.ScoreboardBuilder;
import com.blockkit.api.scoreboard.ScoreboardService;
import com.blockkit.api.world.WorldBuilder;
import com.blockkit.api.world.WorldManager;
import com.blockkit.core.chat.ChatConfig;
import com.blockkit.core.fs.file.FileBuilder;
import com.blockkit.core.fs.file.FileService;
import com.blockkit.core.fs.file.FileServiceImpl;
import com.blockkit.core.fs.folder.FolderBuilder;
import com.blockkit.core.fs.folder.FolderService;
import com.blockkit.core.fs.folder.FolderServiceImpl;
import com.blockkit.core.item.builder.ItemUseServiceImpl;
import com.blockkit.core.listener.ListenerServiceImpl;
import com.blockkit.core.scoreboard.ScoreboardBuilderImpl;
import com.blockkit.core.scoreboard.ScoreboardServiceImpl;
import com.blockkit.core.world.WorldManagerImpl;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import com.blockkit.api.chat.ChatMessenger;
import com.blockkit.core.chat.ChatFormatterImpl;
import com.blockkit.core.chat.ChatMessengerImpl;

import com.blockkit.api.item.ItemBuilder;
import com.blockkit.core.item.builder.ItemBuilderImpl;

import com.blockkit.api.menu.MenuBuilder;
import com.blockkit.api.menu.MenuManager;
import com.blockkit.core.menu.builder.MenuBuilderImpl;
import com.blockkit.core.menu.core.MenuManagerImpl;

import com.blockkit.core.config.ConfigService;

import com.blockkit.core.time.DefaultDurationFormatter;
import com.blockkit.core.time.TimeService;

import java.nio.file.Path;

/**
 * Central entry point for BlockKit.
 * Initialize once in your JavaPlugin#onEnable() and access all subsystems
 * via the static getters and builders.
 */
public final class BlockKit {

    private static Plugin plugin;
    private static ChatConfig chatConfig;
    private static ChatMessenger chat;
    private static ConfigService config;
    private static MenuManager menuManager;
    private static WorldManager worldManager;
    private static TimeService timeService;
    private static ListenerService listenerService;
    private static ItemUseService itemUseService;
    private static ScoreboardService scoreboardService;
    private static FileService fileService;
    private static FolderService folderService;

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

        // Chat subsystem wordt pas geactiveerd via setChatConfig()

        // Config subsystem
        config = new ConfigService();

        // Menu subsystem
        menuManager = MenuManagerImpl.getInstance();
        worldManager = new WorldManagerImpl();

        // Time subsystem
        timeService = new TimeService(new DefaultDurationFormatter());

        // listener service
        listenerService = new ListenerServiceImpl();
        itemUseService = new ItemUseServiceImpl();

        scoreboardService = new ScoreboardServiceImpl();

        folderService = new FolderServiceImpl();
        fileService = new FileServiceImpl();
    }

    /** @return the plugin that initialized BlockKit */
    public static Plugin getPlugin() {
        return plugin;
    }

    public static void setChatConfig(ChatConfig config) {
        chatConfig = config;
        chat = new ChatMessengerImpl(new ChatFormatterImpl(chatConfig));
    }


    public static ChatMessenger getChat() {
        if (chat == null) {
            throw new IllegalStateException("Chat subsystem not initialized. Call BlockKit.setChatConfig(...) first.");
        }
        return chat;
    }

    public static ChatConfig getChatConfig() {
        return chatConfig;
    }

    /** @return a new ItemBuilder for creating ItemStacks fluently */
    public static ItemBuilder itemBuilder(Material material) {
        return new ItemBuilderImpl(material);
    }

    /** @return a new MenuBuilder for building interactive menus */
    public static MenuBuilder menuBuilder(String title, int rows) {
        return new MenuBuilderImpl(title, rows);
    }

    /** @return a new WorldBuilder for building worlds */
    public static WorldBuilder worldBuilder() {
        return new WorldBuilder();
    }

    /** @return MenuManager for registering menus and click handlers */
    public static MenuManager getMenuManager() {
        return menuManager;
    }

    /** @return WorldManager for registering worlds and world handlers */
    public static WorldManager getWorldManager() {
        return worldManager;
    }


    /** @return ConfigService for loading & saving YAML configs */
    public static ConfigService getConfigService() {
        return config;
    }

    /** @return TimeService for formatting durations and countdowns */
    public static TimeService getTimeService() {
        return timeService;
    }

    public static ListenerService getListenerService() {
        return listenerService;
    }
    public static ListenerRegister listenerRegister() {
        return ((com.blockkit.core.listener.ListenerServiceImpl)listenerService)
                .createRegistrar();
    }
    public static ItemUseService getItemUseService() {
        return itemUseService;
    }
    public static ScoreboardService getScoreboardService() {
        return scoreboardService;
    }
    public static ScoreboardBuilder scoreboardBuilder(String boardId) {
        return new ScoreboardBuilderImpl(boardId);
    }

    /** @return FolderService for folder services */
    public static FolderService getFolderService() {
        return folderService;
    }
    public static FileService getFileService() {
        return fileService;
    }
    public static FolderBuilder folderBuilder(Path parentPath, String name) {
        return new FolderBuilder()
                .parentPath(parentPath)
                .name(name);
    }
    public static FileBuilder fileBuilder(Path parentPath, String name) {
        return new FileBuilder()
                .parentPath(parentPath)
                .name(name);
    }
}
