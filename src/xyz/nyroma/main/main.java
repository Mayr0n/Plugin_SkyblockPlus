package xyz.nyroma.main;

import com.google.common.collect.HashMultimap;
import org.bukkit.ChatColor;
import xyz.nyroma.towny.*;
import xyz.nyroma.commands.cmdManager;
import xyz.nyroma.craftsCenter.CraftsManager;
import xyz.nyroma.logsCenter.logsListener;
import xyz.nyroma.tpPack.tpEtCooldowns;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class main extends JavaPlugin {
    private xyz.nyroma.commands.cmdManager cmdManager = new cmdManager(this, new tpEtCooldowns(this));
    private CityManager cm = new CityManager();
    private CityCommands cCmdManager;

    public static void main(String[] args){

    }

    @Override
    public void onEnable() {
        cCmdManager = new CityCommands();
        //Bukkit.getServer().getPluginManager().registerEvents(new , this);
        for(String cmd : cmdManager.getCommands()){
            this.getCommand(cmd).setExecutor(cmdManager);
        }

        for(String cmd : new CityCommands().getCommands()){
            this.getCommand(cmd).setExecutor(cCmdManager);
        }

        new CraftsManager(this, getServer()).build();

        Bukkit.getServer().getPluginManager().registerEvents(new listeners(), this);
        try {
            Bukkit.getServer().getPluginManager().registerEvents(new logsListener(), this);
        } catch(FileNotFoundException ignored){

        }
        Bukkit.getServer().getPluginManager().registerEvents(new CityListeners(), this);

        System.out.println("Chargement des villes...");
        try {
            cm.getCity("Etat");
        } catch (NotExistException e) {
            ClaimsManager.citiesClaims.put("l'Etat", HashMultimap.create());
        }
        cm.deserializeCities();
        System.out.println("Villes chargées !");
        System.out.println("Plugin survie activé !");
    }

    @Override
    public void onDisable() {
        System.out.println("Enregistrement des villes...");
        cm.serializeCities();
        System.out.println("Villes enregistrées !");
        System.out.println("Plugin survie désactivé !");
    }
}
