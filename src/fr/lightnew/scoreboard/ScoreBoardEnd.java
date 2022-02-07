package fr.lightnew.scoreboard;

import fr.lightnew.TheTower;
import fr.lightnew.game.GameStats;
import fr.lightnew.statistiques.KillTop;
import fr.lightnew.utils.TeamsManager;
import fr.mrcubee.scoreboard.CustomSideBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.SimpleDateFormat;
import java.util.*;

public class ScoreBoardEnd {
    public static WeakHashMap<Player, CustomSideBar> boards = new WeakHashMap<>();
    static String serv_ip = TheTower.instance.getConfig().getString("scoreboard.server-ip");

    public static void send(Player player) {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String players = ChatColor.YELLOW + "Joueurs : " + ChatColor.AQUA + Bukkit.getOnlinePlayers().size() + ChatColor.YELLOW + "/" + ChatColor.YELLOW + TheTower.instance.getConfig().getString("Game.max-players");
        Player[] tops = KillTop.getTop(3);

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                final List<String> lines;

                if (GameStats.inState(GameStats.END)) {
                    if (player.isOnline()) {
                        CustomSideBar custom = boards.get(player);
                        if (custom == null) {
                            custom = CustomSideBar.create("dummy", ChatColor.RED + "TheTower");
                            boards.put(player, custom);
                            custom.getReceivers().add(player);
                        }
                        lines = new ArrayList<String>(Arrays.asList(
                                ChatColor.GRAY + date,
                                "§1 ",
                                "§cTop Kills",
                                "§5",
                                "§e--------------",
                                "§8 "
                        ));
                        if (KillTop.KILL_TOP.size() == 1) {
                            lines.add("§eTOP 1 : §r" + TeamsManager.getPrefixColor(tops[0]) + tops[0].getName());
                        }else if (KillTop.KILL_TOP.size() == 2) {
                            lines.add("§eTOP 1 : §r" + TeamsManager.getPrefixColor(tops[1]) + tops[1].getName());
                            lines.add("§6TOP 2 : §r" + TeamsManager.getPrefixColor(tops[0]) + tops[0].getName());
                        } else if (KillTop.KILL_TOP.size() >= 3) {
                            lines.add("§eTOP 1 : §r" + TeamsManager.getPrefixColor(tops[2]) + tops[2].getName());
                            lines.add("§6TOP 2 : §r" + TeamsManager.getPrefixColor(tops[1]) + tops[1].getName());
                            lines.add("§bTOP 3 : §r" + TeamsManager.getPrefixColor(tops[0]) + tops[0].getName());
                        }
                        lines.addAll(Arrays.asList(
                                "§7 ",
                                ChatColor.YELLOW + serv_ip
                        ));
                        custom.setLines(lines);
                    }
                }
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }
}
