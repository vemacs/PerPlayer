package com.nullblock.vemacs.perplayer.threads;

import com.nullblock.vemacs.perplayer.PerPlayer;
import com.nullblock.vemacs.perplayer.tasks.CheckPlayerTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class MonitorThread extends BukkitRunnable {

    private int limit;
    private int safe;
    private int radius;
    private double delay = 0.2;
    private int threadLimit = 2;
    public static Logger LOGGER = Logger.getLogger(PerPlayer.class.getName());
    public static Map<String, Integer> threadCounter = new ConcurrentHashMap<>();

    public MonitorThread(int limit, int safe, int radius) {
        this.limit = limit;
        this.safe = safe;
        this.radius = radius;
    }

    public void run() {
        for (; ; ) {
            Player[] players = Bukkit.getServer().getOnlinePlayers();
            for (Player player : players) {
                try {
                    Thread.sleep((long) (delay * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Bukkit.getServer()
                        .getScheduler()
                        .runTask(
                                Bukkit.getPluginManager()
                                        .getPlugin("PerPlayer"),
                                new CheckPlayerTask(player, radius, limit, safe));
            }
        }
    }

}
