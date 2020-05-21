package xyz.nyroma.betterItems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import xyz.nyroma.craftsCenter.BetterCrafts;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class BetterArmorRecipes {

    public List<ShapedRecipe> getRecipes(JavaPlugin plugin){
        BetterCrafts bc = new BetterCrafts(plugin);
        return Arrays.asList(
                bc.crossCompleteRecipe(Material.GOLDEN_CARROT, Material.IRON_BLOCK, Material.IRON_HELMET, new BetterArmor(BetterArmors.NIGHT_HELMET).getItemStack(3), "nh2"),
                bc.crossCompleteRecipe(Material.PHANTOM_MEMBRANE, Material.IRON_BLOCK, Material.IRON_CHESTPLATE, new BetterArmor(BetterArmors.WINGED_CHESTPLATE).getItemStack(3), "fc3"),
                bc.crossCompleteRecipe(Material.PHANTOM_MEMBRANE, Material.DIAMOND, Material.DIAMOND_CHESTPLATE, new BetterArmor(BetterArmors.WINGED_CHESTPLATE).getItemStack(4), "fc4"),
                bc.crossCompleteRecipe(Material.PHANTOM_MEMBRANE, Material.NETHER_STAR, Material.DIAMOND_CHESTPLATE, new BetterArmor(BetterArmors.WINGED_CHESTPLATE).getItemStack(5), "fc5"),
                bc.crossCompleteRecipe(Material.SUGAR, Material.IRON_INGOT, Material.LEATHER_LEGGINGS, new BetterArmor(BetterArmors.SPEEDY_LEGGINGS).getItemStack(1), "sl1"),
                bc.crossCompleteRecipe(Material.SUGAR, Material.IRON_INGOT, Material.GOLDEN_LEGGINGS, new BetterArmor(BetterArmors.SPEEDY_LEGGINGS).getItemStack(2), "sl2"),
                bc.crossCompleteRecipe(Material.SUGAR, Material.IRON_BLOCK, Material.IRON_LEGGINGS, new BetterArmor(BetterArmors.SPEEDY_LEGGINGS).getItemStack(3), "sl3"),
                bc.crossCompleteRecipe(Material.SUGAR, Material.DIAMOND, Material.DIAMOND_LEGGINGS, new BetterArmor(BetterArmors.SPEEDY_LEGGINGS).getItemStack(4), "sl4"),
                bc.crossCompleteRecipe(Material.SUGAR, Material.NETHER_STAR, Material.DIAMOND_LEGGINGS, new BetterArmor(BetterArmors.SPEEDY_LEGGINGS).getItemStack(5), "sl5"),
                bc.crossCompleteRecipe(Material.SLIME_BALL, Material.IRON_INGOT, Material.LEATHER_BOOTS, new BetterArmor(BetterArmors.JUMPER_BOOTS).getItemStack(1), "jb1"),
                bc.crossCompleteRecipe(Material.SLIME_BALL, Material.IRON_INGOT, Material.GOLDEN_BOOTS, new BetterArmor(BetterArmors.JUMPER_BOOTS).getItemStack(2), "jb3"),
                bc.crossCompleteRecipe(Material.SLIME_BALL, Material.IRON_BLOCK, Material.IRON_BOOTS, new BetterArmor(BetterArmors.JUMPER_BOOTS).getItemStack(3), "jb2"),
                bc.crossCompleteRecipe(Material.SLIME_BALL, Material.DIAMOND, Material.DIAMOND_BOOTS, new BetterArmor(BetterArmors.JUMPER_BOOTS).getItemStack(4), "jb4"),
                bc.crossCompleteRecipe(Material.SLIME_BALL, Material.NETHER_STAR, Material.DIAMOND_BOOTS, new BetterArmor(BetterArmors.JUMPER_BOOTS).getItemStack(5), "jb5")
        );
    }
}