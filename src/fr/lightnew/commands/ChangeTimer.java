package fr.lightnew.commands;

import fr.lightnew.game.Timers;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChangeTimer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender.isOp()) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "fait /timer <time>");
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Vous avez modif en " + args[0]);
                Timers.timerGame = Double.parseDouble(args[0]);
            }
        }
        return false;
    }
}
