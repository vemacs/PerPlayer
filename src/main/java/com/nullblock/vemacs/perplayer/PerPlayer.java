package com.nullblock.vemacs.perplayer;

import com.nullblock.vemacs.perplayer.threads.MonitorThread;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class PerPlayer extends JavaPlugin implements Listener {

    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
        // config handler
        this.saveDefaultConfig();
        int limit = this.getConfig().getInt("limit");
        int safe = this.getConfig().getInt("safe");
        int radius = this.getConfig().getInt("radius");
        // start thread
        Bukkit.getServer()
                .getScheduler()
                .runTaskAsynchronously(
                        Bukkit.getPluginManager().getPlugin("PerPlayer"),
                        new MonitorThread(limit, safe, radius));
    }
}