package com.nullblock.vemacs.perplayer.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.nullblock.vemacs.perplayer.PerPlayer;
import com.nullblock.vemacs.perplayer.tasks.CheckPlayerTask;

public class MonitorThread extends BukkitRunnable {

	private int limit;
	private int safe;
	private int radius;
	private double delay = 0.2;
	private int threadlimit = 2;
	public static Logger LOGGER = Logger.getLogger(PerPlayer.class.getName());
	public static List<String> threadcounter = new ArrayList();

	public MonitorThread(int limit, int safe, int radius) {
		this.limit = limit;
		this.safe = safe;
		this.radius = radius;
	}

	public void run() {
		for (;;) {
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
