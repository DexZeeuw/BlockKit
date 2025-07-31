package com.blockkit.api.command.context;

import org.bukkit.command.CommandSender;
import java.util.Arrays;

/**
 * Wraps sender, label and args for command execution.
 */
public class CommandContext {
    private final CommandSender sender;
    private final String label;
    private String[] args;

    public CommandContext(CommandSender sender, String label, String[] args) {
        this.sender = sender;
        this.label  = label;
        this.args   = args;
    }

    public CommandSender getSender() { return sender; }
    public String[]       getArgs()   { return args; }
    public String         getLabel()  { return label; }

    /** Send a message back to the sender. */
    public void reply(String msg) {
        sender.sendMessage(msg);
    }

    /** Remove the first arg (for nested subcommands). */
    public void shiftArgs() {
        if (args.length > 0) {
            args = Arrays.copyOfRange(args, 1, args.length);
        }
    }
}
