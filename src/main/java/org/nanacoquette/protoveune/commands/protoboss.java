package org.nanacoquette.protoveune.commands;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class protoboss implements CommandExecutor, Listener {

    private Plugin plugin;

    public protoboss() {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin); // Enregistrement de l'événement
        sendBroadcastMessages();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Seuls les joueurs peuvent utiliser cette commande.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("protoveune.cmds.protoboss")) {
            player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'utiliser cette commande.");
            return false;
        }

        spawnProtoBoss(player);
        return true;
    }

    private void spawnProtoBoss(Player player) {
        World world = player.getWorld();
        Location location = new Location(world, 19.5, 71, -25.5);

        LivingEntity boss = (LivingEntity) world.spawnEntity(location, EntityType.ZOMBIE);
        boss.setCustomName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Protovun");
        boss.setCustomNameVisible(true);
        boss.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(200.0);
        boss.setHealth(200.0);
        boss.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(30.0);
        boss.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.6);

        PlayerDisguise disguise = new PlayerDisguise("Protovun");
        disguise.setEntity(boss);

        DisguiseAPI.disguiseToAll(boss, disguise);

        Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + " a invoqué " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Protovun" + ChatColor.RESET + ChatColor.GOLD + "!");
        boss.setAI(true);
        boss.setCanPickupItems(false);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Protovun")) {
            ItemStack stack = new ItemStack(Material.NETHERITE_INGOT, 1, (short) 6);
            event.getDrops().clear();
            event.getDrops().add(stack);
        }
    }

    private void sendBroadcastMessages() {
        // Envoi du message de broadcast lorsque le plugin est activé
        String message = ChatColor.RED + "Protovun a décidé de se combattre";
        String messageFinal = message.replace("Protovun", ChatColor.BOLD + "Protovun" + ChatColor.RED);
        Bukkit.broadcastMessage(messageFinal);
        Bukkit.broadcastMessage(ChatColor.RED + "Il est apparu au spawn. Pour le combattre, rendez-vous au /spawn");
    }
}
