package fr.sky.main;

import fr.sky.claims.ClaimsListeners;
import fr.sky.commands.cmdManager;
import fr.sky.craftsCenter.CraftsManager;
import fr.sky.logsCenter.logsListener;
import fr.sky.tpPack.tpEtCooldowns;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    private cmdManager cmdManager = new cmdManager(this, new tpEtCooldowns(this));

    public static void main(String[] args){

    }

    @Override
    public void onEnable() {
        //Bukkit.getServer().getPluginManager().registerEvents(new , this);
        for(String cmd : cmdManager.getCommands()){
            this.getCommand(cmd).setExecutor(cmdManager);
        }
        new CraftsManager(this, getServer()).build();

        Bukkit.getServer().getPluginManager().registerEvents(new listeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new logsListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ClaimsListeners(), this);
        System.out.println("Plugin survie lancé !");
    }

    @Override
    public void onDisable() {
        System.out.println("Plugin survie désactivé !");
    }
}
