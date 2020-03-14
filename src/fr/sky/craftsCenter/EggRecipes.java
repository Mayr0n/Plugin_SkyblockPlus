package fr.sky.craftsCenter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

import static fr.sky.craftsCenter.CraftsManager.getNamespacedkey;

public class EggRecipes {
    private JavaPlugin plugin;

    public EggRecipes(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public List<ShapedRecipe> build(){
        return Arrays.asList(
                harderSpawnRecipe(Material.DIRT, Material.LEATHER, Material.GOLD_INGOT, "cowegg", Material.COW_SPAWN_EGG),
                basicSpawnRecipe(Material.WHITE_WOOL, "sheepegg", Material.SHEEP_SPAWN_EGG),
                basicSpawnRecipe(Material.WHEAT_SEEDS, "chickegg", Material.CHICKEN_SPAWN_EGG),
                harderSpawnRecipe(Material.CARROT, Material.LEATHER, Material.IRON_INGOT, "rabbitegg", Material.RABBIT_SPAWN_EGG),
                harderSpawnRecipe(Material.BEETROOT, Material.LEATHER, Material.IRON_INGOT, "pigegg", Material.PIG_SPAWN_EGG),
                harderSpawnRecipe(Material.LEATHER, Material.HAY_BLOCK, Material.GOLD_INGOT, "horsegg", Material.HORSE_SPAWN_EGG),
                basicSpawnRecipe(Material.GOLD_NUGGET, "puffegg", Material.PUFFERFISH_SPAWN_EGG),
                harderSpawnRecipe(Material.GUNPOWDER, Material.GREEN_WOOL, Material.REDSTONE, "creepegg", Material.CREEPER_SPAWN_EGG),
                basicSpawnRecipe(Material.BONE, "skelegg", Material.SKELETON_SPAWN_EGG),
                basicSpawnRecipe(Material.ROTTEN_FLESH, "zombegg", Material.ZOMBIE_SPAWN_EGG),
                harderSpawnRecipe(Material.STRING, Material.SPIDER_EYE, Material.STRING, "spidegg", Material.SPIDER_SPAWN_EGG),
                basicSpawnRecipe(Material.INK_SAC, "squidegg", Material.SQUID_SPAWN_EGG),
                harderSpawnRecipe(Material.OBSIDIAN, Material.EMERALD, Material.REDSTONE, "enderegg", Material.ENDERMAN_SPAWN_EGG),
                harderSpawnRecipe(Material.BLAZE_POWDER, Material.GUNPOWDER, Material.REDSTONE, "blazegg", Material.BLAZE_SPAWN_EGG),
                harderSpawnRecipe(Material.FEATHER, Material.VINE, Material.FEATHER, "parrotegg", Material.PARROT_SPAWN_EGG),
                harderSpawnRecipe(Material.LEATHER, Material.BONE_MEAL, Material.GOLD_INGOT, "lamaegg", Material.LLAMA_SPAWN_EGG),
                harderSpawnRecipe(Material.MAGMA_BLOCK, Material.MAGMA_BLOCK, Material.GOLD_INGOT, "magmegg", Material.MAGMA_CUBE_SPAWN_EGG),
                harderSpawnRecipe(Material.LAPIS_BLOCK, Material.GLASS_BOTTLE, Material.REDSTONE, "witchegg", Material.WITCH_SPAWN_EGG),
                harderSpawnRecipe(Material.SNOW_BLOCK, Material.ICE, Material.COD, "polaregg", Material.POLAR_BEAR_SPAWN_EGG),
                harderSpawnRecipe(Material.BAMBOO, Material.WHITE_WOOL, Material.BLACK_WOOL, "pandegg", Material.PANDA_SPAWN_EGG),
                harderSpawnRecipe(Material.LEATHER, Material.COD, Material.VINE, "ocelotegg", Material.OCELOT_SPAWN_EGG),
                harderSpawnRecipe(Material.ROTTEN_FLESH, Material.GOLD_BLOCK, Material.GOLD_NUGGET, "pigmanegg", Material.ZOMBIE_PIGMAN_SPAWN_EGG),
                harderSpawnRecipe(Material.DANDELION, Material.ROSE_BUSH, Material.LEATHER, "beeegg", Material.BEE_SPAWN_EGG),
                harderSpawnRecipe(Material.BONE, Material.ROTTEN_FLESH, Material.GOLD_INGOT, "wolfegg", Material.WOLF_SPAWN_EGG),
                getVillagerEggRecipe()
        );
    }

    private ShapedRecipe getVillagerEggRecipe() {

        ItemStack r = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        ShapedRecipe rr = new ShapedRecipe(getNamespacedkey(plugin, "villageregg"), r);

        rr.shape("aba", "def", "ihi");
        rr.setIngredient('i', Material.DIAMOND);
        rr.setIngredient('a', Material.HAY_BLOCK);
        rr.setIngredient('d', Material.LEATHER_CHESTPLATE);
        rr.setIngredient('b', Material.LEATHER_LEGGINGS);
        rr.setIngredient('f', Material.LEATHER_BOOTS);
        rr.setIngredient('e', Material.EGG);
        rr.setIngredient('h', Material.EMERALD_BLOCK);


        return rr;
    }

    private ShapedRecipe basicSpawnRecipe(Material mat, String name, Material item){
        ItemStack i = new ItemStack(item);
        ShapedRecipe r = new ShapedRecipe(getNamespacedkey(plugin, name), i);
        r.shape("aaa","aba","aaa");

        r.setIngredient('a', mat);
        r.setIngredient('b', Material.EGG);

        return r;
    }
    private ShapedRecipe harderSpawnRecipe(Material m1, Material m2, Material m3, String name, Material item){
        ItemStack i = new ItemStack(item);
        ShapedRecipe r = new ShapedRecipe(getNamespacedkey(plugin, name), i);
        r.shape("aba","aca","ada");

        r.setIngredient('a', m1);
        r.setIngredient('b', m2);
        r.setIngredient('c', Material.EGG);
        r.setIngredient('d', m3);

        return r;
    }

}
