package com.blockkit.extras.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Fluente builder voor JSON‐chatcomponenten met kleuren, styles,
 * hover- en click-events (Bungee Chat API).
 */
public class ChatComponentBuilder {
    private final List<BaseComponent> parts = new ArrayList<>();
    private String text;
    private ChatColor color;
    private Boolean bold, italic, underlined, strikethrough, obfuscated;
    private HoverEvent hoverEvent;
    private ClickEvent clickEvent;

    private ChatComponentBuilder(String text) {
        this.text = text;
    }

    /** Start met een tekstfragment. */
    public static ChatComponentBuilder text(String text) {
        return new ChatComponentBuilder(text);
    }

    /** Zet de kleur voor dit fragment. */
    public ChatComponentBuilder color(ChatColor color) {
        this.color = color;
        return this;
    }

    /** Bold on/off. */
    public ChatComponentBuilder bold(boolean flag) {
        this.bold = flag;
        return this;
    }

    /** Italic on/off. */
    public ChatComponentBuilder italic(boolean flag) {
        this.italic = flag;
        return this;
    }

    /** Underlined on/off. */
    public ChatComponentBuilder underlined(boolean flag) {
        this.underlined = flag;
        return this;
    }

    /** Strikethrough on/off. */
    public ChatComponentBuilder strikethrough(boolean flag) {
        this.strikethrough = flag;
        return this;
    }

    /** Obfuscated on/off. */
    public ChatComponentBuilder obfuscated(boolean flag) {
        this.obfuscated = flag;
        return this;
    }

    /** Hover-tekst tonen. */
    public ChatComponentBuilder hoverText(String hoverText) {
        this.hoverEvent = new HoverEvent(
            HoverEvent.Action.SHOW_TEXT,
            new ComponentBuilder(hoverText).create()
        );
        return this;
    }

    /** Hover-item tonen (ItemStack wordt in tooltip weergegeven). */
    public ChatComponentBuilder hoverItem(ItemStack item) {
        this.hoverEvent = new HoverEvent(
            HoverEvent.Action.SHOW_ITEM,
            new BaseComponent[]{new TextComponent(ItemSerializer.toJson(item))}
        );
        return this;
    }

    /** Klik-event: voer commando uit. */
    public ChatComponentBuilder onClickRunCommand(String command) {
        this.clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, command);
        return this;
    }

    /** Klik-event: open URL. */
    public ChatComponentBuilder onClickOpenUrl(String url) {
        this.clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
        return this;
    }

    /** Klik-event: suggest command in chatbox. */
    public ChatComponentBuilder onClickSuggestCommand(String command) {
        this.clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command);
        return this;
    }

    /** Sluit dit fragment af en begin een nieuw tekstfragment. */
    public ChatComponentBuilder then(String nextText) {
        flush();
        this.text  = nextText;
        this.color = null;
        this.bold  = this.italic = this.underlined = false;
        this.strikethrough = this.obfuscated = false;
        this.hoverEvent    = null;
        this.clickEvent    = null;
        return this;
    }

    /** Bouw de BaseComponent[] array voor verzending. */
    public BaseComponent[] build() {
        flush();
        return parts.toArray(new BaseComponent[0]);
    }

    /** Intern: voeg huidig fragment toe aan de lijst. */
    private void flush() {
        Objects.requireNonNull(text, "Text fragment cannot be null");
        TextComponent comp = new TextComponent(text);
        if (color        != null) comp.setColor(color);
        if (bold         != null) comp.setBold(bold);
        if (italic       != null) comp.setItalic(italic);
        if (underlined   != null) comp.setUnderlined(underlined);
        if (strikethrough!= null) comp.setStrikethrough(strikethrough);
        if (obfuscated   != null) comp.setObfuscated(obfuscated);
        if (hoverEvent   != null) comp.setHoverEvent(hoverEvent);
        if (clickEvent   != null) comp.setClickEvent(clickEvent);
        parts.add(comp);
    }
}
