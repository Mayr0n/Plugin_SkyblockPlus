package fr.sky.craftsCenter;

import fr.sky.main.customItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

import static fr.sky.craftsCenter.CraftsManager.getNamespacedkey;

public class ChangedRecipes {
    private JavaPlugin plugin;

    public ChangedRecipes(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public List<ShapedRecipe> buildShapes() {
        ShapedRecipe coms = new ShapedRecipe(getNamespacedkey(plugin, "comp"), new ItemStack(Material.COMPARATOR));
        coms.shape("zez", "ebe", "ccc");
        coms.setIngredient('b', Material.QUARTZ);
        coms.setIngredient('e', Material.REDSTONE_TORCH);
        coms.setIngredient('c', Material.COBBLESTONE);

        ShapedRecipe res = new ShapedRecipe(getNamespacedkey(plugin, "repeat"), new ItemStack(Material.REPEATER));
        res.shape("zzz", "ebe", "ccc");
        res.setIngredient('b', Material.REDSTONE);
        res.setIngredient('e', Material.REDSTONE_TORCH);
        res.setIngredient('c', Material.COBBLESTONE);

        ShapedRecipe saver = new ShapedRecipe(getNamespacedkey(plugin, "saver"), new customItems().getSaver());

        saver.shape(".a.", "cbc", ".c.");
        saver.setIngredient('a', Material.GOLDEN_APPLE);
        saver.setIngredient('b', Material.ENDER_PEARL);
        saver.setIngredient('c', Material.GOLD_INGOT);

        ShapedRecipe nametag = new ShapedRecipe(getNamespacedkey(plugin, "nametag"), new ItemStack(Material.NAME_TAG));

        nametag.shape("zaa", "bca", "dbz");
        nametag.setIngredient('a', Material.STRING);
        nametag.setIngredient('b', Material.PAPER);
        nametag.setIngredient('c', Material.SLIME_BALL);
        nametag.setIngredient('d', Material.BLACK_DYE);

        ShapedRecipe beehive = new ShapedRecipe(getNamespacedkey(plugin, "beehive"), new ItemStack(Material.BEEHIVE));

        beehive.shape("aba", "def", "aca");
        beehive.setIngredient('a', Material.SUGAR);
        beehive.setIngredient('b', Material.GOLD_INGOT);
        beehive.setIngredient('c', Material.OAK_PLANKS);
        beehive.setIngredient('d', Material.POPPY);
        beehive.setIngredient('e', Material.BEE_SPAWN_EGG);
        beehive.setIngredient('f', Material.DANDELION);

        ShapedRecipe beehive2 = new ShapedRecipe(getNamespacedkey(plugin, "beehive2"), new ItemStack(Material.BEE_NEST));

        beehive2.shape("aba", "def", "aca");
        beehive2.setIngredient('a', Material.SUGAR);
        beehive2.setIngredient('b', Material.GOLD_INGOT);
        beehive2.setIngredient('c', Material.YELLOW_WOOL);
        beehive2.setIngredient('d', Material.POPPY);
        beehive2.setIngredient('e', Material.BEE_SPAWN_EGG);
        beehive2.setIngredient('f', Material.DANDELION);

        ShapedRecipe myce = new ShapedRecipe(getNamespacedkey(plugin, "myce"), new ItemStack(Material.MYCELIUM,6));

        myce.shape("aaa", "bbb", "ccc");
        myce.setIngredient('a', Material.RED_MUSHROOM);
        myce.setIngredient('b', Material.BROWN_MUSHROOM);
        myce.setIngredient('c', Material.GRASS_BLOCK);

        return Arrays.asList(
                coms, res, saver, nametag, beehive, beehive2, myce,
                simpleRecipe(Material.CHARCOAL, new ItemStack(Material.COAL_BLOCK), "coalb"),
                simpleRecipe(Material.COAL_BLOCK, new ItemStack(Material.DIAMOND), "diamond"),
                simpleRecipe(Material.FLINT, new ItemStack(Material.COBBLESTONE), "cobble"),
                circleRecipe(Material.COBBLESTONE, Material.LAVA_BUCKET, new ItemStack(Material.NETHERRACK, 8), "nack"),
                circleRecipe(Material.SHEARS, Material.YELLOW_WOOL, new ItemStack(Material.SPONGE), "sponge"),
                circleRecipe(Material.RED_MUSHROOM, Material.WHEAT_SEEDS, new ItemStack(Material.NETHER_WART), "wart"),
                circleRecipe(Material.ROTTEN_FLESH, Material.WHEAT_SEEDS, new ItemStack(Material.BROWN_MUSHROOM), "gmush"),
                circleRecipe(Material.STONE, Material.RED_DYE, new ItemStack(Material.REDSTONE, 8), "redstone"),
                crossCompleteRecipe(Material.ROTTEN_FLESH, Material.RED_DYE, Material.WHEAT_SEEDS, new ItemStack(Material.RED_MUSHROOM), "rmush"),
                crossCompleteRecipe(Material.SUGAR, Material.JUNGLE_LOG, Material.VINE, new ItemStack(Material.COCOA_BEANS), "cacao"),
                crossCompleteRecipe(Material.GREEN_DYE, Material.STRING, Material.ENDER_PEARL, new ItemStack(Material.SLIME_BALL), "slball"),
                circleRecipe(Material.GREEN_DYE, Material.DIAMOND, new ItemStack(Material.EMERALD), "emer")
        );
    }
    public List<ShapelessRecipe> buildShapeless(){
        ShapelessRecipe c1 = new ShapelessRecipe(getNamespacedkey(plugin, "c1"), new ItemStack(Material.BLUE_CONCRETE));
        c1.addIngredient(Material.BLUE_CONCRETE_POWDER);
        ShapelessRecipe c2 = new ShapelessRecipe(getNamespacedkey(plugin, "c2"), new ItemStack(Material.RED_CONCRETE));
        c2.addIngredient(Material.RED_CONCRETE_POWDER);
        ShapelessRecipe c3 = new ShapelessRecipe(getNamespacedkey(plugin, "c3"), new ItemStack(Material.GREEN_CONCRETE));
        c3.addIngredient(Material.GREEN_CONCRETE_POWDER);
        ShapelessRecipe c4 = new ShapelessRecipe(getNamespacedkey(plugin, "c4"), new ItemStack(Material.YELLOW_CONCRETE));
        c4.addIngredient(Material.YELLOW_CONCRETE_POWDER);
        ShapelessRecipe c5 = new ShapelessRecipe(getNamespacedkey(plugin, "c5"), new ItemStack(Material.CYAN_CONCRETE));
        c5.addIngredient(Material.CYAN_CONCRETE_POWDER);
        ShapelessRecipe c6 = new ShapelessRecipe(getNamespacedkey(plugin, "c6"), new ItemStack(Material.LIME_CONCRETE));
        c6.addIngredient(Material.LIME_CONCRETE_POWDER);
        ShapelessRecipe c7 = new ShapelessRecipe(getNamespacedkey(plugin, "c7"), new ItemStack(Material.PINK_CONCRETE));
        c7.addIngredient(Material.PINK_CONCRETE_POWDER);
        ShapelessRecipe c8 = new ShapelessRecipe(getNamespacedkey(plugin, "c8"), new ItemStack(Material.PURPLE_CONCRETE));
        c8.addIngredient(Material.PURPLE_CONCRETE_POWDER);
        ShapelessRecipe c9 = new ShapelessRecipe(getNamespacedkey(plugin, "c9"), new ItemStack(Material.BLACK_CONCRETE));
        c9.addIngredient(Material.BLACK_CONCRETE_POWDER);
        ShapelessRecipe c10 = new ShapelessRecipe(getNamespacedkey(plugin, "c10"), new ItemStack(Material.GRAY_CONCRETE));
        c10.addIngredient(Material.GRAY_CONCRETE_POWDER);
        ShapelessRecipe c11 = new ShapelessRecipe(getNamespacedkey(plugin, "c11"), new ItemStack(Material.LIGHT_BLUE_CONCRETE));
        c11.addIngredient(Material.LIGHT_BLUE_CONCRETE_POWDER);
        ShapelessRecipe c12 = new ShapelessRecipe(getNamespacedkey(plugin, "c12"), new ItemStack(Material.LIGHT_GRAY_CONCRETE));
        c12.addIngredient(Material.LIGHT_GRAY_CONCRETE_POWDER);
        ShapelessRecipe c13 = new ShapelessRecipe(getNamespacedkey(plugin, "c13"), new ItemStack(Material.BROWN_CONCRETE));
        c13.addIngredient(Material.BROWN_CONCRETE_POWDER);
        ShapelessRecipe c14 = new ShapelessRecipe(getNamespacedkey(plugin, "c14"), new ItemStack(Material.WHITE_CONCRETE));
        c14.addIngredient(Material.WHITE_CONCRETE_POWDER);
        ShapelessRecipe c15 = new ShapelessRecipe(getNamespacedkey(plugin, "c15"), new ItemStack(Material.ORANGE_CONCRETE));
        c15.addIngredient(Material.ORANGE_CONCRETE_POWDER);

        ShapelessRecipe grav = new ShapelessRecipe(getNamespacedkey(plugin, "grav"), new ItemStack(Material.GRAVEL, 4));
        grav.addIngredient(2,Material.COBBLESTONE);
        grav.addIngredient(2,Material.FLINT);

        ShapelessRecipe sand = new ShapelessRecipe(getNamespacedkey(plugin, "sand"), new ItemStack(Material.SAND, 4));
        sand.addIngredient(2,Material.GRAVEL);
        sand.addIngredient(2,Material.FLINT);

        ShapelessRecipe grass = new ShapelessRecipe(getNamespacedkey(plugin, "grass"), new ItemStack(Material.GRASS_BLOCK, 4));

        grass.addIngredient(Material.WHEAT_SEEDS);
        grass.addIngredient(Material.BONE_MEAL);
        grass.addIngredient(Material.SAND);
        grass.addIngredient(Material.COBBLESTONE);

        ShapelessRecipe leather = new ShapelessRecipe(getNamespacedkey(plugin, "leath"), new ItemStack(Material.LEATHER));
        leather.addIngredient(Material.BEEF);
        leather.addIngredient(Material.SHEARS);

        ShapelessRecipe slimeball = new ShapelessRecipe(getNamespacedkey(plugin, "slimeball"), new ItemStack(Material.SLIME_BALL));
        slimeball.addIngredient(Material.WATER_BUCKET);
        slimeball.addIngredient(Material.MAGMA_CREAM);

        ShapelessRecipe l1 = new ShapelessRecipe(getNamespacedkey(plugin, "l1"), new ItemStack(Material.RABBIT_HIDE));
        l1.addIngredient(Material.LEATHER_HELMET);
        l1.addIngredient(Material.SHEARS);
        ShapelessRecipe l2 = new ShapelessRecipe(getNamespacedkey(plugin, "l2"), new ItemStack(Material.LEATHER));
        l2.addIngredient(Material.LEATHER_CHESTPLATE);
        l2.addIngredient(Material.SHEARS);
        ShapelessRecipe l3 = new ShapelessRecipe(getNamespacedkey(plugin, "l3"), new ItemStack(Material.LEATHER));
        l3.addIngredient(Material.LEATHER_LEGGINGS);
        l3.addIngredient(Material.SHEARS);
        ShapelessRecipe l4 = new ShapelessRecipe(getNamespacedkey(plugin, "l4"), new ItemStack(Material.RABBIT_HIDE));
        l4.addIngredient(Material.LEATHER_BOOTS);
        l4.addIngredient(Material.SHEARS);

        ShapelessRecipe cob = new ShapelessRecipe(getNamespacedkey(plugin, "cob"), new ItemStack(Material.COBBLESTONE));
        cob.addIngredient(Material.COBBLESTONE_SLAB);
        cob.addIngredient(Material.COBBLESTONE_SLAB);

        return Arrays.asList(
          c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,grav, sand, grass, leather, slimeball, l1, l2, l3, l4, cob
        );
    }

    private ShapedRecipe simpleRecipe(Material material, ItemStack item, String name) {
        ShapedRecipe i = new ShapedRecipe(getNamespacedkey(plugin, name), item);
        i.shape("aaa", "aaa", "aaa");
        i.setIngredient('a', material);
        return i;
    }
    private ShapedRecipe circleRecipe(Material m1, Material m2, ItemStack item, String name) {
        ShapedRecipe i = new ShapedRecipe(getNamespacedkey(plugin, name), item);
        i.shape("aaa", "aba", "aaa");
        i.setIngredient('a', m1);
        i.setIngredient('b', m2);
        return i;
    }
    private ShapedRecipe crossCompleteRecipe(Material m1, Material m2, Material m3, ItemStack item, String name) {
        ShapedRecipe i = new ShapedRecipe(getNamespacedkey(plugin, name), item);
        i.shape("aba", "bcb", "aba");
        i.setIngredient('a', m1);
        i.setIngredient('b', m2);
        i.setIngredient('c', m3);
        return i;
    }

}
