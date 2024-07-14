package org.nanacoquette.protoveune.events;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.nanacoquette.protoveune.commands.protoswap;

import java.util.Random;

public final class Events implements Listener {

    private final JavaPlugin plugin;
    private final Random random = new Random();

    public Events(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startAutomaticProcedure() {
        scheduleAutomaticProtoswap();
    }

    private void scheduleAutomaticProtoswap() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            protoswap.execute();
        }, getRandomDelay(), getRandomDelay());
    }

    private int getRandomDelay() {
        int minDelay = 48000; // 40 minutes (48000 ticks)
        int maxDelay = 216000; // 3 hours (216000 ticks)

        return minDelay + random.nextInt(maxDelay - minDelay + 1);
    }
}
