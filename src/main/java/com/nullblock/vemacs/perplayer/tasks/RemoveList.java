package com.nullblock.vemacs.perplayer.tasks;

import com.nullblock.vemacs.perplayer.threads.MonitorThread;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveList extends BukkitRunnable {

    private Player player;

    public RemoveList(Player player) {
        this.player = player;
    }

    public void run() {
        MonitorThread.threadCounter.put(player.getName(), MonitorThread.threadCounter.get(player.getName()) - 1);
        MonitorThread.LOGGER.info("Depopulation completed for "
                + player.getName());
    }
}
