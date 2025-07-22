package com.blockkit.core.world;

import com.blockkit.BlockKit;
import com.blockkit.api.world.WorldPreset;
import com.blockkit.api.world.WorldSpec;
import com.blockkit.api.world.WorldManager;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.world.WorldEvent;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
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
    public boolean setSpawn(World world, Location spawn) {
        if (world == null) return false;
        world.setSpawnLocation(spawn);
        return true;
    }

    @Override
    public boolean teleport(Player player, World world) {
        if (player == null || world == null) return false;
        Location spawn = world.getSpawnLocation();
        if (spawn == null) spawn = new Location(world, 0, 64, 0);
        player.teleport(spawn.add(0.5, 0, 0.5));
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
    public <E extends Event> WorldManager on(
            Class<E> eventClass,
            Consumer<E> handler,
            World world
    ) {
        // hergebruiken van ListenerKit
        BlockKit.listenerRegister()
                .on(eventClass, ev -> {
                    if (ev instanceof org.bukkit.event.world.WorldEvent) {
                        handler.accept(ev);
                    } else {
                        // gebruik helper uit WorldEventRegistrarImpl
                        String wName = world.getName();
                        // filter op wereld
                        org.bukkit.World w = null;

                        if (ev instanceof BlockEvent) {
                            w = ((BlockEvent) ev).getBlock().getWorld();
                        }
                        if (ev instanceof EntityEvent) {
                            BlockKit.getChat().broadcast(ev.getEventName() + " event");
                            w = ((EntityEvent) ev).getEntity().getWorld();
                            BlockKit.getChat().broadcast(((EntityEvent) ev).getEntity().getWorld() + " world");
                        }
                        if (ev instanceof PlayerEvent) {
                            w = ((PlayerEvent) ev).getPlayer().getWorld();
                        }

                        if (w != null && wName.equals(w.getName())) {
                            handler.accept(ev);
                        }
                    }
                })
                .bind();
        return this;
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
