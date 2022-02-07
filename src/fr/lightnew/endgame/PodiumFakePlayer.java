package fr.lightnew.endgame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PodiumFakePlayer {
    public static void send(Player... players) {
        Location loc3 = new Location(Bukkit.getWorld("world"), 4.007, 20, -21.957, (float)9.8, (float)5.5);
        Location loc1 = new Location(Bukkit.getWorld("world"), 0.575, 22, -21.994, (float)0.1, (float)9.8);
        Location loc2 = new Location(Bukkit.getWorld("world"), -2.983, 21, -22.004, (float)-8.6, (float)6.9);

        if (players.length == 1)
            NPC.createNPCForAllPlayer(players[0], loc1, players[0].getName(), true);
        if (players.length == 2) {
            NPC.createNPCForAllPlayer(players[1], loc1, players[1].getName(), true);
            NPC.createNPCForAllPlayer(players[0], loc2, players[0].getName(), true);
        }
        if (players.length >= 3) {
            NPC.createNPCForAllPlayer(players[3], loc1, players[3].getName(), true);
            NPC.createNPCForAllPlayer(players[1], loc2, players[1].getName(), true);
            NPC.createNPCForAllPlayer(players[0], loc3, players[0].getName(), true);
        }

    }
}
