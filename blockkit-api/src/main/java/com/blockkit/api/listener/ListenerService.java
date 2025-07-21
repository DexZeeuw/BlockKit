package com.blockkit.api.listener;

import org.bukkit.event.Listener;

/**
 * Service voor centraal registeren en unregistreren van Bukkit-listeners.
 */
public interface ListenerService {
    /**
     * Registreer een Bukkit-listener.
     */
    void register(Listener listener);

    /**
     * Unregistreer een Bukkit-listener.
     */
    void unregister(Listener listener);
}
