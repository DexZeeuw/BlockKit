package com.blockkit.api.listener;

import org.bukkit.event.Event;

import java.util.function.Consumer;

/**
 * Fluente builder voor inline event-handlers.
 */
public interface ListenerRegister {
    /**
     * Registreer een lambda voor een specifiek event-type.
     */
    <E extends Event> ListenerRegister on(Class<E> eventClass, Consumer<E> handler);

    /**
     * Build en registreer alle handlers tegelijk.
     */
    void bind();
}
