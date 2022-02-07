package fr.lightnew.commands;

import fr.lightnew.TheTower;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnLobby implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                if (args.length == 0) {
                    TheTower.instance.getConfig().set("lobby-loc.X", player.getLocation().getX());
                    TheTower.instance.getConfig().set("lobby-loc.Y", player.getLocation().getY());
                    TheTower.instance.getConfig().set("lobby-loc.Z", player.getLocation().getZ());
                    TheTower.instance.getConfig().set("lobby-loc.Pitch", player.getLocation().getPitch());
                    TheTower.instance.getConfig().set("lobby-loc.Yaw", player.getLocation().getYaw());
                    TheTower.instance.saveConfig();
                    player.sendMessage(ChatColor.YELLOW + "Vous avez posé le spawn !");
                }
            } else player.sendMessage(ChatColor.RED + "Vous avez pas la permission de faire ceci !");
        }
        return false;
    }
}
