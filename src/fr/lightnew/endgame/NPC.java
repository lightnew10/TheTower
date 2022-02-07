package fr.lightnew.endgame;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.lightnew.TheTower;
import fr.lightnew.statistiques.StatKiller;
import gnu.trove.map.TIntObjectMap;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class NPC  {
    public static File npcData;
    public static YamlConfiguration npcDataConfig;
    private static List<EntityPlayer> NPC = new ArrayList<>();

    /**
     * Create NPCs
     */
    public static void createNPC(Player player, Location location, String npcName) {
        npcData = new File("/plugins/NPCs/NPCsData", npcName + ".yml");
        if (npcData.exists() == false) {
            MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), npcName);

            EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
            Player npcPlayer = npc.getBukkitEntity().getPlayer();
            npcPlayer.setPlayerListName("");

            npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

            String[] name = setSkin(player, npcName);
            gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));

            for (Player players : Bukkit.getOnlinePlayers()) {
                PlayerConnection connection = ((CraftPlayer) players).getHandle().playerConnection;
                connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
                connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
                connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook());
                connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

            }
            try {
                npcData.createNewFile();
                npcDataConfig = YamlConfiguration.loadConfiguration(npcData);
                npcDataConfig.set("Npc.Name", npcName);
                npcDataConfig.set("Npc.uuid", npcPlayer.getUniqueId());
                npcDataConfig.set("Npc.loc.world", npcPlayer.getWorld());
                npcDataConfig.set("Npc.loc.X", npcPlayer.getLocation().getX());
                npcDataConfig.set("Npc.loc.Y", npcPlayer.getLocation().getY());
                npcDataConfig.set("Npc.loc.Z", npcPlayer.getLocation().getZ());
                npcDataConfig.set("Npc.loc.pitch", npcPlayer.getLocation().getPitch());
                npcDataConfig.set("Npc.loc.yaw", npcPlayer.getLocation().getYaw());
                npcDataConfig.load(npcData);
                npcDataConfig.save(npcData);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        } else player.sendMessage(ChatColor.WHITE + npcName + ChatColor.YELLOW + " est deja posé sur la map !" + ChatColor.GRAY + " (Pour avoir des informations dessus faite /info <npc>)");
    }

    public static void createNPCForAllPlayer(Player player, Location location, String name, Boolean tabRemove) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
        String realname = name.replace('&', '§');
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), realname);
        String[] nname = setSkin(player, realname);
        gameProfile.getProperties().put("textures", new Property("textures", nname[0], nname[1]));
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.setHealth(1F);
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        addNPCPacketForAllPlayer(npc);
        NPC.add(npc);
        Location loc = new Location(location.getWorld(), location.getX()-3, location.getY()+1, location.getZ());
        player.teleport(loc);
        if (tabRemove == true) {
            removeNPCTabList(npc);
        } else return;
    }

    public static void createNPCForPlayer(Player player, Location location, String name, Boolean tabRemove) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld(player.getWorld().getName())).getHandle();
        String realname = name.replace('&', '§');
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), realname);
        String[] nname = setSkin(player, realname);
        gameProfile.getProperties().put("textures", new Property("textures", nname[0], nname[1]));
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        addNPCPacketForPlayer(player, npc);
        NPC.add(npc);
        if (tabRemove == true) {
            removeNPCTabList(npc);
        } else return;
    }

    /**
     * Skin
     */
    private static String[] setSkin(Player player, String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[] {texture, signature};
        }catch (Exception e) {
            EntityPlayer p = ((CraftPlayer) player).getHandle();
            GameProfile profile = p.getProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.getValue();
            String signature = property.getSignature();
            return new String[] {texture, signature};
        }
    }

    private static TIntObjectMap getNPCDataValues(EntityPlayer npc) {
        Field field;

        if (npc == null)
            return null;
        try {
            field = DataWatcher.class.getDeclaredField("dataValues");
            field.setAccessible(true);
            return (TIntObjectMap) field.get(npc.getDataWatcher());
        } catch (Exception ignored) {}
        return null;
    }

    /**
     * Packets
     */
    private static void addNPCPacketForAllPlayer(EntityPlayer npc) {
        final TIntObjectMap npcDataValue = getNPCDataValues(npc);

        if (npcDataValue != null)
            npcDataValue.put(10, new DataWatcher.WatchableObject(0, 10, (byte) (0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40)));
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        }
    }
    private static void removeNPCTabList(EntityPlayer npc) {
        BukkitTask task = new BukkitRunnable() {
            int time = 0;
            @Override
            public void run() {
                time++;
                Bukkit.getConsoleSender().sendMessage(time + " time");
                if (time == 2) {
                    Bukkit.getConsoleSender().sendMessage(time + " time");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
                        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                    }
                    cancel();
                }
            }
        }.runTaskTimer(TheTower.instance, 0, 20);
    }

    private static void addNPCPacketForPlayer(Player player, EntityPlayer npc) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw *256 /360)));
    }
}
