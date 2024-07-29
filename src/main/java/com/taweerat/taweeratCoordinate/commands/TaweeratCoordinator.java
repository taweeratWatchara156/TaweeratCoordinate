package com.taweerat.taweeratCoordinate.commands;

import com.taweerat.taweeratCoordinate.TaweeratCoordinate;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaweeratCoordinator implements CommandExecutor {
    private TaweeratCoordinate instance;
    private List<String> playerList;
    BukkitRunnable runnable;
    public Map<String, BukkitRunnable> run = new HashMap<>();

    public TaweeratCoordinator(TaweeratCoordinate instance) {
        this.instance = instance;
        playerList = instance.getPlayerList();
        run = instance.getRun();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player player){
            String uuid = player.getUniqueId().toString();
            if(!playerList.contains(uuid)){
                player.spigot().sendMessage(ChatMessageType.SYSTEM, TextComponent.fromLegacy("Taweerat Coordinator : " + ChatColor.GREEN + "" + org.bukkit.ChatColor.BOLD + "ON"));
                playerList.add(uuid);
                instance.saveData(playerList);

                run.put(uuid, instance.getPlayerRunnable(player));
                instance.setRun(run);
                instance.getRun().get(uuid).runTaskTimer(instance, 0, 0);
            }else{
                player.spigot().sendMessage(ChatMessageType.SYSTEM, TextComponent.fromLegacy("Taweerat Coordinator : " + ChatColor.RED + "" + org.bukkit.ChatColor.BOLD + "OFF"));
                instance.getRun().get(uuid).cancel();
                run.remove(uuid);
                instance.setRun(run);
                playerList.remove(uuid);
                instance.saveData(playerList);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(" "));
            }
        }

        return true;
    }
}
