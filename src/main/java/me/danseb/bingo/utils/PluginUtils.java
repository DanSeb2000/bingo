package me.danseb.bingo.utils;

import me.danseb.bingo.Core;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Plugin Utils
 *
 * For now to set a custom log, get
 * the time of the game and the future
 * scoreboard that I'll implement.
 */
public class PluginUtils {

    /**
     * Custom log in the name of the plugin
     * @param prefix Prefix of the log, can be anything you want.
     * @param message Message of the log.
     */
    public static void sendLog(String prefix, String message) {
        Core.getInstance().getServer().getConsoleSender()
                .sendMessage(
                        ChatColor.AQUA
                        + "[Bingo!] - "
                        + ChatColor.GOLD
                        + "[" + prefix + "] "
                        + ChatColor.RESET
                        + message);
    }

    /**
     * Current time of the game, will
     * @return time as string in format mm:ss (eg. 04:56).
     */
    public String getCurrentTime(){
        long currentTime = System.currentTimeMillis() - Core.getInstance().getGameManager().getStartTime();
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Date resultdate = new Date(currentTime);
        return sdf.format(resultdate);
    }

    /**
     * WIP
     * @return scoreboard.
     */
    public Scoreboard setScoreboard(){
        ScoreboardManager sbm = Bukkit.getScoreboardManager();
        Scoreboard sb = sbm.getNewScoreboard();
        Objective o = sb.registerNewObjective("White", "");

        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.GREEN+"Bingo!");

        Score score1 = o.getScore(ChatColor.GRAY + "Time:");
        Score score2 = o.getScore(ChatColor.DARK_AQUA + getCurrentTime());
        Score score3 = o.getScore(ChatColor.GRAY + "Teams:");
        Score score4 = o.getScore(ChatColor.DARK_AQUA + "4");

        score1.setScore(1);
        score2.setScore(2);
        score3.setScore(3);
        score4.setScore(4);

        return sb;
    }

    /**
     * Sends an actionbar to the player
     * @param player Player
     * @param message Message to the actionbar
     */
    public static void sendActionBar(Player player, String message){
        if (Core.getInstance().getServer().getVersion().contains("1.8")){
            PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message), (byte)2);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }
    }
}
