package org.nanacoquette.protoveune;

import org.bukkit.plugin.java.JavaPlugin;
import org.nanacoquette.protoveune.commands.protoboss;
import org.nanacoquette.protoveune.commands.protoswap;
import org.nanacoquette.protoveune.events.Events;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        // Enrengistrements des commandes
        getCommand("protoswap").setExecutor(new protoswap());
        getCommand("protoboss").setExecutor(new protoboss());


        // Garder une référence à l'instance d'Events
        Events events = new Events(this);
        events.startAutomaticProcedure();

        getLogger().info("Protovun a piraté le serveur...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Protovun a perdu la connexion au serveur...");
    }
}
