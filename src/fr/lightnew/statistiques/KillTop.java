package fr.lightnew.statistiques;

import org.bukkit.entity.Player;

import java.util.*;

public class KillTop {

    public static final Map<Player, Integer> KILL_TOP = new WeakHashMap<>();

    public static Player[] getTop(int limit) {
        final List<Player> killTopPlayerList;
        final Player[] killTopNames;
        final Player[] result;

        if (limit < 1)
            return null;
        killTopPlayerList = new ArrayList<Player>(KILL_TOP.keySet());
        Collections.reverse(killTopPlayerList);
        killTopNames = killTopPlayerList.toArray(new Player[0]);
        result = new Player[Math.min(limit, killTopNames.length)];
        System.arraycopy(killTopNames, 0, result, 0, result.length);
        return result;
    }
}
