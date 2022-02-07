package fr.lightnew.events;

import fr.lightnew.TheTower;
import fr.lightnew.game.GameSettings;
import fr.lightnew.items.KitEquipment;
import fr.lightnew.statistiques.StatDeath;
import fr.lightnew.statistiques.StatKiller;
import fr.lightnew.utils.ItemBuilder;
import fr.lightnew.utils.TeamsManager;
import fr.lightnew.utils.Title;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class DeathEvent implements Listener {

    private int time = 6;

    @EventHandler
    public void deathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player target = event.getEntity().getKiller();
        //Message death
        player.getInventory().clear();
        event.getDrops().clear();
        if (target instanceof Player) {
            event.setDeathMessage(TeamsManager.getPrefix(target) + target.getName() + ChatColor.YELLOW + " a tué " + ChatColor.RESET + TeamsManager.getPrefix(player) + player.getName());
            //play sound
            target.playSound(target.getLocation(), Sound.NOTE_STICKS, 20, 1);
            //
            player.spigot().respawn();
            cooldownRespawn(player);
            StatKiller.addKill(target, 1);
        } else  {
            event.setDeathMessage(TeamsManager.getPrefix(player) + player.getName() + ChatColor.YELLOW + " est mort");
            player.spigot().respawn();
            cooldownRespawn(player);
        }
        StatDeath.addDeath(player, 1);
    }

    private void cooldownRespawn(Player player) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                Title.sendTitle(player, 5, 20, 5, ChatColor.YELLOW + "Vous pouvez respawn dans " + ChatColor.RED + time);
                player.setGameMode(GameMode.SPECTATOR);
                if (time == 0) {
                    cancel();
                    GameSettings.sendPlayersToLocationTeams(player);
                    player.setGameMode(GameMode.SURVIVAL);
                    GameSettings.defaultStuff(player);
                    time=6;
                }
                time--;
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }


    /*TODO PROTECT TO SPAWN KILL*/
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = new Location(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        /*Location locSpawnRed = new Location(Bukkit.getWorld(), x, y, z);
        Location locSpawnBlue = new Location(Bukkit.getWorld(), x, y, z);*/
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType().equals(Material.NETHER_BRICK) || block.getType().equals(Material.STEP) || block.getType().equals(Material.GOLD_BLOCK))
            event.setCancelled(true);
    }
}
