package fr.lightnew.events;

import fr.lightnew.game.GameStats;
import fr.lightnew.utils.TeamsManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (GameStats.inState(GameStats.GAME)) {
            event.setFormat(TeamsManager.getTeam(player) + " " + player.getName() + ChatColor.RESET + " : " + message);
        }
        if (GameStats.inState(GameStats.LOBBY) || GameStats.inState(GameStats.END)) {
            event.setFormat(player.getName() + " : " + message);
        }
    }
}
