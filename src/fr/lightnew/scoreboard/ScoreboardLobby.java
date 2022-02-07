package fr.lightnew.scoreboard;

import fr.lightnew.TheTower;
import fr.lightnew.game.GameStats;
import fr.lightnew.game.Timers;
import fr.lightnew.utils.TeamsManager;
import fr.mrcubee.scoreboard.CustomSideBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.WeakHashMap;

public class ScoreboardLobby {
    public static WeakHashMap<Player, CustomSideBar> boards = new WeakHashMap<>();
    static String serv_ip = TheTower.instance.getConfig().getString("scoreboard.server-ip");

    public static void send(Player player) {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String players = ChatColor.YELLOW + "Joueurs : " + ChatColor.AQUA + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/" + ChatColor.YELLOW + TheTower.instance.getConfig().getString("Game.max-players");

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (GameStats.inState(GameStats.LOBBY)) {
                    if (player.isOnline()) {
                        CustomSideBar custom = boards.get(player);
                        if (custom == null) {
                            custom = CustomSideBar.create("dummy", ChatColor.RED + "TheTower");
                            boards.put(player, custom);
                            custom.getReceivers().add(player);
                        }
                        custom.setLines(Arrays.asList(
                                ChatColor.GRAY + date,
                                "§1 ",
                                players,
                                "§2 ",
                                timer_preStart(),
                                "§3 ",
                                ChatColor.RESET + "Votre Équipe : " + TeamsManager.getTempTeam(player),
                                "§4 ",
                                ChatColor.YELLOW + serv_ip

                        ));
                    }
                }
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }

    private static String timer_preStart() {
        String result = "Attente joueurs...";
        if (Bukkit.getOnlinePlayers().size() >= TheTower.instance.getConfig().getInt("Game.min-player-launch-game"))
            result = ChatColor.YELLOW + "jeu dans " + ChatColor.RESET + Timers.ti + ChatColor.YELLOW + " secondes!";
        return result;
    }
}
