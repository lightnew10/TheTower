package fr.lightnew.game;

import fr.lightnew.TheTower;
import fr.lightnew.gameevent.TeamUpgrade;
import fr.lightnew.items.KitEquipment;
import fr.lightnew.scoreboard.ScoreBoardEnd;
import fr.lightnew.scoreboard.ScoreboardGame;
import fr.lightnew.scoreboard.ScoreboardLobby;
import fr.lightnew.statistiques.StatDeath;
import fr.lightnew.statistiques.StatKiller;
import fr.lightnew.utils.ActionBar;
import fr.lightnew.utils.TabList;
import fr.lightnew.utils.TeamsManager;
import fr.lightnew.utils.Title;
import fr.lightnew.utils.loc.LocationTeams;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class GameSettings {

    public static void tablist(Player player) {
        //lobby
        String header_lobby = TheTower.instance.getConfig().getString("tablist.lobby.header").replace("%kill%", String.valueOf(StatKiller.getKill(player))
                .replace("%death%", String.valueOf(StatDeath.getDeath(player))).replace('&', '§'));
        String footer_lobby = TheTower.instance.getConfig().getString("tablist.lobby.footer").replace("%kill%", String.valueOf(StatKiller.getKill(player))
                .replace("%death%", String.valueOf(StatDeath.getDeath(player))).replace('&', '§'));
        //game
        String header_game = TheTower.instance.getConfig().getString("tablist.game.header").replace("%kill%", String.valueOf(StatKiller.getKill(player))
                .replace("%death%", String.valueOf(StatDeath.getDeath(player))).replace('&', '§'));
        String footer_game = TheTower.instance.getConfig().getString("tablist.game.footer").replace("%kill%", String.valueOf(StatKiller.getKill(player))
                .replace("%death%", String.valueOf(StatDeath.getDeath(player))).replace('&', '§'));
        //end
        String header_end = TheTower.instance.getConfig().getString("tablist.end.header").replace("%kill%", String.valueOf(StatKiller.getKill(player))
                .replace("%death%", String.valueOf(StatDeath.getDeath(player))).replace('&', '§'));
        String footer_end = TheTower.instance.getConfig().getString("tablist.end.footer").replace("%kill%", String.valueOf(StatKiller.getKill(player))
                .replace("%death%", String.valueOf(StatDeath.getDeath(player))).replace('&', '§'));
        BukkitTask task = new BukkitRunnable() {

            /**
             * FOOTER -> POINTS TEAMS + IP SERV | HEADER -> NAME GAME + SPACE
             **/
            @Override
            public void run() {
                if (player.isOnline()) {
                    if (GameStats.inState(GameStats.LOBBY)) {
                            TabList.sendTabTitle(player, header_lobby, footer_lobby);
                    }

                    if (GameStats.inState(GameStats.GAME)) {
                        TabList.sendTabTitle(player, header_game, footer_game);
                    }

                    if (GameStats.inState(GameStats.END)) {
                        TabList.sendTabTitle(player, header_end, footer_end);
                    }
                }else cancel();
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }

    public static void sendScoreBoard(Player player) {
        if (GameStats.inState(GameStats.LOBBY)) {
            ScoreBoardEnd.boards.remove(player);
            ScoreboardGame.boards.remove(player);
            ScoreboardLobby.send(player);
        }
        if (GameStats.inState(GameStats.GAME)) {
            ScoreboardLobby.boards.remove(player);
            ScoreBoardEnd.boards.remove(player);
            ScoreboardGame.send(player);
        }
        if (GameStats.inState(GameStats.END)) {
            ScoreboardLobby.boards.remove(player);
            ScoreboardGame.boards.remove(player);
            ScoreBoardEnd.send(player);
        }
    }

    public static void sendActionbarTimer(Player player) {
        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (Timers.timerGame == 0.0)
                    cancel();
                else ActionBar.sendActionBar(player, ChatColor.YELLOW + String.valueOf(Timers.timerFormatHour(Timers.timerGameAround())));
            }
        }.runTaskTimer(TheTower.instance, 0, 10);
    }

    public static void sendPlayersToLocationTeams(Player player) {
        if (TeamsManager.getTeam_blue(player))
            if (LocationTeams.blueTeam() != null)
                player.teleport(LocationTeams.blueTeam());
        if (TeamsManager.getTeam_red(player))
            if (LocationTeams.redTeam() != null)
                player.teleport(LocationTeams.redTeam());
    }

    public static void defaultStuff(Player player) {
        player.getInventory().clear();
        //TODO GIVE STUFF
        if (TeamsManager.getTeam_red(player))
            KitEquipment.sendEquipmentRed(player);
        if (TeamsManager.getTeam_blue(player))
            KitEquipment.sendEquipmentBlue(player);
    }

    public static void sendPlayersToTeams(Player player) {
        if (!TeamsManager.emptyTeam()) {
            if (TeamsManager.playerContainsInTeam(player)) {
                if (TeamsManager.getPlayerInTempTeamBlue(player)) {
                    TeamsManager.addPlayerInTeamBlue(player);
                }
                if (TeamsManager.getPlayerInTempTeamRed(player)) {
                    TeamsManager.addPlayerInTeamRed(player);
                }
            } else TeamsManager.addPlayerInRandomTeams(player);
        } else TeamsManager.forceRandomTeam(player);
    }

    public static void clearLag() {
        List<Entity> Entity = Bukkit.getWorld("world").getEntities();

        int count = 0;
        for(Entity current : Entity) {
            if(current instanceof Item || current instanceof Monster) {
                count++;
                current.remove();
            }
        }
    }

    public static void launchEndGame() {
        GameStats.setState(GameStats.END);
        Collection<? extends Player> p = Bukkit.getOnlinePlayers();
        for (Player player : p) {
            if (TeamUpgrade.getWinner().contains(player)) {
                launchFireWorks(player.getLocation());
                if (TeamUpgrade.getWinner() == TeamsManager.list_team_red) {sendAllMessagesRedWinner(player);}
                if (TeamUpgrade.getWinner() == TeamsManager.list_team_blue) {sendAllMessagesBlueWinner(player);}
            } else {
                String team_winner = TeamUpgrade.getWinnerWithPoints();
                if (Objects.equals(team_winner, "ROUGE"))
                    sendAllMessagesRedWinner(player);
                if (Objects.equals(team_winner, "BLEU"))
                    sendAllMessagesBlueWinner(player);
                if (Objects.equals(team_winner, "EGALITE"))
                    sendAllMessagesEqualsWinner(player);
            }
        }
        clearLag();
        Timers.timerPreEnd();
    }

    private static void sendAllMessagesRedWinner(Player player){
        player.sendMessage(ChatColor.YELLOW + "====================\n" + "L'Équipe " + ChatColor.RED + "Rouge " + ChatColor.YELLOW + "a gagné la partie !" + ChatColor.YELLOW + "\n====================\n");
        Title.sendTitle(player, 3, 35, 3, ChatColor.YELLOW + "L'Équipe " + ChatColor.RED + "Rouge " + ChatColor.YELLOW + "a gagné !");
    }
    private static void sendAllMessagesBlueWinner(Player player){
        player.sendMessage(ChatColor.YELLOW + "====================\n" + "L'Équipe " + ChatColor.BLUE + "Bleu " + ChatColor.YELLOW + "a gagné la partie !" + ChatColor.YELLOW + "\n====================\n");
        Title.sendTitle(player, 3, 35, 3, ChatColor.YELLOW + "L'Équipe " + ChatColor.BLUE + "Bleu " + ChatColor.YELLOW + "a gagné !");
    }
    private static void sendAllMessagesEqualsWinner(Player player){
        player.sendMessage(ChatColor.YELLOW + "====================\n" + ChatColor.GRAY + "          EGALITE !" + ChatColor.YELLOW + "\n====================\n");
        Title.sendTitle(player, 3, 35, 3, ChatColor.GRAY + "EGALITE !");
    }

    private static int timeFireworks = 5;
    public static void launchFireWorks(Location location){
        Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(1);
        Color[] colors = new Color[] {Color.GREEN, Color.WHITE, Color.RED, Color.BLUE, Color.AQUA};
        for (int i = 0; i < 2; i++)
            fwm.addEffect(FireworkEffect.builder().withColor(colors).trail(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (timeFireworks == 0) {
                    cancel();
                    timeFireworks = 10;
                }

                if (timeFireworks == 8 || timeFireworks == 6 || timeFireworks == 4 || timeFireworks == 2)
                    for(int i = 0; i<1; i++){
                        Firework fw2 = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
                        fw2.setFireworkMeta(fwm);
                    }
                timeFireworks--;
            }
        }.runTaskTimer(TheTower.instance, 0, 10);
    }
}
