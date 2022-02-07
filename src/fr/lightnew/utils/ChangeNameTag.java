package fr.lightnew.utils;

import com.mojang.authlib.GameProfile;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;

public class ChangeNameTag {

    public static Team team;
    private static Scoreboard scoreboard;

    public static void changePrefix(Player player, String prefix, ChangeTeamAction action){
        if (player.getScoreboard() == null || prefix == null || action == null)
            return;
        scoreboard = player.getScoreboard();

        if (scoreboard.getTeam(player.getName()) == null) {
            scoreboard.registerNewTeam(player.getName());
        }

        team = scoreboard.getTeam(player.getName());
        team.setPrefix(prefix);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);

        switch (action) {
            case CREATE:
                team.addPlayer(player);
                team.setAllowFriendlyFire(false);
                break;
            case UPDATE:
                team.unregister();
                scoreboard.registerNewTeam(player.getName());
                team = scoreboard.getTeam(player.getName());
                team.setAllowFriendlyFire(false);
                team.setPrefix(prefix);
                team.setNameTagVisibility(NameTagVisibility.ALWAYS);
                team.addPlayer(player);
                player.setDisplayName(team.getPrefix() + player.getName());
                break;
            case DESTROY:
                team.unregister();
                player.setDisplayName(player.getName());
                break;
        }
    }

    public static void changeColorName(Player player, ChatColor color, ChangeTeamAction action){
        if (player.getScoreboard() == null || action == null)
            return;
        scoreboard = player.getScoreboard();

        if (scoreboard.getTeam(player.getName()) == null)
            scoreboard.registerNewTeam(player.getName());
        team = scoreboard.getTeam(player.getName());
        team.setPrefix(color.toString());
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        team.setAllowFriendlyFire(false);

        switch (action) {
            case CREATE:
                team.addPlayer(player);
                team.setAllowFriendlyFire(false);
                break;
            case UPDATE:
                team.unregister();
                scoreboard.registerNewTeam(player.getName());
                team = scoreboard.getTeam(player.getName());
                team.setPrefix(color.toString());
                team.setNameTagVisibility(NameTagVisibility.ALWAYS);
                team.addPlayer(player);
                team.setAllowFriendlyFire(false);
                break;
            case DESTROY:
                team.unregister();
                break;
        }
    }

    public static void changePrefixNameAndColor(Player player, ChatColor color, String prefix, ChangeTeamAction action){
        if (player.getScoreboard() == null || prefix == null || action == null)
            return;
        scoreboard = player.getScoreboard();

        if (scoreboard.getTeam(player.getName()) == null)
            scoreboard.registerNewTeam(player.getName());
        team = scoreboard.getTeam(player.getName());
        team.setPrefix(color.toString() + prefix);
        team.setNameTagVisibility(NameTagVisibility.ALWAYS);
        team.setAllowFriendlyFire(false);

        switch (action) {
            case CREATE:
                team.addPlayer(player);
                break;
            case UPDATE:
                team.unregister();
                scoreboard.registerNewTeam(player.getName());
                team = scoreboard.getTeam(player.getName());
                team.setAllowFriendlyFire(false);
                team.setPrefix(color + prefix);
                team.setNameTagVisibility(NameTagVisibility.ALWAYS);
                team.addPlayer(player);
                break;
            case DESTROY:
                team.unregister();
                break;
        }
    }

    public static void setGameprofileName(Player player) {
        String name = player.getName();
        try {
            GameProfile playerProfile = ((CraftPlayer) player).getHandle().getProfile();
            Field ff = playerProfile.getClass().getDeclaredField(name);
            ff.setAccessible(true);
            ff.set(playerProfile, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void setDefaultGameprofileName(Player player) {
        String name = player.getName();
        try {
            GameProfile playerProfile = ((CraftPlayer) player).getHandle().getProfile();
            Field ff = playerProfile.getClass().getDeclaredField(name);
            ff.setAccessible(true);
            ff.set(playerProfile, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
