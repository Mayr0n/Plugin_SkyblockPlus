package xyz.nyroma.craftsCenter;

import xyz.nyroma.betterItems.BetterArmorManager;
import xyz.nyroma.main.customItems;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class ChangedRecipes {
    private JavaPlugin plugin;

    public ChangedRecipes(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public List<ShapedRecipe> buildShapes() {
        ShapedRecipe coms = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "comp"), new ItemStack(Material.COMPARATOR));
        coms.shape("zez", "ebe", "ccc");
        coms.setIngredient('b', Material.QUARTZ);
        coms.setIngredient('e', Material.REDSTONE_TORCH);
        coms.setIngredient('c', Material.COBBLESTONE);

        ShapedRecipe res = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "repeat"), new ItemStack(Material.REPEATER));
        res.shape("zzz", "ebe", "ccc");
        res.setIngredient('b', Material.REDSTONE);
        res.setIngredient('e', Material.REDSTONE_TORCH);
        res.setIngredient('c', Material.COBBLESTONE);

        ShapedRecipe saver = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "saver"), new customItems().getSaver());

        saver.shape(".a.", "cbc", ".c.");
        saver.setIngredient('a', Material.GOLDEN_APPLE);
        saver.setIngredient('b', Material.ENDER_PEARL);
        saver.setIngredient('c', Material.GOLD_INGOT);

        ShapedRecipe nametag = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "nametag"), new ItemStack(Material.NAME_TAG));

        nametag.shape("zaa", "bca", "dbz");
        nametag.setIngredient('a', Material.STRING);
        nametag.setIngredient('b', Material.PAPER);
        nametag.setIngredient('c', Material.SLIME_BALL);
        nametag.setIngredient('d', Material.BLACK_DYE);

        ShapedRecipe beehive = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "beehive"), new ItemStack(Material.BEEHIVE));

        beehive.shape("aba", "def", "aca");
        beehive.setIngredient('a', Material.SUGAR);
        beehive.setIngredient('b', Material.GOLD_INGOT);
        beehive.setIngredient('c', Material.OAK_PLANKS);
        beehive.setIngredient('d', Material.POPPY);
        beehive.setIngredient('e', Material.BEE_SPAWN_EGG);
        beehive.setIngredient('f', Material.DANDELION);

        ShapedRecipe beehive2 = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "beehive2"), new ItemStack(Material.BEE_NEST));

        beehive2.shape("aba", "def", "aca");
        beehive2.setIngredient('a', Material.SUGAR);
        beehive2.setIngredient('b', Material.GOLD_INGOT);
        beehive2.setIngredient('c', Material.YELLOW_WOOL);
        beehive2.setIngredient('d', Material.POPPY);
        beehive2.setIngredient('e', Material.BEE_SPAWN_EGG);
        beehive2.setIngredient('f', Material.DANDELION);

        ShapedRecipe myce = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "myce"), new ItemStack(Material.MYCELIUM,6));

        myce.shape("aaa", "bbb", "ccc");
        myce.setIngredient('a', Material.RED_MUSHROOM);
        myce.setIngredient('b', Material.BROWN_MUSHROOM);
        myce.setIngredient('c', Material.GRASS_BLOCK);

        ShapedRecipe sadd = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "sadd"), new ItemStack(Material.SADDLE));

        sadd.shape("aaa", "bbb", "czc");
        sadd.setIngredient('a', Material.LEATHER);
        sadd.setIngredient('b', Material.STRING);
        sadd.setIngredient('c', Material.IRON_INGOT);

        ShapedRecipe trid = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "trid"), new ItemStack(Material.TRIDENT));
        trid.shape("aaa", ".b.", ".a.");
        trid.setIngredient('a', Material.DIAMOND);
        trid.setIngredient('b', Material.STICK);

        ShapedRecipe satur = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, "satur"), getSatur());
        satur.shape("abc", "def", "ghi");
        satur.setIngredient('a', Material.COOKED_BEEF);
        satur.setIngredient('b', Material.COOKED_CHICKEN);
        satur.setIngredient('c', Material.COOKED_MUTTON);
        satur.setIngredient('d', Material.COOKED_PORKCHOP);
        satur.setIngredient('e', Material.GOLDEN_APPLE);
        satur.setIngredient('f', Material.COOKED_RABBIT);
        satur.setIngredient('g', Material.BREAD);
        satur.setIngredient('h', Material.GOLDEN_CARROT);
        satur.setIngredient('i', Material.PUMPKIN_PIE);


        Hashtable<ItemStack, PotionEffect> armor = new BetterArmorManager().build();
        List<ItemStack> keys = new ArrayList<>(armor.keySet());

        List<ShapedRecipe> recipes = Arrays.asList(
                coms, res, saver, nametag, beehive, beehive2, myce, sadd, trid, satur,
                simpleRecipe(Material.CHARCOAL, new ItemStack(Material.COAL_BLOCK), "coalb"),
                simpleRecipe(Material.COAL_BLOCK, new ItemStack(Material.DIAMOND), "diamond"),
                simpleRecipe(Material.FLINT, new ItemStack(Material.COBBLESTONE), "cobble"),
                simpleRecipe(Material.GUNPOWDER, new ItemStack(Material.COAL), "coalg"),
                circleRecipe(Material.COBBLESTONE, Material.LAVA_BUCKET, new ItemStack(Material.NETHERRACK, 8), "nack"),
                circleRecipe(Material.SHEARS, Material.YELLOW_WOOL, new ItemStack(Material.SPONGE), "sponge"),
                circleRecipe(Material.RED_MUSHROOM, Material.WHEAT_SEEDS, new ItemStack(Material.NETHER_WART), "wart"),
                circleRecipe(Material.ROTTEN_FLESH, Material.WHEAT_SEEDS, new ItemStack(Material.BROWN_MUSHROOM), "gmush"),
                circleRecipe(Material.STONE, Material.RED_DYE, new ItemStack(Material.REDSTONE, 8), "redstone"),
                circleRecipe(Material.DIAMOND, Material.DIAMOND_HELMET, getSuperAmor(Material.DIAMOND_HELMET, "H"), "shelmet"),
                circleRecipe(Material.DIAMOND, Material.DIAMOND_CHESTPLATE, getSuperAmor(Material.DIAMOND_CHESTPLATE, "C"), "schestplate"),
                circleRecipe(Material.DIAMOND, Material.DIAMOND_LEGGINGS, getSuperAmor(Material.DIAMOND_LEGGINGS, "L"), "sleggings"),
                circleRecipe(Material.DIAMOND, Material.DIAMOND_BOOTS, getSuperAmor(Material.DIAMOND_BOOTS, "B"), "sboots"),
                circleRecipe(Material.IRON_BARS, Material.NETHER_STAR, new ItemStack(Material.SPAWNER), "spawner"),
                circleRecipe(Material.ROTTEN_FLESH, Material.SHEARS, new ItemStack(Material.LEATHER), "leat"),
                crossCompleteRecipe(Material.ROTTEN_FLESH, Material.RED_DYE, Material.WHEAT_SEEDS, new ItemStack(Material.RED_MUSHROOM), "rmush"),
                crossCompleteRecipe(Material.SUGAR, Material.JUNGLE_LOG, Material.VINE, new ItemStack(Material.COCOA_BEANS, 4), "cacao"),
                crossCompleteRecipe(Material.GREEN_DYE, Material.STRING, Material.ENDER_PEARL, new ItemStack(Material.SLIME_BALL), "slball"),
                circleRecipe(Material.GREEN_DYE, Material.DIAMOND, new ItemStack(Material.EMERALD), "emer"),
                crossCompleteRecipe(Material.GOLDEN_CARROT, Material.DIAMOND, Material.DIAMOND_HELMET, keys.get(3), "nhelm"),
                crossCompleteRecipe(Material.FEATHER, Material.DIAMOND, Material.DIAMOND_CHESTPLATE, keys.get(2), "fchest"),
                crossCompleteRecipe(Material.SUGAR, Material.DIAMOND, Material.DIAMOND_LEGGINGS, keys.get(1), "slegs"),
                crossCompleteRecipe(Material.SLIME_BALL, Material.DIAMOND, Material.DIAMOND_BOOTS, keys.get(0), "jboots"),
                crossCompleteRecipe(Material.STRING, Material.GOLD_INGOT, Material.FEATHER, getSlowFeather(), "slowf"),
                demiCircleRecipe(Material.PHANTOM_MEMBRANE, Material.TNT, Material.DIAMOND, getPropulser(), "propul")
        );

        return recipes;
    }
    public List<ShapelessRecipe> buildShapeless(){
        ShapelessRecipe c1 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c1"), new ItemStack(Material.BLUE_CONCRETE));
        c1.addIngredient(Material.BLUE_CONCRETE_POWDER);
        ShapelessRecipe c2 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c2"), new ItemStack(Material.RED_CONCRETE));
        c2.addIngredient(Material.RED_CONCRETE_POWDER);
        ShapelessRecipe c3 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c3"), new ItemStack(Material.GREEN_CONCRETE));
        c3.addIngredient(Material.GREEN_CONCRETE_POWDER);
        ShapelessRecipe c4 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c4"), new ItemStack(Material.YELLOW_CONCRETE));
        c4.addIngredient(Material.YELLOW_CONCRETE_POWDER);
        ShapelessRecipe c5 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c5"), new ItemStack(Material.CYAN_CONCRETE));
        c5.addIngredient(Material.CYAN_CONCRETE_POWDER);
        ShapelessRecipe c6 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c6"), new ItemStack(Material.LIME_CONCRETE));
        c6.addIngredient(Material.LIME_CONCRETE_POWDER);
        ShapelessRecipe c7 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c7"), new ItemStack(Material.PINK_CONCRETE));
        c7.addIngredient(Material.PINK_CONCRETE_POWDER);
        ShapelessRecipe c8 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c8"), new ItemStack(Material.PURPLE_CONCRETE));
        c8.addIngredient(Material.PURPLE_CONCRETE_POWDER);
        ShapelessRecipe c9 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c9"), new ItemStack(Material.BLACK_CONCRETE));
        c9.addIngredient(Material.BLACK_CONCRETE_POWDER);
        ShapelessRecipe c10 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c10"), new ItemStack(Material.GRAY_CONCRETE));
        c10.addIngredient(Material.GRAY_CONCRETE_POWDER);
        ShapelessRecipe c11 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c11"), new ItemStack(Material.LIGHT_BLUE_CONCRETE));
        c11.addIngredient(Material.LIGHT_BLUE_CONCRETE_POWDER);
        ShapelessRecipe c12 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c12"), new ItemStack(Material.LIGHT_GRAY_CONCRETE));
        c12.addIngredient(Material.LIGHT_GRAY_CONCRETE_POWDER);
        ShapelessRecipe c13 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c13"), new ItemStack(Material.BROWN_CONCRETE));
        c13.addIngredient(Material.BROWN_CONCRETE_POWDER);
        ShapelessRecipe c14 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c14"), new ItemStack(Material.WHITE_CONCRETE));
        c14.addIngredient(Material.WHITE_CONCRETE_POWDER);
        ShapelessRecipe c15 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c15"), new ItemStack(Material.ORANGE_CONCRETE));
        c15.addIngredient(Material.ORANGE_CONCRETE_POWDER);
        ShapelessRecipe c16 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "c16"), new ItemStack(Material.MAGENTA_CONCRETE));
        c16.addIngredient(Material.MAGENTA_CONCRETE_POWDER);

        ShapelessRecipe w1 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w1"), new ItemStack(Material.STRING,2));
        w1.addIngredient(Material.BLUE_WOOL);
        w1.addIngredient(Material.SHEARS);
        ShapelessRecipe w2 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w2"), new ItemStack(Material.STRING,2));
        w2.addIngredient(Material.RED_WOOL);
        w2.addIngredient(Material.SHEARS);
        ShapelessRecipe w3 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w3"), new ItemStack(Material.STRING,2));
        w3.addIngredient(Material.GREEN_WOOL);
        w3.addIngredient(Material.SHEARS);
        ShapelessRecipe w4 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w4"), new ItemStack(Material.STRING,2));
        w4.addIngredient(Material.YELLOW_WOOL);
        w4.addIngredient(Material.SHEARS);
        ShapelessRecipe w5 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w5"), new ItemStack(Material.STRING,2));
        w5.addIngredient(Material.CYAN_WOOL);
        w5.addIngredient(Material.SHEARS);
        ShapelessRecipe w6 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w6"), new ItemStack(Material.STRING,2));
        w6.addIngredient(Material.LIME_WOOL);
        w6.addIngredient(Material.SHEARS);
        ShapelessRecipe w7 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w7"), new ItemStack(Material.STRING,2));
        w7.addIngredient(Material.PINK_WOOL);
        w7.addIngredient(Material.SHEARS);
        ShapelessRecipe w8 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w8"), new ItemStack(Material.STRING,2));
        w8.addIngredient(Material.PURPLE_WOOL);
        w8.addIngredient(Material.SHEARS);
        ShapelessRecipe w9 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w9"), new ItemStack(Material.STRING,2));
        w9.addIngredient(Material.BLACK_WOOL);
        w9.addIngredient(Material.SHEARS);
        ShapelessRecipe w10 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w10"), new ItemStack(Material.STRING,2));
        w10.addIngredient(Material.GRAY_WOOL);
        w10.addIngredient(Material.SHEARS);
        ShapelessRecipe w11 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w11"), new ItemStack(Material.STRING,2));
        w11.addIngredient(Material.LIGHT_BLUE_WOOL);
        w11.addIngredient(Material.SHEARS);
        ShapelessRecipe w12 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w12"), new ItemStack(Material.STRING,2));
        w12.addIngredient(Material.LIGHT_GRAY_WOOL);
        w12.addIngredient(Material.SHEARS);
        ShapelessRecipe w13 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w13"), new ItemStack(Material.STRING,2));
        w13.addIngredient(Material.BROWN_WOOL);
        w13.addIngredient(Material.SHEARS);
        ShapelessRecipe w14 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w14"), new ItemStack(Material.STRING,2));
        w14.addIngredient(Material.WHITE_WOOL);
        w14.addIngredient(Material.SHEARS);
        ShapelessRecipe w15 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "w15"), new ItemStack(Material.STRING, 2));
        w15.addIngredient(Material.ORANGE_WOOL);
        w15.addIngredient(Material.SHEARS);

        ShapelessRecipe grav = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "grav"), new ItemStack(Material.GRAVEL, 4));
        grav.addIngredient(2,Material.COBBLESTONE);
        grav.addIngredient(2,Material.FLINT);

        ShapelessRecipe sand = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "sand"), new ItemStack(Material.SAND, 4));
        sand.addIngredient(2,Material.GRAVEL);
        sand.addIngredient(2,Material.FLINT);

        ShapelessRecipe grass = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "grass"), new ItemStack(Material.GRASS_BLOCK, 4));

        grass.addIngredient(Material.WHEAT_SEEDS);
        grass.addIngredient(Material.BONE_MEAL);
        grass.addIngredient(Material.SAND);
        grass.addIngredient(Material.COBBLESTONE);

        ShapelessRecipe leather = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "leath"), new ItemStack(Material.LEATHER));
        leather.addIngredient(Material.BEEF);
        leather.addIngredient(Material.SHEARS);

        ShapelessRecipe slimeball = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "slimeball"), new ItemStack(Material.SLIME_BALL));
        slimeball.addIngredient(Material.WATER_BUCKET);
        slimeball.addIngredient(Material.MAGMA_CREAM);

        ShapelessRecipe l1 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "l1"), new ItemStack(Material.RABBIT_HIDE));
        l1.addIngredient(Material.LEATHER_HELMET);
        l1.addIngredient(Material.SHEARS);
        ShapelessRecipe l2 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "l2"), new ItemStack(Material.LEATHER));
        l2.addIngredient(Material.LEATHER_CHESTPLATE);
        l2.addIngredient(Material.SHEARS);
        ShapelessRecipe l3 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "l3"), new ItemStack(Material.LEATHER));
        l3.addIngredient(Material.LEATHER_LEGGINGS);
        l3.addIngredient(Material.SHEARS);
        ShapelessRecipe l4 = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "l4"), new ItemStack(Material.RABBIT_HIDE));
        l4.addIngredient(Material.LEATHER_BOOTS);
        l4.addIngredient(Material.SHEARS);

        ShapelessRecipe cob = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "cob"), new ItemStack(Material.COBBLESTONE));
        cob.addIngredient(Material.COBBLESTONE_SLAB);
        cob.addIngredient(Material.COBBLESTONE_SLAB);

        ShapelessRecipe sto = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "sto"), new ItemStack(Material.STONE_BRICKS));
        sto.addIngredient(Material.STONE_BRICK_SLAB);
        sto.addIngredient(Material.STONE_BRICK_SLAB);

        ShapelessRecipe fea = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "fea"), new ItemStack(Material.FEATHER));
        fea.addIngredient(Material.PHANTOM_MEMBRANE);

        ShapelessRecipe bld = new ShapelessRecipe(CraftsManager.getNamespacedkey(plugin, "bld"), new ItemStack(Material.BLACK_DYE, 3));
        bld.addIngredient(Material.CYAN_DYE);
        bld.addIngredient(Material.YELLOW_DYE);
        bld.addIngredient(Material.MAGENTA_DYE);

        return Arrays.asList(
          c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15, c16, grav, sand, grass, leather, slimeball, l1, l2, l3, l4, cob,
                w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12,w13,w14,w15,fea,sto,bld
        );
    }

    private ShapedRecipe simpleRecipe(Material material, ItemStack item, String name) {
        ShapedRecipe i = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, name), item);
        i.shape("aaa", "aaa", "aaa");
        i.setIngredient('a', material);
        return i;
    }
    private ShapedRecipe circleRecipe(Material m1, Material m2, ItemStack item, String name) {
        ShapedRecipe i = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, name), item);
        i.shape("aaa", "aba", "aaa");
        i.setIngredient('a', m1);
        i.setIngredient('b', m2);
        return i;
    }
    private ShapedRecipe demiCircleRecipe(Material m1, Material m2, Material m3, ItemStack item, String name) {
        ShapedRecipe i = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, name), item);
        i.shape("aaa", "aba", "ccc");
        i.setIngredient('a', m1);
        i.setIngredient('c', m2);
        i.setIngredient('b', m3);
        return i;
    }
    private ShapedRecipe crossCompleteRecipe(Material m1, Material m2, Material m3, ItemStack item, String name) {
        ShapedRecipe i = new ShapedRecipe(CraftsManager.getNamespacedkey(plugin, name), item);
        i.shape("aba", "bcb", "aba");
        i.setIngredient('a', m1);
        i.setIngredient('b', m2);
        i.setIngredient('c', m3);
        return i;
    }

    public static ItemStack getSatur(){
        ItemStack it = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta im = it.getItemMeta();
        im.setDisplayName("Festin");
        im.addEnchant(Enchantment.DURABILITY, 10,true);
        im.setLore(Arrays.asList("De quoi n'avoir jamais faim !"));
        it.setItemMeta(im);
        return it;
    }

    private ItemStack getPropulser(){
        ItemStack it = new ItemStack(Material.FIREWORK_STAR);
        ItemMeta im = it.getItemMeta();
        im.setDisplayName("Propulseur");
        im.addEnchant(Enchantment.KNOCKBACK, 5,true);
        im.setLore(Arrays.asList("POUF", "Clic gauche : permet un recul 5", "Clic droit : permet de s'élancer dans les airs"));
        it.setItemMeta(im);
        return it;
    }
    private ItemStack getSlowFeather(){
        ItemStack it = new ItemStack(Material.FEATHER);
        ItemMeta im = it.getItemMeta();
        im.setDisplayName("Wing");
        im.addEnchant(Enchantment.DURABILITY, 5,true);
        im.setLore(Arrays.asList("Permet un effet de slow falling à l'infini une fois dans l'inventaire !"));
        it.setItemMeta(im);
        return it;
    }
    private ItemStack getSuperAmor(Material m, String type){
        ItemStack it = new ItemStack(m);
        ItemMeta im = it.getItemMeta();
        im.setDisplayName("Super-armor " + type);
        im.addEnchant(Enchantment.DURABILITY, 10,true);
        it.setItemMeta(im);
        return it;
    }

}
