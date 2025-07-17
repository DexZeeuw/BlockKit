package com.blockkit.api.world;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;

import java.util.List;

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
     * Exporteert de huidige instellingen als WorldSpec.
     */
    WorldSpec serialize(World world);

    /**
     * Haalt alle geladen werelden op.
     */
    List<World> getAll();
}
