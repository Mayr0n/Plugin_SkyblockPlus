package fr.sky.craftsCenter;

import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class CraftsManager {
    private JavaPlugin plugin;
    private Server server;

    public CraftsManager(JavaPlugin plugin, Server server){
        this.plugin = plugin;
        this.server = server;
    }

    public void build(){
        for(ShapedRecipe r : new EggRecipes(plugin).build()){
            server.addRecipe(r);
        }
        for(ShapedRecipe r : new BetterToolsRecipes(plugin).build()){
            server.addRecipe(r);
        }
        for(ShapedRecipe r : new ChangedRecipes(plugin).buildShapes()){
            server.addRecipe(r);
        }
        for(ShapelessRecipe r : new ChangedRecipes(plugin).buildShapeless()){
            server.addRecipe(r);
        }
    }


    public static NamespacedKey getNamespacedkey(JavaPlugin plugin, String name){
        return new NamespacedKey(plugin, name);
    }
}
