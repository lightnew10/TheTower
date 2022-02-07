package fr.lightnew.events;

import fr.lightnew.TheTower;
import fr.lightnew.game.GameStats;
import fr.lightnew.utils.TeamsManager;
import fr.lightnew.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.Yaml;

import java.util.List;

public class MovePlayerSpawnKill implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player  =event.getPlayer();
        Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getBlockY(), player.getLocation().getZ());
        Location locd = new Location(player.getWorld(), 161, 52, 1348);
        //TODO ANTI-SPAWN-KILL TEAM BLUE
        List<Integer> zBlue = TheTower.instance.getConfig().getIntegerList("anti-spawnkill.loc");
        for (int zB : zBlue) {
            for (int yB = 52; yB < 54; yB++) {
                if (loc.getBlockX() == 164 && loc.getBlockY() == yB && loc.getBlockZ() == zB) {
                    if (TeamsManager.getTeam_red(player)) {
                        Title.sendTitle(player, 3, 15, 3, ChatColor.RED + "Vous avez pas le droit d'aller ici");
                        player.setVelocity(new Vector(2, 0, 0));
                    } else
                        player.setVelocity(new Vector(0.5, 0, 0));
                }
            }
        }
        //TODO ANTI-SPAWN-KILL TEAM RED
        List<Integer> zRed = TheTower.instance.getConfig().getIntegerList("anti-spawnkill.loc");
        for (int zR : zRed) {
            for (int yR = 52; yR < 54; yR++) {
                if (loc.getBlockX() == 342 && loc.getBlockY() == yR && loc.getBlockZ() == zR) {
                    if (TeamsManager.getTeam_blue(player)) {
                        Title.sendTitle(player, 3, 15, 3, ChatColor.RED + "Vous avez pas le droit d'aller ici");
                        player.setVelocity(new Vector(-2, 0, 0));
                    } else
                        player.setVelocity(new Vector(-0.5, 0, 0));
                }
            }
        }

        Location locSpawn = new Location(Bukkit.getWorld("world"), 0.5 ,20 ,0.5 ,-180 ,(float) -0.7);
        if (GameStats.inState(GameStats.END)) {
            if (player.getLocation().getBlockY() == 11)
                player.teleport(locSpawn);
        }
    }
}
