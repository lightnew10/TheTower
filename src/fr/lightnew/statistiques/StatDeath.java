package fr.lightnew.statistiques;

import fr.lightnew.TheTower;
import fr.lightnew.utils.CreateFiles;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class StatDeath {

    public static void addDeath(Player player, int amount) {
        File file = new File(TheTower.instance.getDataFolder() + "/PlayerData", player.getName() + ".yml");
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            int playerDeath = config.getInt("Player-info.death");
            int result = playerDeath + amount;
            config.set("Player-info.death", result);
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getDeath(Player player) {
        File file = new File(TheTower.instance.getDataFolder() + "/PlayerData", player.getName() + ".yml");
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            int playerDeath = config.getInt("Player-info.death");
            return playerDeath;
        } else return 0;
    }
}
