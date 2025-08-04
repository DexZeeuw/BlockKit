package com.blockkit.extras.menu;

/**
 * Neemt drie argumenten en voert een bewerking uit.
 * Dit is een Java-8 functional interface.
 */
@FunctionalInterface
public interface TriConsumer<A, B, C> {
    void accept(A a, B b, C c);
}
