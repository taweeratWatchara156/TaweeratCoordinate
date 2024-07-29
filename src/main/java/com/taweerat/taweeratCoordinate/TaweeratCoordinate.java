package com.taweerat.taweeratCoordinate;

import com.taweerat.taweeratCoordinate.commands.TaweeratCoordinator;
import com.taweerat.taweeratCoordinate.listerners.Coordinate;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class TaweeratCoordinate extends JavaPlugin {

    private List<String> playerList = new ArrayList<>();
    private Map<String, BukkitRunnable> run = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        playerList = loadConfig();

        getServer().getPluginManager().registerEvents(new Coordinate(this), this);
        Objects.requireNonNull(getCommand("tc")).setExecutor(new TaweeratCoordinator(this));
    }

    public BukkitRunnable getPlayerRunnable(Player player){

        return new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacy((ChatColor.GOLD + "" + ChatColor.BOLD + "X" + ChatColor.RESET + " : " + (int) player.getLocation().getX() + " ") +
                                (ChatColor.GOLD + "" + ChatColor.BOLD + "Y" + ChatColor.RESET + " : " + (int) player.getLocation().getY() + " ") +
                                (ChatColor.GOLD + "" + ChatColor.BOLD + "Z" + ChatColor.RESET + " : " + (int) player.getLocation().getZ() + " ") +
                                (ChatColor.GOLD + "" + ChatColor.BOLD + "Time" + ChatColor.RESET + " : " + convertTicksToTime(player.getWorld().getTime()))));
            }
        };
    }

    private static String convertTicksToTime(long ticks) {
        // Calculate hours and minutes
        long hours = (ticks / 1000 + 6) % 24;
        long minutes = (ticks % 1000) * 60 / 1000;

        // Format hours and minutes to HH:MM format
        return String.format("%02d:%02d", hours, minutes);
    }

    private List<String> loadConfig(){
        List<String> res = new ArrayList<>();

        if(getConfig().contains("player")){
            res = getConfig().getStringList("player");
        }

        return res;
    }

    public void saveData(List<String> list){
        getConfig().set("player", list);
        saveConfig();
    }

    public List<String> getPlayerList() {
        return playerList;
    }

    public void setRun(Map<String, BukkitRunnable> run) {
        this.run = run;
    }

    public Map<String, BukkitRunnable> getRun() {
        return run;
    }
}
