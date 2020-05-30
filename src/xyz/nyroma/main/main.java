package xyz.nyroma.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nyroma.Capitalism.bank.BankCache;
import xyz.nyroma.Capitalism.bank.BankCommands;
import xyz.nyroma.Capitalism.bank.BankListeners;
import xyz.nyroma.Capitalism.bourse.ShopCommands;
import xyz.nyroma.Capitalism.bourse.ShopListeners;
import xyz.nyroma.Capitalism.jobs.JobCommands;
import xyz.nyroma.Capitalism.jobs.JobListeners;
import xyz.nyroma.Capitalism.jobs.JobManager;
import xyz.nyroma.betterItems.BetterListeners;
import xyz.nyroma.commands.CityCommands;
import xyz.nyroma.commands.CommandManager;
import xyz.nyroma.craftsCenter.CraftsManager;
import xyz.nyroma.homes.*;
import xyz.nyroma.listeners.CityListeners;
import xyz.nyroma.logsCenter.logsListener;
import xyz.nyroma.towny.citymanagement.CitiesCache;

import java.util.ArrayList;
import java.util.Hashtable;

public class main extends JavaPlugin {

    public static void main(String[] args) {

    }

    @Override
    public void onEnable() {

        for (String cmd : new CommandManager().getCommands()) {
            this.getCommand(cmd).setExecutor(new CommandManager());
        }

        for (String cmd : new CityCommands().getCommands()) {
            this.getCommand(cmd).setExecutor(new CityCommands());
        }

        for(String cmd : new HomeCommands().getCommands()){
            this.getCommand(cmd).setExecutor(new HomeCommands());
        }

        for(String cmd : new BankCommands().getCommands()){
            this.getCommand(cmd).setExecutor(new BankCommands());
        }

        for(String cmd : JobCommands.getCommands()){
            this.getCommand(cmd).setExecutor(new JobCommands());
        }

        for(String cmd : new ShopCommands().getCommands()){
            this.getCommand(cmd).setExecutor(new ShopCommands());
        }

        new CraftsManager(this, getServer()).build();

        Bukkit.getServer().getPluginManager().registerEvents(new listeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BetterListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new logsListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CityListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BankListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JobListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ShopListeners(), this);

        Hashtable<String, Hashtable<Integer, ArrayList<Integer>>> claims = new Hashtable<>();
        Hashtable<Integer, ArrayList<Integer>> coos = new Hashtable<>();
        ArrayList<Integer> z = new ArrayList<>();
        z.add(1);
        z.add(-1);

        coos.put(1, z);
        coos.put(-1, z);

        CitiesCache.setup(claims);
        HomesCache.setup(this);
        logsListener.setup();
        BankCache.setup(this);
        JobManager.setup(this);
        //ShopstandCache.setup(this);

        new TaskManager(this, Bukkit.getServer()).build();

        System.out.println("Plugin survie activé !");

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(ChatColor.DARK_GREEN + "Le serveur a été reload.");
            for(NamespacedKey nk : CraftsManager.namespacedKeys){
                p.discoverRecipe(nk);
            }
        }
    }

    @Override
    public void onDisable() {
        CitiesCache.shutdown();
        HomesCache.serializeAll();
        logsListener.serializeAll();
        BankCache.serializeAll();
        JobManager.serializeAll();
        //ShopstandCache.serializeAll();
        System.out.println("Plugin survie désactivé !");
    }
}
