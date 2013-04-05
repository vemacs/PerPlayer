package com.nullblock.vemacs.perplayer.threads;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import com.nullblock.vemacs.perplayer.PerPlayer;

public class MonitorThread implements Runnable {

	private int limit;
	private int safe;
	private int radius;
	private int delay = 5;
	public static Logger LOGGER = Logger.getLogger(PerPlayer.class.getName());

	public MonitorThread(int limit, int safe, int radius) {
		this.limit = limit;
		this.safe = safe;
		this.radius = radius;
	}

	public void run() {
		for (;;) {
			try {
				Thread.sleep(delay * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				LOGGER.info("Checking " + player.getName());
				List<Entity> entities = player.getNearbyEntities(radius,
						radius, radius);
				LOGGER.info(player.getName() + " has " + entities.size() + " entities");
				Iterator cleanup = entities.iterator();
				while (cleanup.hasNext()) {
					Entity checked = (Entity) cleanup.next();
					if (!(checked instanceof Monster)) {
						cleanup.remove();
					}
				}
				LOGGER.info(player.getName() + " has " + entities.size() + " monsters");
				LOGGER.info("Limit is " + limit + " monsters");
				if (entities.size() > limit) {
					LOGGER.info(player.getName() + " hit the limit of " + limit
							+ " monsters!");
					new Thread(
							new DepopThread(safe, entities, player.getName()))
							.start();
				}
			}
		}
	}
}
