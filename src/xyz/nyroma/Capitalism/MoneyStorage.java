package xyz.nyroma.Capitalism;

import org.bukkit.entity.Player;

import java.io.Serializable;

public class MoneyStorage implements Serializable {
    private Player p;
    private int amount;


    public MoneyStorage(Player p){
        this.p = p;
    }

    public void add(int amount){
        this.amount += amount;
    }

    public void remove(int amount){
        this.amount -= amount;
    }



}
