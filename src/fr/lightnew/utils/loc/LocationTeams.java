package fr.lightnew.utils.loc;

import fr.lightnew.TheTower;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationTeams {

    public static Location lobby() {
        World world = Bukkit.getWorld("world");
        double x = TheTower.instance.getConfig().getDouble("lobby-loc.X");
        double y = TheTower.instance.getConfig().getDouble("lobby-loc.Y");
        double z = TheTower.instance.getConfig().getDouble("lobby-loc.Z");
        float pitch = (float) TheTower.instance.getConfig().getDouble("lobby-loc.Pitch");
        float yaw = (float) TheTower.instance.getConfig().getDouble("lobby-loc.Yaw");
        return new Location(world,x,y,z,pitch,yaw);
    }

    public static Location redTeam() {
        World world = Bukkit.getWorld("world");
        double x = TheTower.instance.getConfig().getDouble("red-team.X");
        double y = TheTower.instance.getConfig().getDouble("red-team.Y");
        double z = TheTower.instance.getConfig().getDouble("red-team.Z");
        float pitch = (float) TheTower.instance.getConfig().getDouble("red-team.Pitch");
        float yaw = (float) TheTower.instance.getConfig().getDouble("red-team.Yaw");
        return new Location(world,x,y,z,pitch,yaw);
    }

    public static Location blueTeam() {
        World world = Bukkit.getWorld("world");
        double x = TheTower.instance.getConfig().getDouble("blue-team.X");
        double y = TheTower.instance.getConfig().getDouble("blue-team.Y");
        double z = TheTower.instance.getConfig().getDouble("blue-team.Z");
        float pitch = (float) TheTower.instance.getConfig().getDouble("blue-team.Pitch");
        float yaw = (float) TheTower.instance.getConfig().getDouble("blue-team.Yaw");
        return new Location(world,x,y,z,pitch,yaw);
    }
}
