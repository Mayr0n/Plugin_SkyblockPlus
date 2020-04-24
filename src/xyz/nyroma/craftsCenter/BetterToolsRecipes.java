package xyz.nyroma.craftsCenter;

import xyz.nyroma.main.customItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class BetterToolsRecipes {
    private JavaPlugin plugin;

    public BetterToolsRecipes(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public List<ShapedRecipe> build() {
        ShapedRecipe headcut = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "headcut"), new customItems().getHeadcutter());

        headcut.shape("aaz", "abz", "zcz");
        headcut.setIngredient('a', Material.REDSTONE_BLOCK);
        headcut.setIngredient('b', Material.GOLDEN_AXE);
        headcut.setIngredient('c', Material.STICK);

        return Arrays.asList(
                headcut,
                golderRecipe(Material.IRON_INGOT, new customItems().getGolder1(), "g1"),
                golderRecipe(Material.GOLD_INGOT, new customItems().getGolder2(), "g2"),
                stringerRecipe(Material.IRON_INGOT, new customItems().getStringer1(), "s1"),
                stringerRecipe(Material.GOLD_INGOT, new customItems().getStringer2(), "s2"),
                ironerRecipe(Material.IRON_INGOT, new customItems().getIroner1(), "i1"),
                ironerRecipe(Material.GOLD_INGOT, new customItems().getIroner2(), "i2")
        );
    }

    private ShapedRecipe ironerRecipe(Material material, ItemStack item, String name) {
        ShapedRecipe ironer = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, name), item);

        ironer.shape("aaa", "aaa", "zbz");
        ironer.setIngredient('a', material);
        ironer.setIngredient('b', Material.STICK);

        return ironer;
    }
    private ShapedRecipe stringerRecipe(Material material, ItemStack item, String name) {
        ShapedRecipe stringer = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, name), item);

        stringer.shape("cab", "aab", "zzb");
        stringer.setIngredient('a', material);
        stringer.setIngredient('b', Material.STICK);
        stringer.setIngredient('c', Material.SPIDER_EYE);
        return stringer;
    }
    private ShapedRecipe golderRecipe(Material material, ItemStack item, String name) {
        ShapedRecipe golder = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, name), item);
        golder.shape("aab", "aab", "zzb");
        golder.setIngredient('a', material);
        golder.setIngredient('b', Material.STICK);
        return golder;
    }

}
