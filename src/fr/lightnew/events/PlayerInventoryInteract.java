package fr.lightnew.events;

import fr.lightnew.TheTower;
import fr.lightnew.game.GameStats;
import fr.lightnew.gui.GuiTeams;
import fr.lightnew.items.ItemsActionBar;
import fr.lightnew.utils.TeamsManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerInventoryInteract implements Listener {

    @EventHandler
    public void InventoryInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        Inventory inv = event.getInventory();
        String message_error_team = ChatColor.translateAlternateColorCodes('&', TheTower.instance.getConfig().getString("interaction.message-same-team"));

        if (inv != null || item != null) {
            if (GameStats.inState(GameStats.LOBBY)) {
                if (Objects.equals(inv, GuiTeams.inventory)) {
                    if (item.isSimilar(GuiTeams.ITEM_RED)) {
                        if (!TeamsManager.getPlayerInTempTeamRed(player)) {
                            player.closeInventory();
                            player.sendMessage(ChatColor.RED + "Vous êtes dans l'équipe rouge");
                            TeamsManager.tempSetPlayerInTeamRed(player);
                        } else {
                            player.sendMessage(message_error_team);
                            event.setCancelled(true);
                        }
                    }
                    if (item.isSimilar(GuiTeams.ITEM_BLUE)) {
                        if (!TeamsManager.getPlayerInTempTeamBlue(player)) {
                            player.closeInventory();
                            player.sendMessage(ChatColor.BLUE + "Vous êtes dans l'équipe bleu");
                            TeamsManager.tempSetPlayerInTeamBlue(player);
                        } else {
                            player.sendMessage(message_error_team);
                            event.setCancelled(true);
                        }
                    }
                }
                if (Objects.equals(inv, player.getInventory())) {
                    if (Objects.equals(item, ItemsActionBar.ITEM_TEAM)) {
                        event.setCancelled(true);
                    }
                }
            } else player.getItemInHand().setType(Material.AIR);
        }
    }
}