package com.nullblock.vemacs.perplayer.threads;

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

public class MonitorThread extends BukkitRunnable {

	private int limit;
	private int safe;
	private int radius;
	private double delay = 0.2;
	private int threadlimit = 3;
	public static Logger LOGGER = Logger.getLogger(PerPlayer.class.getName());
	public static HashMap threadcounter = new HashMap();
	
	public MonitorThread(int limit, int safe, int radius) {
		this.limit = limit;
		this.safe = safe;
		this.radius = radius;
	}

	public void run() {
		for (;;) {
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				try {
					Thread.sleep((long) (delay * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//LOGGER.info("Checking " + player.getName());
				List<Entity> entities = player.getNearbyEntities(radius,
						radius, radius);
				//LOGGER.info(player.getName() + " has " + entities.size() + " entities");
				Iterator cleanup = entities.iterator();
				while (cleanup.hasNext()) {
					Entity checked = (Entity) cleanup.next();
					if (!(checked instanceof Monster)) {
						cleanup.remove();
					}
				}
				//LOGGER.info(player.getName() + " has " + entities.size() + " monsters");
				//LOGGER.info("Limit is " + limit + " monsters");
				if (entities.size() > limit) {
					if (!threadcounter.containsKey(player)) {
						threadcounter.put(player, 0);
					}
					LOGGER.info(player.getName() + " hit the limit of " + limit
							+ " monsters within a radius of " + radius + " blocks!");
					if ((int) threadcounter.get(player) < threadlimit) {
					new Thread(
							new DepopThread(safe, entities, player))
							.start();
					threadcounter.put(player, (int) threadcounter.get(player) + 1);
					}
				}
			}
		}
	}
}
