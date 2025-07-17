package com.blockkit.api.world;

import org.bukkit.World;
import org.bukkit.WorldType;

/**
 * Immutable pakket met configuratiegegevens voor het aanmaken van een Minecraft‐wereld.
 * Wordt gebruikt door WorldBuilder of WorldManager#create(...).
 */
public final class WorldSpec {

    private final String name;
    private final World.Environment environment;
    private final WorldType worldType;
    private final long seed;
    private final boolean generateStructures;
    private final WorldPreset preset;

    public WorldSpec(
        String name,
        World.Environment environment,
        WorldType worldType,
        long seed,
        boolean generateStructures,
        WorldPreset preset
    ) {
        this.name = name;
        this.environment = environment;
        this.worldType = worldType;
        this.seed = seed;
        this.generateStructures = generateStructures;
        this.preset = preset;
    }

    public String name() {
        return name;
    }

    public World.Environment environment() {
        return environment;
    }

    public WorldType worldType() {
        return worldType;
    }

    public long seed() {
        return seed;
    }

    public boolean generateStructures() {
        return generateStructures;
    }

    public WorldPreset preset() {
        return preset;
    }
}
