package fr.lightnew.events;

import fr.lightnew.game.GameStats;
import fr.lightnew.utils.TeamsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class CancelEvents implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (GameStats.inState(GameStats.LOBBY) || GameStats.inState(GameStats.END))
            event.setCancelled(true);
        if (GameStats.inState(GameStats.GAME))
            event.setCancelled(false);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (GameStats.inState(GameStats.LOBBY) || GameStats.inState(GameStats.END))
            event.setCancelled(true);
        if (GameStats.inState(GameStats.GAME))
            event.setCancelled(false);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (GameStats.inState(GameStats.LOBBY) || GameStats.inState(GameStats.END))
            event.setCancelled(true);
        if (GameStats.inState(GameStats.GAME))
            event.setCancelled(false);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (GameStats.inState(GameStats.LOBBY) || GameStats.inState(GameStats.END))
            event.setCancelled(true);
        if (GameStats.inState(GameStats.GAME))
            event.setCancelled(false);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (GameStats.inState(GameStats.LOBBY) || GameStats.inState(GameStats.END))
            event.setCancelled(true);
        if (GameStats.inState(GameStats.GAME)) {
            event.setCancelled(false);
            Player player = event.getPlayer();
            Player target = event.getPlayer().getKiller();
            if (TeamsManager.playerInTeamTargetPlayer(player, target)) {
                event.setCancelled(true);
            } else event.setCancelled(false);
        }
    }

    @EventHandler
    public void Food(FoodLevelChangeEvent event) { event.setCancelled(true);}

    @EventHandler
    public void WeatherChange(WeatherChangeEvent event) {
        if (!event.toWeatherState())
            return;
        event.setCancelled(true);
        event.getWorld().setWeatherDuration(0);
        event.getWorld().setThundering(false);
    }

    @EventHandler
    public void onMoveInventoryItems(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null)
            if (item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.LEATHER_LEGGINGS ||
                    item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.IRON_BOOTS || item.getType() == Material.IRON_LEGGINGS)
                event.setCancelled(true);
    }

}
