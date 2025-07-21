package com.blockkit.api.world;

import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;
import java.util.function.Consumer;

/**
 * Interface voor het beheren, aanpassen en creëren van Minecraft–werelden.
 */
public interface WorldManager {

    /**
     * Creëert een nieuwe wereld op basis van de opgegeven specificatie.
     *
     * @param spec de wereldconfiguratie
     * @return de gecreëerde wereld, of null bij fout
     */
    World create(WorldSpec spec);

    /**
     * Verwijdert een wereld van disk (mits unloaded).
     *
     * @param world de te verwijderen wereld
     * @return true als geslaagd
     */
    boolean delete(World world);

    /**
     * Unloadt een wereld zonder de server te stoppen.
     *
     * @param world de te unloaden wereld
     * @return true als geslaagd
     */
    boolean unload(World world);

    /**
     * Wijzigt de spawn van een wereld
     *
     * @param world de wereld
     * @param spawn de doellocatie
     * @return true als geslaagd
     */
    boolean setSpawn(World world, Location spawn);

    /**
     * Teleporteert een speler naar een specifieke wereld.
     *
     * @param player de speler
     * @param world de doellocatie
     * @return true als teleport succesvol was
     */
    boolean teleport(Player player, World world);

    /**
     * Wijzigt de tijd van een wereld.
     */
    boolean setTime(World world, long time);

    /**
     * Past het weer aan voor een wereld.
     */
    boolean setWeather(World world, WeatherType type);

    /**
     * Stelt een game rule in op de wereld.
     *
     * @param rule het rule–type
     * @param value de waarde
     * @return true als geslaagd
     */
    <T> boolean setGameRule(World world, GameRule<T> rule, T value);

    /**
     * Registreer een handler die alleen afgaat in {@code world}.
     *
     * @param eventClass het Bukkit Event-type
     * @param handler    de callback
     * @param world      de wereld context
     * @param <E>        subtype van Event
     * @return deze WorldManager (fluent)
     */
    <E extends Event> WorldManager on(
            Class<E> eventClass,
            Consumer<E> handler,
            World world
    );

    /**
     * Exporteert de huidige instellingen als WorldSpec.
     */
    WorldSpec serialize(World world);

    /**
     * Haalt alle geladen werelden op.
     */
    List<World> getAll();
}
