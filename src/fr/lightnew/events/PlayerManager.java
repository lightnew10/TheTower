package fr.lightnew.events;

import fr.lightnew.TheTower;
import fr.lightnew.endgame.NPC;
import fr.lightnew.game.GameSettings;
import fr.lightnew.game.GameStats;
import fr.lightnew.items.ItemsActionBar;
import fr.lightnew.items.KitEquipment;
import fr.lightnew.scoreboard.ScoreboardGame;
import fr.lightnew.scoreboard.ScoreboardLobby;
import fr.lightnew.utils.*;
import fr.lightnew.utils.loc.LocationTeams;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlayerManager implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (int i = 0; i < 40; i++) {
            player.getInventory().clear(i);
        }
        /**/
        if (GameStats.inState(GameStats.LOBBY)) {
            event.setJoinMessage(ChatColor.GRAY + player.getName() + ChatColor.YELLOW + " a rejoint " + ChatColor.GRAY + "(" + ChatColor.GREEN + Bukkit.getOnlinePlayers().size() + ChatColor.AQUA + "/" + ChatColor.GREEN + TheTower.instance.getConfig().getInt("Game.max-players") + ChatColor.GRAY + ")");
            player.getInventory().setItem(4, ItemsActionBar.ITEM_TEAM);
            ScoreboardLobby.send(player);
            player.teleport(LocationTeams.lobby());
            if (LocationTeams.lobby() != null) {
                player.teleport(LocationTeams.lobby());
                TextComponent infoServ = new TextComponent("Informations du jeu");
                infoServ.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                infoServ.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Cliquez pour voir les informations").create()));
                infoServ.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/infothetower"));
                player.spigot().sendMessage(infoServ);
            }
            player.setGameMode(GameMode.ADVENTURE);
        }
        /**/
        if  (GameStats.inState(GameStats.GAME)) {
            TeamEnum getTeam = TeamsManager.getTeamRescue(player);
            if (getTeam == TeamEnum.RED)  {
                event.setJoinMessage(ChatColor.YELLOW + player.getName() + " a re-rejoint");
                TeamsManager.addPlayerInTeamRed(player);
                defaultParamReJoin(player);
                player.setGameMode(GameMode.SURVIVAL);
            }
            if (getTeam == TeamEnum.BLUE) {
                event.setJoinMessage(ChatColor.YELLOW + player.getName() + " a re-rejoint");
                TeamsManager.addPlayerInTeamBlue(player);
                defaultParamReJoin(player);
                player.setGameMode(GameMode.SURVIVAL);
            }
            if (getTeam == TeamEnum.NOTEAM) {
                event.setJoinMessage("");
                player.sendMessage(ChatColor.GRAY + "Vous êtes spectateur");
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
        /**/
        if (GameStats.inState(GameStats.END)) {
            event.setJoinMessage("");
            player.kickPlayer("La partie est fini !");
        }

        GameSettings.tablist(player);
    }

    private void defaultParamReJoin(Player player) {
        GameSettings.defaultStuff(player);
        GameSettings.sendPlayersToLocationTeams(player);
        GameSettings.sendActionbarTimer(player);
        ScoreboardGame.send(player);
        GameSettings.sendPlayersToTeams(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.YELLOW + player.getName() + " a quitté");
        ChangeNameTag.changePrefix(player, "", ChangeTeamAction.DESTROY);
        TeamsManager.removePlayerAllTeam(player);
    }
}