package xyz.nyroma.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nyroma.Capitalism.bank.BankCache;
import xyz.nyroma.Capitalism.bank.BankCommands;
import xyz.nyroma.Capitalism.bank.BankListeners;
import xyz.nyroma.commands.BourseCommands;
import xyz.nyroma.listeners.BourseListener;
import xyz.nyroma.Capitalism.jobs.JobCommands;
import xyz.nyroma.Capitalism.jobs.JobListeners;
import xyz.nyroma.Capitalism.jobs.JobUtils;
import xyz.nyroma.betterItems.BetterListeners;
import xyz.nyroma.bourseAPI.BourseCache;
import xyz.nyroma.commands.CityCommands;
import xyz.nyroma.commands.CommandManager;
import xyz.nyroma.craftsCenter.CraftsManager;
import xyz.nyroma.homes.*;
import xyz.nyroma.listeners.CityListeners;
import xyz.nyroma.listeners.MainListeners;
import xyz.nyroma.logsCenter.LogsListener;
import xyz.nyroma.towny.citymanagement.CitiesCache;

import java.util.ArrayList;
import java.util.Hashtable;

public class Main extends JavaPlugin {

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

        for(String cmd : BourseCommands.getCommands()){
            this.getCommand(cmd).setExecutor(new BourseCommands());
        }

        new CraftsManager(this, getServer()).build();

        Bukkit.getServer().getPluginManager().registerEvents(new MainListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BetterListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new LogsListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CityListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BankListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JobListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BourseListener(), this);

        Hashtable<String, Hashtable<Integer, ArrayList<Integer>>> claims = new Hashtable<>();
        Hashtable<Integer, ArrayList<Integer>> coos = new Hashtable<>();
        ArrayList<Integer> z = new ArrayList<>();
        z.add(1);
        z.add(-1);

        coos.put(1, z);
        coos.put(-1, z);

        CitiesCache.setup(claims);
        HomesCache.setup(this);
        LogsListener.setup();
        BankCache.setup(this);
        JobUtils.setup(this);
        BourseCache.setup();

        new TaskManager(this, Bukkit.getServer()).build();

        System.out.println("Plugin survie activé !");

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.discoverRecipes(CraftsManager.namespacedKeys);
            p.sendMessage(ChatColor.DARK_GREEN + "Le serveur a été reload.");
        }
    }

    @Override
    public void onDisable() {
        CitiesCache.shutdown();
        HomesCache.serializeAll();
        LogsListener.serializeAll();
        BankCache.serializeAll();
        JobUtils.serializeAll();
        BourseCache.serializeAll();
        System.out.println("Plugin survie désactivé !");
        Bukkit.broadcastMessage(ChatColor.DARK_RED + "Attention reload ! Des bugs sont possibles.");
    }
}
