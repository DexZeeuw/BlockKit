package com.blockkit.api.world;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;

import java.util.Random;

/**
 * Fluente builder voor het aanmaken van een Minecraft–wereld via WorldSpec.
 */
public final class WorldBuilder {

    private String name;
    private Environment environment = Environment.NORMAL;
    private WorldType worldType = WorldType.NORMAL;
    private long seed = new Random().nextLong();
    private boolean generateStructures = true;
    private WorldPreset preset = WorldPreset.NONE;

    public WorldBuilder name(String name) {
        this.name = name;
        return this;
    }

    public WorldBuilder environment(Environment env) {
        this.environment = env;
        return this;
    }

    public WorldBuilder type(WorldType type) {
        this.worldType = type;
        return this;
    }

    public WorldBuilder seed(long seed) {
        this.seed = seed;
        return this;
    }

    public WorldBuilder generateStructures(boolean value) {
        this.generateStructures = value;
        return this;
    }

    public WorldBuilder preset(WorldPreset preset) {
        this.preset = preset;
        return this;
    }

    /**
     * Maakt de wereld aan via WorldCreator en past de preset toe.
     */
    public World build() {
        WorldCreator creator = new WorldCreator(name)
            .environment(environment)
            .type(worldType)
            .seed(seed)
            .generateStructures(generateStructures);

        World world = creator.createWorld();
        preset.apply(world);
        return world;
    }

    /**
     * Exporteert als WorldSpec voor eventueel later gebruik.
     */
    public WorldSpec toSpec() {
        return new WorldSpec(name, environment, worldType, seed, generateStructures, preset);
    }
}
