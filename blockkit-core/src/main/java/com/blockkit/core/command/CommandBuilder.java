package com.blockkit.core.command;

import com.blockkit.api.command.ICommandExecutor;
import com.blockkit.api.command.context.CommandContext;
import com.blockkit.api.command.context.TabContext;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Fluent builder + dispatcher for a command with subcommands.
 */
public class CommandBuilder implements ICommandExecutor {

    private final String name;
    private String permission = "";
    private String description = "";
    private BiConsumer<CommandContext, CommandBuilder> handler;
    private Function<TabContext, List<String>> tabCompleter;
    private final Map<String, CommandBuilder> subcommands = new LinkedHashMap<>();

    private CommandBuilder(String name) {
        this.name = name.toLowerCase();
    }

    /** Begin building a command under this label. */
    public static CommandBuilder of(String name) {
        return new CommandBuilder(name);
    }

    /** Set permission node. */
    public CommandBuilder permission(String node) {
        this.permission = node;
        return this;
    }

    /** Set description (for help). */
    public CommandBuilder description(String desc) {
        this.description = desc;
        return this;
    }

    /** Define the main handler. */
    public CommandBuilder handler(BiConsumer<CommandContext, CommandBuilder> h) {
        this.handler = h;
        return this;
    }

    /** Define a custom tab-completer. */
    public CommandBuilder tab(Function<TabContext, List<String>> completer) {
        this.tabCompleter = completer;
        return this;
    }

    /** Add a subcommand. */
    public CommandBuilder sub(String label, Consumer<CommandBuilder> cfg) {
        CommandBuilder child = new CommandBuilder(label);
        cfg.accept(child);
        subcommands.put(label.toLowerCase(), child);
        return this;
    }

    /** Register with the given service. */
    public void register(CommandService svc) {
        svc.register(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean execute(CommandContext ctx) {
        if (!permission.isEmpty() && !ctx.getSender().hasPermission(permission)) {
            ctx.reply("No permission.");
            return true;
        }

        String[] args = ctx.getArgs();
        if (args.length > 0) {
            String key = args[0].toLowerCase();
            if (subcommands.containsKey(key)) {
                ctx.shiftArgs();
                return subcommands.get(key).execute(ctx);
            }
        }

        if (handler != null) {
            handler.accept(ctx, this);
        } else {
            ctx.reply("Usage: /" + name);
        }
        return true;
    }

    @Override
    public List<String> tabComplete(TabContext ctx) {
        if (tabCompleter != null) {
            return tabCompleter.apply(ctx);
        }
        String[] args = ctx.getArgs();
        if (args.length <= 1) {
            String prefix = args.length == 0 ? "" : args[0].toLowerCase();
            List<String> out = new ArrayList<>();
            for (String sub : subcommands.keySet()) {
                if (sub.startsWith(prefix)) out.add(sub);
            }
            return out;
        }
        CommandBuilder child = subcommands.get(args[0].toLowerCase());
        if (child != null) {
            String[] rest = Arrays.copyOfRange(args, 1, args.length);
            return child.tabComplete(new TabContext(ctx.getSender(), ctx.getLabel(), rest));
        }
        return Collections.emptyList();
    }
}
