package com.blockkit.api.world;

import org.bukkit.World;
import org.bukkit.GameRule;
import org.bukkit.WeatherType;

/**
 * Een WorldPreset past extra instellingen toe op een Minecraft–wereld
 * direct na het aanmaken via WorldBuilder.
 */
@FunctionalInterface
public interface WorldPreset {

    /**
     * Past configuratie toe op de opgegeven wereld.
     */
    void apply(World world);

    /**
     * Geen aanpassing. Default preset.
     */
    WorldPreset NONE = w -> {};

    /**
     * Een VOID–preset zonder mobs, regen of tijdsverloop.
     */
    WorldPreset VOID = world -> {
        world.setTime(1000);
        world.setStorm(false);
        world.setWeatherDuration(999999);

        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
    };
}
