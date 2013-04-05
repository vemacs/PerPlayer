package com.nullblock.vemacs.perplayer.threads;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

public class DepopThread implements Runnable {

	private int safe;
	private List<Entity> entities;
	private int pass = 10;
	private int delay = 1;
	
	public DepopThread(int safe, List<Entity> entities) {
		this.safe = safe;
		this.entities = entities;
	}
	
	public void run() {
		while( entities.size() > safe ) {
			List<Entity> toremove = pickRandom(entities, pass);
			depop(toremove);
			try {
				Thread.sleep(delay * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Thread.currentThread().interrupt();
	}
	
	public void depop(List<Entity> toremove) {
		Iterator remover = entities.iterator();
		while( remover.hasNext() ){
			Entity nextentity = (Entity) remover.next();
			if( nextentity != null && nextentity instanceof Monster ){
				nextentity.remove();
			} 
			entities.remove(nextentity);
		}
	}
	
	public List<Entity> pickRandom(List<Entity> entities, int number) {
	    List<Entity> copy = new LinkedList<Entity>(entities);
	    Collections.shuffle(copy);
	    return copy.subList(0, number);
	}
}
