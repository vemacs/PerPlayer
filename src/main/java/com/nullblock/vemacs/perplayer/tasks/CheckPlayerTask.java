package com.nullblock.vemacs.perplayer.tasks;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.nullblock.vemacs.perplayer.PerPlayer;

public class CheckPlayerTask extends BukkitRunnable {

	private Player player;
	private int radius;
	private int limit;
	private int safe;
	private int pass = 10;
	private int delaytick = 20;

	public CheckPlayerTask(Player player, int radius,
			int limit, int safe) {
		this.player = player;
		this.radius = radius;
		this.limit = limit;
		this.safe = safe;
	}

	public void run() {
		List<Entity> entities = player
				.getNearbyEntities(radius, radius, radius);
		Iterator cleanup = entities.iterator();
		while (cleanup.hasNext()) {
			Entity checked = (Entity) cleanup.next();
			if (!(checked instanceof Monster)) {
				cleanup.remove();
			}
		}
		if (entities.size() > limit) {
			PerPlayer.PerPlayer.getLogger().info(player.getName() + " hit the limit of " + limit
							+ " monsters within a radius of " + radius + " blocks!");
			for(int i = 0; i < Math.ceil((entities.size() - safe) / 10); i++) {
				Bukkit.getServer().getScheduler().runTaskLater(PerPlayer.PerPlayer, new DepopTask(entities, pass), delaytick * i);
			}
		}
	}
}