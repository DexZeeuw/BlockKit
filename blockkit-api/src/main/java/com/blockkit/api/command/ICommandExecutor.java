package com.blockkit.api.command;

import com.blockkit.api.command.context.CommandContext;
import com.blockkit.api.command.context.TabContext;
import java.util.List;

/**
 * Represents one command (with optional subcommands and tab-completion).
 */
public interface ICommandExecutor {

    /** Primary command label (e.g. "warp"). */
    String getName();

    /** Execute the command. @return true if handled. */
    boolean execute(CommandContext ctx);

    /** Provide tab-completion suggestions. */
    List<String> tabComplete(TabContext ctx);

    /** Whether this executor provides its own tab completer. */
    default boolean hasTabCompleter() {
        return true;
    }
}
