package fr.lightnew.game;

import fr.lightnew.TheTower;
import fr.lightnew.endgame.PodiumFakePlayer;
import fr.lightnew.gameevent.ChestGameEvent;
import fr.lightnew.gameevent.WitherGameEvent;
import fr.lightnew.statistiques.KillTop;
import fr.lightnew.statistiques.StatKiller;
import fr.lightnew.utils.ActionBar;
import fr.lightnew.utils.TeamsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

public class Timers {

    public static int ti = 11;
    public static void timerPreStart() {
        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() >= TheTower.instance.getConfig().getInt("Game.min-player-launch-game")) {
                    if (ti == 10)
                        Bukkit.broadcastMessage("10"+ChatColor.YELLOW+" secondes !");
                    if (ti <= 5 && ti >= 1)
                        Bukkit.broadcastMessage(ti +""+ ChatColor.YELLOW + " secondes !");
                    if (ti == 0) {
                        cancel();
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "C'est parti !");
                        Game.startSettings();
                    }
                } else {
                    ti = 11;
                }
                ti--;
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }
    public static void timerForceStart() {
        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                    if (ti == 10)
                        Bukkit.broadcastMessage("10"+ChatColor.YELLOW+" secondes !");
                    if (ti <= 5 && ti >= 1)
                        Bukkit.broadcastMessage(ti +""+ ChatColor.YELLOW + " secondes !");
                    if (ti == 0) {
                        cancel();
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "C'est parti !");
                        Game.startSettings();
                    }
                ti--;
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }

    /*
    * Timer in game if score is not reached
    */
    public static double timerGame = 60*60;

    public static Double getTimerGame() {
        return timerGame;
    }
    public static int timerGameAround(){return getTimerGame().intValue();}
    public static String timerFormatHour(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public static void timerInGame() {
        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (GameStats.inState(GameStats.GAME)) {
                    //spawn wither 50:00
                    if (timerGame == 3300.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 5 minutes !");
                    if (timerGame == 3004.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 4 secondes !");
                    if (timerGame == 3003.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 3 secondes !");
                    if (timerGame == 3002.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 2 secondes !");
                    if (timerGame == 3001.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 1 seconde !");
                    if (timerGame == 3000.0) {
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither a SPAWN !");
                        WitherGameEvent.spawnWither();
                    }
                    //spawn wither 35:00
                    if (timerGame == 2400.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 5 minutes !");
                    if (timerGame == 2104.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 4 secondes !");
                    if (timerGame == 2103.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 3 secondes !");
                    if (timerGame == 2102.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 2 secondes !");
                    if (timerGame == 2101.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 1 seconde !");
                    if (timerGame == 2100.0) {
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither a SPAWN !");
                        WitherGameEvent.spawnWither();
                    }
                    //spawn wither 20:00
                    if (timerGame == 1500.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 5 minutes !");
                    if (timerGame == 1204.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 4 secondes !");
                    if (timerGame == 1203.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 3 secondes !");
                    if (timerGame == 1202.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 2 secondes !");
                    if (timerGame == 1201.0)
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither va spawn dans 1 seconde !");
                    if (timerGame == 1200.0) {
                        Bukkit.broadcastMessage(ChatColor.RED + "Le Wither a SPAWN !");
                        WitherGameEvent.spawnWither();
                    }

                    if (timerGame == 3600.0)
                        ChestGameEvent.addItemInChest();
                    //recharge des coffres 20:00
                    if (timerGame == 3000.0 || timerGame == 2400.0 || timerGame == 1200.0 || timerGame == 600.0 || timerGame == 300.0) {
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "Rechargement des coffres ! ");
                        ChestGameEvent.addItemInChest();
                    }


                    //timer end game
                    if (timerGame == 10.0 || timerGame == 5.0)
                        Bukkit.broadcastMessage(TheTower.instance.getConfig().getString("messages-timer.timer-game-10s-5s").replace('&', '§').replace("%timer%", String.valueOf(timerGameAround())).replace("%timer_decimal%", String.valueOf(getTimerGame())));
                    if (timerGame < 5.0)
                        Bukkit.broadcastMessage(TheTower.instance.getConfig().getString("messages-timer.timer-game-10s-5s").replace('&', '§').replace("%timer%", String.valueOf(timerGameAround())).replace("%timer_decimal%", String.valueOf(getTimerGame())));
                    if (timerGame == 0.0) {
                        Collection<? extends Player> p = Bukkit.getOnlinePlayers();
                        for (Player player : p)
                            ActionBar.sendActionBar(player, "");
                        GameSettings.launchEndGame();
                        cancel();
                    }

                    if (TeamsManager.list_team_blue.size() == 0 || TeamsManager.list_team_red.size() == 0) {
                        Collection<? extends Player> p = Bukkit.getOnlinePlayers();
                        for (Player player : p)
                            ActionBar.sendActionBar(player, "");
                        cancel();
                        Bukkit.broadcastMessage(ChatColor.RED  + "Il y'a plus de joueur dans l'équipe d'en face !");
                        GameSettings.launchEndGame();
                    }

                    timerGame--;
                }else cancel();
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }

    private static int timePreEndGame = 5;
    public static void timerPreEnd(){
        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (timePreEndGame == 4) {
                    Collection<? extends Player> p = Bukkit.getOnlinePlayers();
                    //Top Killer
                    KillTop.KILL_TOP.clear();
                    for (Player player : p) {
                        KillTop.KILL_TOP.put(player, StatKiller.getKill(player));
                    }
                    //Place NPCs
                    PodiumFakePlayer.send(KillTop.getTop(3));
                }
                if (timePreEndGame == 2) {
                    Collection<? extends Player> p = Bukkit.getOnlinePlayers();
                    Location loc = new Location(Bukkit.getWorld("world"), 0.5 ,20 ,0.5 ,-180 ,(float) -0.7);
                    for (Player player : p) {
                        player.teleport(loc);
                        GameSettings.sendScoreBoard(player);
                        //
                        Player[] tops = KillTop.getTop(3);
                        if (KillTop.KILL_TOP.size() >= 2) {
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "-------------------------------------------\n" +
                                    ChatColor.YELLOW + "\n                      TOP 1 " + ChatColor.GRAY + "" + ChatColor.BOLD + "- " + ChatColor.RESET + TeamsManager.getPrefixColor(tops[1]) + tops[1].getName() + ChatColor.RESET + ChatColor.GRAY + ChatColor.BOLD + " - " + StatKiller.getKill(tops[1]) + "\n" +
                                    ChatColor.GOLD + "                      TOP 2 " + ChatColor.GRAY + "" + ChatColor.BOLD + "- " + ChatColor.RESET + TeamsManager.getPrefixColor(tops[0]) + tops[0].getName() + ChatColor.RESET + ChatColor.GRAY + ChatColor.BOLD + " - " + StatKiller.getKill(tops[0]) + "\n" +
                                    ChatColor.GREEN + "\n" + ChatColor.BOLD + "\n-------------------------------------------");
                        }
                        if (KillTop.KILL_TOP.size() >= 3) {
                            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "-------------------------------------------\n" +
                                    ChatColor.YELLOW + "\n                      TOP 1 " + ChatColor.GRAY + "" + ChatColor.BOLD + "- " + ChatColor.RESET + TeamsManager.getPrefixColor(tops[2]) + tops[2].getName() + ChatColor.RESET + ChatColor.GRAY + ChatColor.BOLD + " - " + StatKiller.getKill(tops[2]) + "\n" +
                                    ChatColor.GOLD + "                      TOP 2 " + ChatColor.GRAY + "" + ChatColor.BOLD + "- " + ChatColor.RESET + TeamsManager.getPrefixColor(tops[1]) + tops[1].getName() + ChatColor.RESET + ChatColor.GRAY + ChatColor.BOLD + " - " + StatKiller.getKill(tops[1]) + "\n" +
                                    ChatColor.RED + "                      TOP 3 " + ChatColor.GRAY + "" + ChatColor.BOLD + "- " + ChatColor.RESET + TeamsManager.getPrefixColor(tops[0]) + tops[0].getName() + ChatColor.RESET + ChatColor.GRAY + ChatColor.BOLD + " - " + StatKiller.getKill(tops[0]) + "\n" +
                                    ChatColor.GREEN + "\n" + ChatColor.BOLD + "\n-------------------------------------------");
                        }
                    }
                }
                if (timePreEndGame == 0) {
                    cancel();
                    timerEnd();
                }
                timePreEndGame--;
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }

    private static int timeEndGame = 20;
    public static void timerEnd() {
        Location loc = new Location(Bukkit.getWorld("world"), 0.353, 18, -23.3);
        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (timeEndGame == 5) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Bien joué a tout le monde ! \n" + ChatColor.RED + "Le serveur va redémarrer !");
                }
                if (timeEndGame == 0) {
                    cancel();
                    //Bukkit.getServer().shutdown();
                }

                GameSettings.launchFireWorks(loc);
                timeEndGame--;
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }

}
