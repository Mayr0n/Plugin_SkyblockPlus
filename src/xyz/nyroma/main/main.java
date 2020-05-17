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
import xyz.nyroma.Capitalism.bourse.ShopstandCache;
import xyz.nyroma.Capitalism.jobs.JobCommands;
import xyz.nyroma.Capitalism.jobs.JobListeners;
import xyz.nyroma.Capitalism.jobs.JobManager;
import xyz.nyroma.commands.CommandManager;
import xyz.nyroma.craftsCenter.CraftsManager;
import xyz.nyroma.homes.*;
import xyz.nyroma.logsCenter.logsListener;
import xyz.nyroma.towny.*;
import xyz.nyroma.tpPack.tpEtCooldowns;

public class main extends JavaPlugin {

    public static void main(String[] args) {

    }

    @Override
    public void onEnable() {

        for (String cmd : new CommandManager(new tpEtCooldowns(this)).getCommands()) {
            this.getCommand(cmd).setExecutor(new CommandManager(new tpEtCooldowns(this)));
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
        Bukkit.getServer().getPluginManager().registerEvents(new logsListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CityListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BankListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JobListeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ShopListeners(), this);

        CitiesCache.setup(this);
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
        CitiesCache.serializeAll();
        HomesCache.serializeAll();
        logsListener.serializeAll();
        BankCache.serializeAll();
        JobManager.serializeAll();
        //ShopstandCache.serializeAll();
        System.out.println("Plugin survie désactivé !");
    }
}
