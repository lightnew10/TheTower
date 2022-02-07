package fr.lightnew.commands;

import fr.lightnew.game.Timers;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceStart implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.isOp()) {
                Timers.timerForceStart();
                player.sendMessage(ChatColor.RED + "Vous avez forcer le début de la game");
            } else return true;
        }
        return false;
    }
}
