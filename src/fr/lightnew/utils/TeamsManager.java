package fr.lightnew.utils;

import fr.lightnew.TheTower;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TeamsManager {

    /*
     * set default full team (int)
     */
    public static int fullTeamRed = 5;
    public static int fullTeamBlue = 5;


    /**
     *
     * TEMPORARY TEAMS
     *
     **/


    /*
     * Set player in team and add all options
     */
    private static ArrayList<Player> temp_team_red = new ArrayList<>();
    private static ArrayList<Player> temp_team_blue = new ArrayList<>();

    /*set player in game stat -> GAME*/
    public static void tempSetPlayerInTeamRed(Player player) {
        temp_team_blue.remove(player);
        temp_team_red.add(player);
        player.setPlayerListName(ChatColor.RED + "R " + player.getName());
        ChangeNameTag.changePrefixNameAndColor(player, ChatColor.RED, "R ", ChangeTeamAction.DESTROY);
        ChangeNameTag.changePrefixNameAndColor(player, ChatColor.RED, "R ", ChangeTeamAction.CREATE);
    }
    public static void tempSetPlayerInTeamBlue(Player player) {
        temp_team_red.remove(player);
        temp_team_blue.add(player);
        player.setPlayerListName(ChatColor.BLUE + "B " + player.getName());
        ChangeNameTag.changePrefixNameAndColor(player, ChatColor.BLUE, "B ", ChangeTeamAction.DESTROY);
        ChangeNameTag.changePrefixNameAndColor(player, ChatColor.BLUE, "B ", ChangeTeamAction.CREATE);
    }

    public static Boolean getPlayerInTempTeamRed(Player player){
        boolean result = false;
        if (temp_team_red.contains(player))
            result = true;
        return result;
    }
    public static Boolean getPlayerInTempTeamBlue(Player player){
        boolean result = false;
        if (temp_team_blue.contains(player))
            result = true;
        return result;
    }

    public static Boolean getPlayerInNothingTempList(Player player) {
        boolean result = true;
        if (getPlayerInTempTeamRed(player))
            result = false;
        if (getPlayerInTempTeamBlue(player))
            result = false;
        return result;
    }

    /*
    * get team in game-state in lobby
    */
    public static String getTempTeam(Player player) {
        String nothing = "sans équipe";
        if (temp_team_blue.contains(player))
            return ChatColor.BLUE + "Bleu";
        if (temp_team_red.contains(player))
            return ChatColor.RED + "Rouge";
        return nothing;
    }

    /**
     *
     * TEAMS
     *
     **/

    /*
     * ListPlayer in team
     */
    public static ArrayList<Player> list_team_red = new ArrayList();
    public static ArrayList<Player> list_team_blue = new ArrayList();

    /*
     * HashMap Player for add player in team if player leave
     */
    public static HashMap<Player, String> team_red = new HashMap<>();
    public static HashMap<Player, String> team_blue = new HashMap<>();

    /*
     * get Player if in team
     */
    public static Boolean playerContainsInTeam(Player player) {
        boolean result = false;
        if (team_red.containsKey(player))
            result = true;
        if (team_blue.containsKey(player))
            result = true;
        return result;
    }

    public static Boolean emptyTeam() {
        boolean result = false;
        if (list_team_red.size() == 0)
            result = true;
        if (list_team_blue.size() == 0)
            result = true;
        return result;
    }

    /*set player in game stat -> GAME*/
    public static void addPlayerInTeamRed(Player player) {
        if (!getTeamRedIsFull()) {
            list_team_red.add(player);
            team_red.put(player, "RED");
            ChangeNameTag.changePrefixNameAndColor(player, ChatColor.RED, "", ChangeTeamAction.DESTROY);
            ChangeNameTag.changePrefixNameAndColor(player, ChatColor.RED, getPrefix(player), ChangeTeamAction.CREATE);
            player.setPlayerListName(getPrefix(player) + player.getName());
            CreateFiles.create(player);
        }
    }
    public static void addPlayerInTeamBlue(Player player) {
        if (!getTeamBlueIsFull()) {
            list_team_blue.add(player);
            team_blue.put(player, "BLUE");
            ChangeNameTag.changePrefixNameAndColor(player, ChatColor.BLUE, "", ChangeTeamAction.DESTROY);
            ChangeNameTag.changePrefixNameAndColor(player, ChatColor.BLUE, getPrefix(player), ChangeTeamAction.CREATE);
            player.setPlayerListName(getPrefix(player) + player.getName());
            CreateFiles.create(player);
        }
    }

    /**
     * Send players in random teams
     */
    public static void addPlayerInRandomTeams(Player player) {
        if (getPlayerInNothingTempList(player)) {
            if (Bukkit.getOnlinePlayers().size() < TheTower.instance.getConfig().getInt("Game.max-players")) {
                int r = Bukkit.getOnlinePlayers().size() / 2;
                TeamsManager.fullTeamBlue = r;
                TeamsManager.fullTeamRed = r;
            }
            if (TeamsManager.getTeamBlueIsFull()) {
                TeamsManager.addPlayerInTeamRed(player);
            } else TeamsManager.addPlayerInTeamBlue(player);
        }
    }
    /**set players in force random teams*/
    public static void forceRandomTeam(Player player) {
        if (Bukkit.getOnlinePlayers().size() < TheTower.instance.getConfig().getInt("Game.max-players")) {
            int r = Bukkit.getOnlinePlayers().size() / 2;
            TeamsManager.fullTeamBlue = r;
            TeamsManager.fullTeamRed = r;
        }
        if (TeamsManager.getTeamBlueIsFull()) {
            TeamsManager.addPlayerInTeamRed(player);
        } else TeamsManager.addPlayerInTeamBlue(player);
    }

    /*
    * Get if team is full
    */
    public static Boolean getTeamRedIsFull() {
        boolean result = false;
        if (list_team_red.size() >= fullTeamRed || team_red.size() >= fullTeamRed)
            result = true;
        return result;
    }
    public static Boolean getTeamBlueIsFull() {
        boolean result = false;
        if (list_team_blue.size() >= fullTeamBlue || team_blue.size() >= fullTeamBlue)
            result = true;
        return result;
    }

    /*
     *  remove player team
     */
    public static void removePlayerAllTeam(Player player) {
        list_team_blue.remove(player);
        list_team_red.remove(player);
        team_red.remove(player);
        team_blue.remove(player);
    }

    /*
    * Get Name Team player
    */
    public static String getTeam(Player player) {
        String nothing = "sans équipe";
        if (team_blue.containsKey(player))
            return ChatColor.BLUE + "Bleu";
        if (team_red.containsKey(player))
            return ChatColor.RED + "Rouge";
        return nothing;
    }

    public static String getTeams(Player player) {
        String nothing = "sans équipe";
        if (team_blue.containsKey(player))
            return "Bleu";
        if (team_red.containsKey(player))
            return "Rouge";
        return nothing;
    }

    /*
     * Get prefix Team player
     */
    public static String getPrefix(Player player) {
        String nothing = "sans prefix";
        if (team_blue.containsKey(player))
            return ChatColor.BLUE + "B ";
        if (team_red.containsKey(player))
            return ChatColor.RED + "R ";
        return nothing;
    }

    public static ChatColor getPrefixColor(Player player) {
        ChatColor nothing = ChatColor.RESET;
        if (team_blue.containsKey(player))
            return ChatColor.BLUE;
        if (team_red.containsKey(player))
            return ChatColor.RED;
        return nothing;
    }

    /*
    * GetHashMap & ArrayList
    */
    public static ArrayList<Player> getList_team_blue() {
        return list_team_blue;
    }
    public static ArrayList<Player> getList_team_red() {
        return list_team_red;
    }

    public static Boolean getTeam_blue(Player player) {
        boolean result = false;
        if (team_blue.containsKey(player))
            result = true;
        return result;
    }
    public static Boolean getTeam_red(Player player) {
        boolean result = false;
        if (team_red.containsKey(player))
            result = true;
        return result;
    }



    //Friendly fire
    public static Boolean playerInTeamTargetPlayer(Player player, Player target) {
        boolean result = false;
        if (getTeam(player).equals(getTeam(target)))
            result = true;
        return result;
    }

    /*RESCUE TEAM*/
    public static TeamEnum getTeamRescue(Player player) {
        TeamEnum result = TeamEnum.NOTEAM;
        File file = new File(TheTower.instance.getDataFolder() + "/PlayerData", player.getName() + ".yml");
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            String team = config.getString("Player-info.Team");
            if (Objects.equals(team, "Bleu"))
                result = TeamEnum.BLUE;
            if (Objects.equals(team, "Rouge"))
                result = TeamEnum.RED;
        }
        return result;
    }
}