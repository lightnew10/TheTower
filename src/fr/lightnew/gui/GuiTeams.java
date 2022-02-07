package fr.lightnew.gui;

import fr.lightnew.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiTeams {
    public static ItemStack ITEM_RED = ItemBuilder.createWithoutLores(Material.WOOL, 1, (byte) 14, ChatColor.RED + "Équipe ROUGE");
    public static ItemStack ITEM_BLUE = ItemBuilder.createWithoutLores(Material.WOOL, 1, (byte) 11, ChatColor.BLUE + "Équipe BLEU");
    public static Inventory inventory;

    public static void send(Player player) {
        inventory = Bukkit.createInventory(player, 3*9, ChatColor.GREEN + "Choix de l'équipe");
        inventory.setItem(12, ITEM_BLUE);
        inventory.setItem(14, ITEM_RED);
        player.openInventory(inventory);
    }
}
