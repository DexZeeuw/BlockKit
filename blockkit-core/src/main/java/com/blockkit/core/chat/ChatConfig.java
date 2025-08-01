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
public class ChatConfig implements com.blockkit.api.chat.ChatConfig {

    private final Plugin plugin;
    private String prefix = "";
    private String divider = "";
    private boolean multiLine = false;

    private String primaryHex = "";
    private String secondaryHex = "";
    private String tertiaryHex = "";
    private String quaternaryHex = "";

    public ChatConfig(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getDivider() {
        return this.divider;
    }

    @Override
    public void setDivider(String divider) {
        this.divider = divider;
    }

    @Override
    public boolean isMultiLine() {
        return this.multiLine;
    }

    @Override
    public void setMultiLine(boolean multiLine) {
        this.multiLine = multiLine;
    }

    @Override
    public String getPrimaryHex() {
        return this.primaryHex;
    }

    @Override
    public void setPrimaryHex(String hex) {
        this.primaryHex = hex;
    }

    @Override
    public String getSecondaryHex() {
        return this.secondaryHex;
    }

    @Override
    public void setSecondaryHex(String hex) {
        this.secondaryHex = hex;
    }

    @Override
    public String getTertiaryHex() {
        return this.tertiaryHex;
    }

    @Override
    public void setTertiaryHex(String hex) {
        this.tertiaryHex = hex;
    }

    @Override
    public String getQuaternaryHex() {
        return this.quaternaryHex;
    }

    @Override
    public void setQuaternaryHex(String hex) {
        this.quaternaryHex = hex;
    }
}