package fr.lightnew.gameevent;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.Random;

public class ChestGameEvent {

    public static void addItemInChest() {
        for (Chunk chunk : Bukkit.getServer().getWorld("world").getLoadedChunks()) {
            for (BlockState enti : chunk.getTileEntities()) {
                if (enti instanceof Chest) {
                    Inventory inv = ((Chest) enti).getInventory();
                    inv.clear();

                    Random random = new Random();
                    ItemStack[] list = new ItemStack[] {buildPotion(PotionType.INSTANT_HEAL), buildPotion(PotionType.JUMP), buildPotion(PotionType.REGEN), buildPotion(PotionType.SPEED),
                            new ItemStack(Material.GOLDEN_APPLE, 1), new ItemStack(Material.APPLE, 1)};
                    for(int i = 0;i<1;i++) {
                        inv.setItem( 13, new ItemStack(list[random.nextInt(list.length)]));
                    }
                }
            }
        }
    }

    private static ItemStack buildPotion(PotionType potionType) {
        Potion potion = new Potion(potionType);
        potion.setLevel(1);
        potion.setTier(Potion.Tier.ONE);
        potion.setSplash(true);
        ItemStack pot = potion.toItemStack(1);
        return pot;
    }
}
