package fr.lightnew.endgame;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Holo {

    public static void createHolo(Location loc, String message) {
        ArmorStand armor = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armor.setVisible(true);
        armor.setGravity(false);
        armor.setCustomName(message);
        armor.setCustomNameVisible(true);
        armor.setBasePlate(false);
    }
}
