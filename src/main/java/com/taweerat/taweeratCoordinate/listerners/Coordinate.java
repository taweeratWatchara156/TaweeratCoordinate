package com.taweerat.taweeratCoordinate.listerners;

import com.taweerat.taweeratCoordinate.TaweeratCoordinate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Coordinate implements Listener {
    public TaweeratCoordinate instance;
    public Map<String, BukkitRunnable> run = new HashMap<>();

    public Coordinate(TaweeratCoordinate instance) {
        this.instance = instance;
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();

        String uuid = player.getUniqueId().toString();
        if(instance.getPlayerList().contains(uuid)){
            run.put(uuid, instance.getPlayerRunnable(player));
            instance.setRun(run);

            instance.getRun().get(uuid).runTaskTimer(instance, 0, 0);
        }
    }

    @EventHandler
    public void PlayerReloadServer(PlayerCommandPreprocessEvent event){
        if(event.getMessage().equalsIgnoreCase("/reload confirm")){
            run = new HashMap<>();
            instance.saveData(new ArrayList<>());
        }
    }
}
