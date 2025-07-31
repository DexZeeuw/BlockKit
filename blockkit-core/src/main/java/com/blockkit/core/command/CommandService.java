package com.blockkit.core.command;

import com.blockkit.api.command.ICommandExecutor;
import com.blockkit.api.command.context.CommandContext;
import com.blockkit.api.command.context.TabContext;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.logging.Logger;

/**
 * Central service to register commands and tab-completers with Bukkit.
 */
public class CommandService {

    private final Plugin plugin;
    private final Logger log;

    public CommandService(Plugin plugin) {
        this.plugin = plugin;
        this.log    = plugin.getLogger();
    }

    /**
     * Register an ICommandExecutor under its getName().
     */
    public void register(ICommandExecutor exec) {
        String name = exec.getName().toLowerCase();
        PluginCommand cmd = plugin.getServer().getPluginCommand(name);
        if (cmd == null) {
            log.warning("Command '/" + name + "' missing in plugin.yml");
            return;
        }

        // Correct onCommand signature: (sender, command, label, args)
        cmd.setExecutor((sender, command, label, args) ->
            exec.execute(new CommandContext(sender, label, args))
        );

        // Correct onTabComplete signature: (sender, command, alias, args)
        if (exec.hasTabCompleter()) {
            cmd.setTabCompleter((sender, command, alias, args) ->
                exec.tabComplete(new TabContext(sender, alias, args))
            );
        }

        log.info("Registered command '/" + name + "'");
    }
}
