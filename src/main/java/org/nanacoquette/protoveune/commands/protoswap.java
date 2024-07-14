package org.nanacoquette.protoveune.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class protoswap implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player || sender instanceof ConsoleCommandSender) {
            execute();
            return true;
        }
        return false;
    }

    public static void execute() {
        World world = Bukkit.getWorlds().get(0);
        if (world == null) {
            return;
        }

        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Protovun a trouvé l'IP du serveur...");

        String initialMessage = ChatColor.DARK_RED + "Attention !" + ChatColor.DARK_BLUE + ChatColor.BOLD + " Protovun " + ChatColor.RESET + ChatColor.DARK_RED + " a lancé une attaque informatique sur le serveur...";
        Bukkit.broadcastMessage(initialMessage);

        // Planification du piratage après 5 secondes (100 ticks)
        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("Protoveune"), () -> {
            String piratageMessage = ChatColor.GREEN.toString() + ChatColor.BOLD + ChatColor.ITALIC + " PIRATAGE EN COURS";

            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            Collections.shuffle(players);

            for (Player player : players) {
                world.strikeLightningEffect(player.getLocation());
                player.sendTitle(ChatColor.GREEN + "Piratage", "Par Protovun", 10, 70, 20);
                player.sendMessage(piratageMessage);
            }

            List<ItemStack> originalItems = new ArrayList<>();
            for (Player player : players) {
                originalItems.add(player.getInventory().getItemInMainHand());
            }

            int playerCount = players.size();
            for (int i = 0; i < playerCount; i++) {
                Player currentPlayer = players.get(i);
                Player nextPlayer = players.get((i + 1) % playerCount);
                ItemStack currentItem = originalItems.get(i);
                ItemStack nextItem = originalItems.get((i + 1) % playerCount);

                if (currentItem != null && nextItem != null) {
                    currentPlayer.getInventory().setItemInMainHand(nextItem);
                    nextPlayer.getInventory().setItemInMainHand(currentItem);

                    Bukkit.broadcastMessage(ChatColor.YELLOW + currentPlayer.getName() + ChatColor.RESET + " a échangé " + ChatColor.YELLOW + currentItem.getType().name() + ChatColor.RESET + " avec " + ChatColor.YELLOW + nextPlayer.getName() + ChatColor.RESET + " qui avait " + ChatColor.YELLOW + nextItem.getType().name());
                }
            }

            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("Protoveune"), () -> {
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Protovun a quitté le serveur.");
            }, 100); // 100 ticks = 5 secondes

        }, 100); // 100 ticks = 5 secondes
    }
}
