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
    private boolean multiLine = false;
    private String newLineDelimiter = "";

    public ChatConfig(Plugin plugin) {
        this.plugin = plugin;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isMultiLine() {
        return multiLine;
    }

    public String getNewLineDelimiter() {
        return newLineDelimiter;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setMultiLine(boolean multiLine) {
        this.multiLine = multiLine;
    }

    public void setNewLineDelimiter(String newLineDelimiter) {
        this.newLineDelimiter = newLineDelimiter;
    }
}