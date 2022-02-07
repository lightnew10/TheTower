package fr.lightnew.gameevent;


import fr.lightnew.TheTower;
import fr.lightnew.items.KitEquipment;
import fr.lightnew.utils.TeamsManager;
import fr.lightnew.utils.Title;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.List;

public class WitherGameEvent {
    private static Location locWither() {
        FileConfiguration config = TheTower.instance.getConfig();
        String loc = "wither-event.location.";
        World world = Bukkit.getWorld(config.getString(loc+"World"));
        double x = config.getDouble(loc+"X");
        double y = config.getDouble(loc+"Y");
        double z = config.getDouble(loc+"Z");
        return new Location(world,x,y,z);
    }
    public static Boolean withIsSpawn = false;
    private static String name = TheTower.instance.getConfig().getString("wither-event.name");

    private static void setAI(LivingEntity entity, boolean hasAi) {
        EntityLiving handle = ((CraftLivingEntity) entity).getHandle();
        handle.getDataWatcher().watch(15, (byte) (hasAi ? 0 : 1));
    }
    private static Double getHealth = 600.0;
    private static boolean up1 = false;
    private static boolean up2 = false;
    private static boolean up3 = false;

    private static String levelUpgrade() {
        String result = "";
        if (!up1)
            result = "1";
        else
            result = "2";
        return result;
    }

    public static void spawnWither() {
        Wither wither = locWither().getWorld().spawn(locWither(), Wither.class);
        wither.setCustomName(name.replace('&','§').replace("%health%", String.valueOf(wither.getHealth())));
        setAI(wither, false);

        if (withIsSpawn)
            wither.remove();
        withIsSpawn = true;

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                if (!wither.isDead()) {
                    getHealth = wither.getHealth();
                    wither.setCustomName(name.replace('&','§').replace("%health%", String.valueOf(getHealth.intValue())));
                } else {
                    cancel();
                    Player killer = wither.getKiller();
                    String team = TeamsManager.getTeam(killer);

                    List<Player> blue = TeamsManager.list_team_blue;
                    List<Player> red = TeamsManager.list_team_red;

                    //send for all player title
                    Collection<? extends Player> p = Bukkit.getOnlinePlayers();
                    for (Player player : p)
                        Title.sendTitle(player, 3, 25, 3, ChatColor.YELLOW + "L'équipe " + team + ChatColor.YELLOW + " à remporter l'upgrade", ChatColor.RED + "Niveau " + levelUpgrade());

                    //send new equipment players
                    if (blue.contains(killer)) {
                        if (!up1) {
                            up1 = true;
                            TeamUpgrade.setBlueUpgrade1();
                        } else if (!up2){
                            up2 = true;
                            TeamUpgrade.setBlueUpgrade2();
                        } else if (!up3){
                            up3 = true;
                            TeamUpgrade.setBlueUpgrade3();
                        }
                        for (Player player_in_team : blue)
                            KitEquipment.sendEquipmentBlue(player_in_team);
                    }
                    if (red.contains(killer)) {
                        if (!up1) {
                            up1 = true;
                            TeamUpgrade.setRedUpgrade1();
                        } else if (!up2){
                            up2 = true;
                            TeamUpgrade.setRedUpgrade2();
                        } else  {
                            up3 = true;
                            TeamUpgrade.setRedUpgrade3();
                        }
                        for (Player player_in_team : red) {
                            KitEquipment.sendEquipmentRed(player_in_team);
                        }
                    }
                    withIsSpawn = false;
                }
            }
        }.runTaskTimer(TheTower.instance, 0, 10);
        wither.setCustomNameVisible(true);
    }
}
