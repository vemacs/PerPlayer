package com.nullblock.vemacs.perplayer;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nullblock.vemacs.perplayer.threads.MonitorThread;

public class PerPlayer extends JavaPlugin implements Listener {

	public static Plugin PerPlayer = Bukkit.getPluginManager().getPlugin("perplayer");
	
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
		new Thread(new MonitorThread(limit, safe, radius)).start();
	}
}