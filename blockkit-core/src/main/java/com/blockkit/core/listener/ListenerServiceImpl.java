package com.blockkit.core.listener;

import com.blockkit.BlockKit;
import com.blockkit.api.listener.ListenerRegister;
import com.blockkit.api.listener.ListenerService;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ListenerServiceImpl implements ListenerService {
    private final Plugin plugin = BlockKit.getPlugin();
    private final List<Listener> registered = new ArrayList<>();

    public ListenerServiceImpl() {

    }

    @Override
    public void register(Listener listener) {
        plugin.getServer()
                .getPluginManager()
                .registerEvents(listener, plugin);
        registered.add(listener);
    }

    @Override
    public void unregister(Listener listener) {
        HandlerList.unregisterAll(listener);
        registered.remove(listener);
    }

    /**
     * Maak een nieuwe fluent registrar.
     */
    public ListenerRegister createRegistrar() {
        return new ListenerBuilder(plugin, this);
    }

    /**
     * Unregister all at shutdown (optioneel).
     */
    public void unregisterAll() {
        new ArrayList<>(registered)
                .forEach(this::unregister);
    }
}
