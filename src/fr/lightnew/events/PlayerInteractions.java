package fr.lightnew.events;

import fr.lightnew.game.GameStats;
import fr.lightnew.gui.GuiTeams;
import fr.lightnew.items.ItemsActionBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerInteractions implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (item != null || action != null) {
            if (GameStats.inState(GameStats.LOBBY)) {
                if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                    if (Objects.equals(item, ItemsActionBar.ITEM_TEAM)) {
                        GuiTeams.send(player);
                    }
                }
            }
        }
    }
}
