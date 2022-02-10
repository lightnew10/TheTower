package fr.lightnew.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;

public class AntiPlaceBlock implements Listener {
    public static HashMap<Block, Location> removeAllBlock = new HashMap<>();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location locBlock = event.getBlockPlaced().getLocation();
        //zone team red
        int x = locBlock.getBlockX();
        int y = locBlock.getBlockY();
        int z = locBlock.getBlockZ();

        for (int xR = 336; xR < 349; xR++)
            for (int yR = 51; yR < 55; yR++)
                for (int zR = 1346; zR < 1351; zR++)
                    if (x == xR && y == yR && z == zR)
                        if (!player.isOp())
                            event.setCancelled(true);

        //zone team blue
        for (int xB = 336; xB < 349; xB++)
            for (int yB = 51; yB < 55; yB++)
                for (int zB = 1346; zB < 1351; zB++)
                    if (x == xB && y == yB && z == zB)
                        if (!player.isOp())
                            event.setCancelled(true);

        if (y == 85){
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Vous êtes à la hauteur maximum");
        }

        removeAllBlock.put(event.getBlockPlaced(), event.getBlockPlaced().getLocation());
    }
}
