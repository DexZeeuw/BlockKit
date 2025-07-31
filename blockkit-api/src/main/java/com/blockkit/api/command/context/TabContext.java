package com.blockkit.api.command.context;

import org.bukkit.command.CommandSender;

/**
 * Wraps sender, label and args for tab-completion.
 */
public class TabContext {
    private final CommandSender sender;
    private final String       label;
    private final String[]     args;

    public TabContext(CommandSender sender, String label, String[] args) {
        this.sender = sender;
        this.label  = label;
        this.args    = args;
    }

    public CommandSender getSender() { return sender; }
    public String[]       getArgs()   { return args; }
    public String         getLabel()  { return label; }
}
