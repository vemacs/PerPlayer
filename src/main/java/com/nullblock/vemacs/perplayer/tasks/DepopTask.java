package com.nullblock.vemacs.perplayer.tasks;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DepopTask extends BukkitRunnable {

    private List<Entity> entities;
    private int pass;

    public DepopTask(List<Entity> entities, int pass) {
        this.entities = entities;
        this.pass = pass;
    }

    public void run() {
        List<Entity> toremove = pickRandom(entities, pass);
        depop(toremove);
    }

    public void depop(List<Entity> toremove) {
        for (Entity nextentity : toremove) {
            if (nextentity != null && nextentity instanceof Monster) {
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
