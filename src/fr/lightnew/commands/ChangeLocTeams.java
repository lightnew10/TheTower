package fr.lightnew.commands;

import fr.lightnew.TheTower;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeLocTeams implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "Faite ceci -> " + ChatColor.RESET + "/locteam <red|blue>");
                }
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("blue")) {
                        TheTower.instance.getConfig().set("red-team.X", player.getLocation().getX());
                        TheTower.instance.getConfig().set("red-team.Y", player.getLocation().getY());
                        TheTower.instance.getConfig().set("red-team.Z", player.getLocation().getZ());
                        TheTower.instance.getConfig().set("red-team.Pitch", player.getLocation().getPitch());
                        TheTower.instance.getConfig().set("red-team.Yaw", player.getLocation().getYaw());
                        TheTower.instance.saveConfig();
                        player.sendMessage(ChatColor.YELLOW + "Fait pour l'équipe " + ChatColor.BLUE + "Bleu");
                    } else if (args[0].equalsIgnoreCase("red")) {
                        TheTower.instance.getConfig().set("blue-team.X", player.getLocation().getX());
                        TheTower.instance.getConfig().set("blue-team.Y", player.getLocation().getY());
                        TheTower.instance.getConfig().set("blue-team.Z", player.getLocation().getZ());
                        TheTower.instance.getConfig().set("blue-team.Pitch", player.getLocation().getPitch());
                        TheTower.instance.getConfig().set("blue-team.Yaw", player.getLocation().getYaw());
                        TheTower.instance.saveConfig();
                        player.sendMessage(ChatColor.YELLOW + "Fait pour l'équipe " + ChatColor.RED + "Rouge");
                    } else player.sendMessage(ChatColor.RED + "Mettez un nom correct (blue|red)");

                }
            } else player.sendMessage(ChatColor.RED + "Vous avez pas la permission de faire ceci !");
        }
        return false;
    }
}
