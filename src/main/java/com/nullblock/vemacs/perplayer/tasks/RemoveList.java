package com.nullblock.vemacs.perplayer.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.nullblock.vemacs.perplayer.threads.MonitorThread;

public class RemoveList extends BukkitRunnable {
	
	private Player player;
	
	public RemoveList(Player player) {
		this.player = player;
	}

	public void run() {
		MonitorThread.threadcounter.remove(player);
	}
}
