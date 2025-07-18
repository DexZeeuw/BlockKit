package com.blockkit.core.chat;

import org.bukkit.plugin.Plugin;

/**
 * Laadt chat-instellingen uit de plugin’s config.yml.
 * Verwacht onder de root:
 *
 * chat:
 *   prefix: "&7[MyPlugin] &r"
 *   multiLine: false
 *   newLineDelimiter: "%nl%"
 */
public class ChatConfig {

    private final Plugin plugin;
    private String prefix = "";
    private String divider = "";
    private boolean multiLine = false;

    private String primaryHex = "";
    private String secondaryHex = "";

    public ChatConfig(Plugin plugin) {
        this.plugin = plugin;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDivider() {
        return divider;
    }

    public boolean isMultiLine() {
        return multiLine;
    }

    public String getPrimaryHex() {
        return primaryHex;
    }

    public String getSecondaryHex() {
        return secondaryHex;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setDivider(String divider) {
        this.divider = divider;
    }

    public void setMultiLine(boolean multiLine) {
        this.multiLine = multiLine;
    }

    public void setPrimaryHex(String primaryHex) {
        this.primaryHex = primaryHex;
    }

    public void setSecondaryHex(String secondaryHex) {
        this.secondaryHex = secondaryHex;
    }
}