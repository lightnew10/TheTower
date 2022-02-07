package fr.lightnew.items;

import fr.lightnew.gameevent.TeamUpgrade;
import fr.lightnew.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitEquipment {
    /*EQUIPMENT*/
    private static final ItemStack RED_WOOL = ItemBuilder.create(Material.WOOL, 64, 14, ChatColor.RED + "Rouge");
    private static final ItemStack BLUE_WOOL = ItemBuilder.create(Material.WOOL, 64, 11,ChatColor.BLUE + "Bleu");
    private static final ItemStack SWORD = ItemBuilder.create(Material.STONE_SWORD, 1, "");

    private static final ItemStack HELMET_RED = ItemBuilder.createLeatherStuff(Material.LEATHER_HELMET, 1, Color.RED, "");
    private static final ItemStack CHESTPLATE_RED = ItemBuilder.createLeatherStuff(Material.LEATHER_CHESTPLATE, 1, Color.RED, "");
    private static final ItemStack LEGGINGS_RED = ItemBuilder.createLeatherStuff(Material.LEATHER_LEGGINGS, 1, Color.RED, "");
    private static final ItemStack BOOTS_RED = ItemBuilder.createLeatherStuff(Material.LEATHER_BOOTS, 1, Color.RED, "");

    private static final ItemStack HELMET_BLUE = ItemBuilder.createLeatherStuff(Material.LEATHER_HELMET, 1, Color.BLUE, "");
    private static final ItemStack CHESTPLATE_BLUE = ItemBuilder.createLeatherStuff(Material.LEATHER_CHESTPLATE, 1, Color.BLUE, "");
    private static final ItemStack LEGGINGS_BLUE = ItemBuilder.createLeatherStuff(Material.LEATHER_LEGGINGS, 1, Color.BLUE, "");
    private static final ItemStack BOOTS_BLUE = ItemBuilder.createLeatherStuff(Material.LEATHER_BOOTS, 1, Color.BLUE, "");

    /*UPGRADE ITEM*/
    private static final ItemStack ITEM_UPGRADE_1 = ItemBuilder.create(Material.IRON_LEGGINGS, 1, "");
    private static final ItemStack ITEM_UPGRADE_1_2 = ItemBuilder.create(Material.IRON_BOOTS, 1, "");

    public static void sendEquipmentRed(Player player) {
        player.getInventory().setHelmet(HELMET_RED);
        player.getInventory().setChestplate(CHESTPLATE_RED);
        if (TeamUpgrade.getRedUpgrade1()) {
            player.getInventory().setLeggings(ITEM_UPGRADE_1);
            player.getInventory().setBoots(ITEM_UPGRADE_1_2);
        } else {
            player.getInventory().setLeggings(LEGGINGS_RED);
            player.getInventory().setBoots(BOOTS_RED);
        }
        if (TeamUpgrade.getRedUpgrade2()) {
            ItemStack[] armor = player.getInventory().getArmorContents();
            for (ItemStack i : armor) {
                ItemMeta meta = i.getItemMeta();
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                i.setItemMeta(meta);
            }
        }
        if (TeamUpgrade.getRedUpgrade3()) {
            ItemMeta meta = SWORD.getItemMeta();
            meta.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
            SWORD.setItemMeta(meta);
            player.getInventory().setItem(0, SWORD);

        } else player.getInventory().setItem(0, SWORD);
        player.getInventory().setItem(1, RED_WOOL);
        player.getInventory().setItem(2, RED_WOOL);
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack i : armor) {
            ItemMeta meta = i.getItemMeta();
            meta.spigot().setUnbreakable(true);
            i.setItemMeta(meta);
        }
    }

    public static void sendEquipmentBlue(Player player) {
        player.getInventory().setHelmet(HELMET_BLUE);
        player.getInventory().setChestplate(CHESTPLATE_BLUE);
        if (TeamUpgrade.getBlueUpgrade1()) {
            player.getInventory().setLeggings(ITEM_UPGRADE_1);
            player.getInventory().setBoots(ITEM_UPGRADE_1_2);
        } else {
            player.getInventory().setLeggings(LEGGINGS_BLUE);
            player.getInventory().setBoots(BOOTS_BLUE);
        }
        if (TeamUpgrade.getBlueUpgrade2()) {
            ItemStack[] armor = player.getInventory().getArmorContents();
            for (ItemStack i : armor) {
                ItemMeta meta = i.getItemMeta();
                meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
                i.setItemMeta(meta);
            }
        }
        player.getInventory().setItem(0, SWORD);
        player.getInventory().setItem(1, BLUE_WOOL);
        player.getInventory().setItem(2, BLUE_WOOL);
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack i : armor) {
            ItemMeta meta = i.getItemMeta();
            meta.spigot().setUnbreakable(true);
            i.setItemMeta(meta);
        }
    }
}
