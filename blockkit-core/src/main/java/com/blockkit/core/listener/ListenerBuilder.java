package com.blockkit.core.listener;

import com.blockkit.BlockKit;
import com.blockkit.api.listener.ListenerRegister;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ListenerBuilder implements ListenerRegister {
    private final Plugin plugin;
    private final ListenerServiceImpl service;
    private final List<Listener> temp = new ArrayList<>();

    public ListenerBuilder(Plugin plugin, ListenerServiceImpl service) {
        this.plugin = plugin;
        this.service = service;
    }

    @Override
    public <E extends Event> ListenerRegister on(Class<E> eventClass, Consumer<E> handler) {
        Listener listener = new Listener() {}; // marker

        EventExecutor executor = (l, e) -> {
            if (eventClass.isInstance(e)) {
                handler.accept(eventClass.cast(e));
            }
        };

        // nu past het: Class<E> is Class<? extends Event>
        plugin.getServer().getPluginManager()
                .registerEvent(eventClass, listener,
                        org.bukkit.event.EventPriority.NORMAL,
                        executor, plugin);
        temp.add(listener);
        return this;
    }

    @Override
    public void bind() {
        temp.forEach(service::register);
        temp.clear();
    }
}
