package fr.lightnew.commands;

import fr.lightnew.game.Game;
import fr.lightnew.game.GameSettings;
import fr.lightnew.game.GameStats;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameStates implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Faites /game <lobby/game/end>" + "\nVotre GameStats -> " + GameStats.getState());
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("lobby")) {
                        if (GameStats.inState(GameStats.LOBBY)) {
                            player.sendMessage(ChatColor.RED + "Vous êtes déjà au gamestats lobby!");
                        } else {
                            GameStats.setState(GameStats.LOBBY);
                            player.sendMessage(ChatColor.GREEN + "Vous avez changer le gameStats en LOBBY");
                        }
                    }
                    if (args[0].equalsIgnoreCase("game")) {
                        if (GameStats.inState(GameStats.GAME)) {
                            player.sendMessage(ChatColor.RED + "Vous êtes déjà au gamestats game!");
                        } else {
                            GameStats.setState(GameStats.GAME);
                            player.sendMessage(ChatColor.GREEN + "Vous avez changer le gameStats en GAME");
                            Game.startSettings();
                        }
                    }
                    if (args[0].equalsIgnoreCase("end")) {
                        if (GameStats.inState(GameStats.END)) {
                            player.sendMessage(ChatColor.RED + "Vous êtes déjà au gamestats end!");
                        } else {
                            GameStats.setState(GameStats.END);
                            player.sendMessage(ChatColor.GREEN + "Vous avez changer le gameStats en END");
                        }
                    }
                    GameSettings.sendScoreBoard(player);
                }
            }
        }
        return false;
    }
}
