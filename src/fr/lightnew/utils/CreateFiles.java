package fr.lightnew.utils;

import fr.lightnew.TheTower;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class CreateFiles {
    private static File folder = new File(TheTower.instance.getDataFolder() + "/PlayerData");
    private static File folderTeam = new File(TheTower.instance.getDataFolder() + "/Teams");

    public static void create(Player player) {
        File file = new File(TheTower.instance.getDataFolder() + "/PlayerData", player.getName() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.set("Player-info.uuid", player.getUniqueId().toString());
                config.set("Player-info.name", player.getName());
                config.set("Player-info.Team", TeamsManager.getTeams(player));
                config.set("Player-info.kill", 0);
                config.set("Player-info.death", 0);
                config.set("Player-info.points-put", 0);
                config.save(file);
            }catch (IOException e) {e.printStackTrace();}
        }
    }

    public static void removeFilesInFolder() {
        try {
            FileUtils.deleteDirectory(folder);
            FileUtils.deleteDirectory(folderTeam);
            TheTower.log(ChatColor.RED + "le dossier " + folder.getName() +" est supprimer");
            TheTower.log(ChatColor.RED + "le dossier " + folderTeam.getName() +" est supprimer");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFolder() {
        if (!folder.exists()) {
            if (folder.mkdir()) {
                TheTower.log(ChatColor.YELLOW + "Folder 'PlayerData' is create");
            }
        }
        if (!folderTeam.exists()) {
            if (folderTeam.mkdir()) {
                TheTower.log(ChatColor.YELLOW + "Folder 'Teams' is create");
            }
        }
    }

    public static void createFileTeam() {
        File red = new File(TheTower.instance.getDataFolder() + "/Teams", "Red.yml");
        File blue = new File(TheTower.instance.getDataFolder() + "/Teams", "Blue.yml");

        YamlConfiguration config_red = YamlConfiguration.loadConfiguration(red);
        YamlConfiguration config_blue = YamlConfiguration.loadConfiguration(blue);

        if (!red.exists()) {
            try {
                red.createNewFile();
                config_red.set("info.upgrade-1", false);
                config_red.set("info.upgrade-2", false);
                config_red.set("info.upgrade-3", false);
                config_red.set("info.points", 0);
                config_red.save(red);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!blue.exists()) {
            try {
                blue.createNewFile();
                config_blue.set("info.upgrade-1", false);
                config_blue.set("info.upgrade-2", false);
                config_blue.set("info.upgrade-3", false);
                config_blue.set("info.points", 0);
                config_blue.save(blue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
