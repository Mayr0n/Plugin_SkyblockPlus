package xyz.nyroma.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.nyroma.commands.CommandManager;
import xyz.nyroma.craftsCenter.CraftsManager;
import xyz.nyroma.homes.*;
import xyz.nyroma.logsCenter.logsListener;
import xyz.nyroma.towny.*;
import xyz.nyroma.tpPack.tpEtCooldowns;

import java.io.FileNotFoundException;

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

        new CraftsManager(this, getServer()).build();

        Bukkit.getServer().getPluginManager().registerEvents(new listeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new logsListener(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CityListeners(), this);

        CitiesCache.setup(this);
        HomesCache.setup(this);
        logsListener.setup();

        new TaskManager(this, Bukkit.getServer()).build();

        System.out.println("Plugin survie activé !");

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(ChatColor.DARK_GREEN + "Le serveur a été reload.");
        }
    }

    @Override
    public void onDisable() {
        CitiesCache.serializeAll();
        HomesCache.serializeAll();
        logsListener.serializeAll();
        System.out.println("Plugin survie désactivé !");
    }
}
