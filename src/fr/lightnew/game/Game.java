package fr.lightnew.game;

import fr.lightnew.scoreboard.ScoreboardGame;
import fr.lightnew.utils.CreateFiles;
import fr.lightnew.utils.TeamsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class Game {
    /*START*/
    public static void startSettings() {
        //change state GAME STATS
        GameStats.setState(GameStats.GAME);
        Collection<? extends Player> p = Bukkit.getOnlinePlayers();
        for (Player player : p) {
            //place players in teams
            GameSettings.sendPlayersToTeams(player);
            //send scoreboard
            ScoreboardGame.send(player);
            //send action bar
            GameSettings.sendActionbarTimer(player);
            //tp player to location team
            GameSettings.sendPlayersToLocationTeams(player);
            //give defaultkit to player
            GameSettings.defaultStuff(player);
        }
        //create files for teams
        CreateFiles.createFileTeam();
        //clear items adn monsters
        GameSettings.clearLag();
        //start timer game (1h)
        Timers.timerInGame();
    }

    /*EVENT RANDOM*/
}
