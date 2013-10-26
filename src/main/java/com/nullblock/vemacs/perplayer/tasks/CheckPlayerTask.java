package com.nullblock.vemacs.perplayer.tasks;

import com.nullblock.vemacs.perplayer.threads.MonitorThread;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.List;

public class CheckPlayerTask extends BukkitRunnable {

    private Player player;
    private int radius;
    private int limit;
    private int safe;
    private int pass;
    private int delayTick = 20;
    public static int maxpass = Bukkit.getPluginManager().getPlugin("PerPlayer")
            .getConfig().getInt("maxpass");

    public CheckPlayerTask(Player player, int radius, int limit, int safe) {
        this.player = player;
        this.radius = radius;
        this.limit = limit;
        this.safe = safe;
    }

    public void run() {
        if (!MonitorThread.threadCounter.containsKey(player.getName())) {
            MonitorThread.threadCounter.put(player.getName(), 0);
        }

        if ((!(player == null)) && (MonitorThread.threadCounter.get(player.getName()) < maxpass)) {
            List<Entity> entities = player.getNearbyEntities(radius, radius,
                    radius);
            Iterator<Entity> cleanup = entities.iterator();
            while (cleanup.hasNext()) {
                Entity checked = cleanup.next();
                if (!(checked instanceof Monster)) {
                    cleanup.remove();
                }
            }
            if (entities.size() > limit) {
                Bukkit.getPluginManager()
                        .getPlugin("PerPlayer")
                        .getLogger()
                        .info(player.getName() + " hit the limit of " + limit
                                + " monsters within a radius of " + radius
                                + " blocks!");
                for (int i = 0; i < Math.ceil((entities.size() - safe)
                        / (Bukkit.getPluginManager().getPlugin("PerPlayer")
                        .getConfig().getInt("pass"))); i++) {
                    Bukkit.getServer()
                            .getScheduler()
                            .runTaskLater(
                                    Bukkit.getPluginManager().getPlugin(
                                            "PerPlayer"),
                                    new DepopTask(entities, Bukkit
                                            .getPluginManager()
                                            .getPlugin("PerPlayer").getConfig()
                                            .getInt("pass")), delayTick * i);
                }
                MonitorThread.threadCounter.put(player.getName(), MonitorThread.threadCounter.get(player.getName()) + 1);
                Bukkit.getServer()
                        .getScheduler()
                        .runTaskLater(
                                Bukkit.getPluginManager()
                                        .getPlugin("PerPlayer"),
                                new RemoveList(player),
                                (long) (delayTick * Math.ceil(((entities.size() - safe))
                                        / (Bukkit.getPluginManager()
                                        .getPlugin("PerPlayer")
                                        .getConfig().getInt("pass")))));
            }
        }
    }
}
