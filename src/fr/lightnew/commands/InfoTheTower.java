package fr.lightnew.commands;

import fr.lightnew.TheTower;
import fr.lightnew.endgame.Holo;
import fr.lightnew.statistiques.KillTop;
import fr.lightnew.statistiques.StatKiller;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoTheTower implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String info = TheTower.instance.getConfig().getString("message-info-thetower"); // Ow putin "TheTower.instance". J'ai une envie de meurtre :-)
            player.sendMessage(info.replace('&', '§'));
        }
        return false;
    }
}
