package fr.lightnew.events;

import fr.lightnew.TheTower;
import fr.lightnew.game.GameSettings;
import fr.lightnew.gameevent.TeamUpgrade;
import fr.lightnew.utils.TeamsManager;
import fr.lightnew.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collection;
import java.util.List;

public class AddPointBut implements Listener {
    @EventHandler
    public void pointEvent(PlayerMoveEvent event) {
        List<Integer> xR = TheTower.instance.getConfig().getIntegerList("but-red.loc-x");
        List<Integer> xB = TheTower.instance.getConfig().getIntegerList("but-blue.loc-x");

        List<Integer> zU = TheTower.instance.getConfig().getIntegerList("but-uni.z");
        List<Integer> yU = TheTower.instance.getConfig().getIntegerList("but-uni.y");

        Player player = event.getPlayer();
        Location locPlayer = player.getLocation();

        int xPlayer = locPlayer.getBlockX();
        int yPlayer = locPlayer.getBlockY();
        int zPlayer = locPlayer.getBlockZ();

        String title = TheTower.instance.getConfig().getString("message-player-goal.title").replace("%team%", TeamsManager.getTeam(player).replace('&', '§'));
        String subtitle = TheTower.instance.getConfig().getString("message-player-goal.subtitle").replace("%team%", TeamsManager.getTeam(player).replace('&', '§'));

        //but red
        for (int z : zU)
            for (int y : yU)
                for (int x : xR)
                    if (z == zPlayer && y == yPlayer && x == xPlayer) {
                        if (TeamsManager.list_team_blue.contains(player)) {
                            TeamUpgrade.addPointBlueTeam(1);
                            sendTitleForAll(title, subtitle);
                            GameSettings.sendPlayersToLocationTeams(player);
                        }
                    }

        //but blue
        for (int z : zU)
            for (int y : yU)
                for (int x : xB)
                    if (z == zPlayer && y == yPlayer && x == xPlayer) {
                        if (TeamsManager.list_team_red.contains(player)) {
                            TeamUpgrade.addPointRedTeam(1);
                            sendTitleForAll(title, subtitle);
                            GameSettings.sendPlayersToLocationTeams(player);
                        }
                    }
    }

    private void sendTitleForAll(String var1, String var2) {
        Collection<? extends Player> p = Bukkit.getOnlinePlayers();
        for (Player player : p) {
            if (var2 != null) {
                Title.sendTitle(player, 3, 25, 3, var1, var2);
            } else Title.sendTitle(player, 3, 25, 3, var1);
        }
    }
}
