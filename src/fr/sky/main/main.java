package fr.sky.main;

import fr.sky.claims.ClaimsListeners;
import fr.sky.commands.cmdManager;
import fr.sky.logsCenter.logsListener;
import fr.sky.tpPack.tpEtCooldowns;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;
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
        for(ShapedRecipe r : new customCrafts(this).getCrafts()){
            getServer().addRecipe(r);
        }
        Bukkit.getServer().getPluginManager().registerEvents(new listeners(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new logsListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ClaimsListeners(), this);
        /*try {
            Bukkit.getServer().loadServerIcon(new File("server-icon.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        System.out.println("Plugin survie lancé !");
    }

    @Override
    public void onDisable() {
        System.out.println("Plugin survie désactivé !");
    }
}
