package fr.lightnew;

import fr.lightnew.commands.*;
import fr.lightnew.commands.bases.GMC;
import fr.lightnew.commands.bases.GMS;
import fr.lightnew.commands.bases.Heal;
import fr.lightnew.events.*;
import fr.lightnew.game.GameStats;
import fr.lightnew.game.Timers;
import fr.lightnew.utils.CreateFiles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

public class TheTower extends JavaPlugin {
    /**FRENCH DEVELOPER**/

    public static TheTower instance;

    @Override
    public void onEnable() {
        instance=this;
        saveDefaultConfig();
        log(ChatColor.GREEN + "The Tower is Enable");

        //TODO LISTENER
        Bukkit.getPluginManager().registerEvents(new PlayerManager(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractions(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInventoryInteract(), this);
        Bukkit.getPluginManager().registerEvents(new CancelEvents(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new MovePlayerSpawnKill(), this);
        Bukkit.getPluginManager().registerEvents(new AntiPlaceBlock(), this);
        Bukkit.getPluginManager().registerEvents(new AddPointBut(), this);
        Bukkit.getPluginManager().registerEvents(new ChatFormat(), this);

        //TODO COMMANDS
        getCommand("forcestart").setExecutor(new ForceStart());
        getCommand("gmc").setExecutor(new GMC());
        getCommand("gms").setExecutor(new GMS());
        getCommand("heal").setExecutor(new Heal());
        getCommand("game").setExecutor(new GameStates());
        getCommand("infothetower").setExecutor(new InfoTheTower());
        getCommand("timer").setExecutor(new ChangeTimer());
        getCommand("changeloc").setExecutor(new ChangeLocTeams());
        getCommand("setspawn").setExecutor(new SetSpawnLobby());

        //TODO GAME STATE
        GameStats.setState(GameStats.LOBBY);
        Timers.timerPreStart();
        //TODO CREATE FILE
        CreateFiles.createFolder();
    }

    @Override
    public void onDisable() {
        log(ChatColor.RED + "The Tower is Disable");
        CreateFiles.removeFilesInFolder();
        removeAllBlock();
    }
    public void removeAllBlock() {
        for (Location loc : AntiPlaceBlock.removeAllBlock.values()) {
            Bukkit.getWorld("world").getBlockAt(loc).setType(Material.AIR);
        }
        AntiPlaceBlock.removeAllBlock.clear();
    }
    public static void log(String s) {Bukkit.getConsoleSender().sendMessage(s);}
}
