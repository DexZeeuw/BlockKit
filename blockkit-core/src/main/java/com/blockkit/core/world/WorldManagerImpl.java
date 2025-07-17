package com.blockkit.core.world;

import com.blockkit.api.world.WorldPreset;
import com.blockkit.api.world.WorldSpec;
import com.blockkit.api.world.WorldManager;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

public class WorldManagerImpl implements WorldManager {

    @Override
    public World create(WorldSpec spec) {
        try {
            WorldCreator creator = new WorldCreator(spec.name())
                .environment(spec.environment())
                .type(spec.worldType())
                .seed(spec.seed())
                .generateStructures(spec.generateStructures());

            World world = creator.createWorld();
            spec.preset().apply(world);
            return world;
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "World creation failed: " + spec.name(), e);
            return null;
        }
    }

    @Override
    public boolean delete(World world) {
        try {
            if (world == null || world.getPlayers().size() > 0) return false;
            boolean unloaded = unload(world);
            if (!unloaded) return false;

            File worldFolder = world.getWorldFolder();
            return deleteRecursive(worldFolder);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to delete world: " + world.getName(), e);
            return false;
        }
    }

    private boolean deleteRecursive(File file) {
        if (!file.exists()) return false;
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursive(child);
            }
        }
        return file.delete();
    }

    @Override
    public boolean unload(World world) {
        return Bukkit.unloadWorld(world, false);
    }

    @Override
    public boolean teleport(Player player, World world) {
        if (player == null || world == null) return false;
        Location spawn = world.getSpawnLocation();
        if (spawn == null) spawn = new Location(world, 0, 64, 0);
        player.teleport(spawn);
        return true;
    }

    @Override
    public boolean setTime(World world, long time) {
        if (world == null) return false;
        world.setTime(time);
        return true;
    }

    @Override
    public boolean setWeather(World world, WeatherType type) {
        if (world == null || type == null) return false;

        switch (type) {
            case CLEAR:
                world.setStorm(false);
                break;
            case DOWNFALL:
                world.setStorm(true);
                break;
        }
        return true;
    }

    @Override
    public <T> boolean setGameRule(World world, GameRule<T> rule, T value) {
        if (world == null || rule == null || value == null) return false;
        return world.setGameRule(rule, value);
    }

    @Override
    public WorldSpec serialize(World world) {
        if (world == null) return null;
        return new WorldSpec(
            world.getName(),
            world.getEnvironment(),
            world.getWorldType(),
            world.getSeed(),
            world.canGenerateStructures(),
            WorldPreset.NONE // runtime preset niet bekend; default gebruiken
        );
    }

    @Override
    public List<World> getAll() {
        return Bukkit.getWorlds();
    }
}
