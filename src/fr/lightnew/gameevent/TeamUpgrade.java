package fr.lightnew.gameevent;

import fr.lightnew.TheTower;
import fr.lightnew.game.GameSettings;
import fr.lightnew.utils.TeamsManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamUpgrade {
    private static File red = new File(TheTower.instance.getDataFolder() + "/Teams", "Red.yml");
    private static File blue = new File(TheTower.instance.getDataFolder() + "/Teams", "Blue.yml");
    private static Boolean verifFiles() {
        boolean result = false;
        if (red.exists())
            result=true;
        if (blue.exists())
            result=true;
        return result;
    }
    private static String up1 = "info.upgrade-1";
    private static String up2 = "info.upgrade-2";
    private static String up3 = "info.upgrade-3";

    public static Boolean getRedUpgrade1() {
        boolean result = false;
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(red);
            result = config.getBoolean(up1);
        }
        return result;
    }
    public static Boolean getRedUpgrade2() {
        boolean result = false;
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(red);
            result = config.getBoolean(up2);
        }
        return result;
    }
    public static Boolean getRedUpgrade3() {
        boolean result = false;
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(red);
            result = config.getBoolean(up3);
        }
        return result;
    }

    public static Boolean getBlueUpgrade1() {
        boolean result = false;
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blue);
            result = config.getBoolean(up1);
        }
        return result;
    }
    public static Boolean getBlueUpgrade2() {
        boolean result = false;
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blue);
            result = config.getBoolean(up2);
        }
        return result;
    }
    public static Boolean getBlueUpgrade3() {
        boolean result = false;
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blue);
            result = config.getBoolean(up3);
        }
        return result;
    }

    public static void setRedUpgrade1() {
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(red);
            config.set(up1, true);
            try {
                config.save(red);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setRedUpgrade2() {
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(red);
            config.set(up2, true);
            try {
                config.save(red);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setRedUpgrade3() {
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(red);
            config.set(up3, true);
            try {
                config.save(red);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setBlueUpgrade1() {
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blue);
            config.set(up1, true);
            try {
                config.save(blue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setBlueUpgrade2() {
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blue);
            config.set(up2, true);
            try {
                config.save(blue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void setBlueUpgrade3() {
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blue);
            config.set(up3, true);
            try {
                config.save(blue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addPointRedTeam(int point) {
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(red);
            int oldPoint = config.getInt("info.points");
            int newPoint = oldPoint+point;
            if (newPoint == 10) {
                GameSettings.launchEndGame();
            } else {
                config.set("info.points", newPoint);
                try {
                    config.save(red);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void addPointBlueTeam(int point) {
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blue);
            int oldPoint = config.getInt("info.points");
            int newPoint = oldPoint+point;
            if (newPoint == 10) {
                GameSettings.launchEndGame();
            } else {
                config.set("info.points", newPoint);
                try {
                    config.save(blue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getPointRed() {
        int result = 0;
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(red);
            result = config.getInt("info.points");
        }
        return result;
    }
    public static int getPointBlue() {
        int result = 0;
        if (verifFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(blue);
            result = config.getInt("info.points");
        }
        return result;
    }

    public static List<Player> getWinner() {
        List<Player> result = new ArrayList<>();
        if (getPointRed() == 10)
            result = TeamsManager.list_team_red;
        if (getPointBlue() == 10)
            result = TeamsManager.list_team_blue;
        return result;
    }
    public static String getWinnerWithPoints() {
        String result = "EGALITE";
        if (getPointBlue() > getPointRed())
            result = "BLEU";
        if (getPointRed() > getPointBlue())
            result = "ROUGE";
        if (getPointBlue() == getPointRed())
            result = "EGALITE";
        return result;
    }

}
