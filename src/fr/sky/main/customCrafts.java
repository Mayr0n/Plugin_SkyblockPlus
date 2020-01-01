package fr.sky.main;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class customCrafts {
    private static List<ShapedRecipe> crafts;
    private static JavaPlugin plugin;


    public List<ShapedRecipe> getCrafts(){
        return crafts;
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

    /*private ItemStack getCompressedItem(Material material){
        ItemStack c = new ItemStack(material);
        ItemMeta cm = c.getItemMeta();
        cm.addEnchant(Enchantment.DURABILITY, 10,true);
        c.setItemMeta(cm);
        return c;
    }

    private ShapedRecipe getCompressedRecipe(Material material){
        ShapedRecipe cg = new ShapedRecipe(getNamespacedkey(plugin, "cg"),getCompressedItem(material));
        cg.shape("aaa", "aaa", "aaa");
        cg.setIngredient('a', material);
        return cg;
    }*/

    public customCrafts(JavaPlugin plugin){
        customCrafts.plugin = plugin;
        ItemStack com = new ItemStack(Material.COMPARATOR);
        ShapedRecipe coms = new ShapedRecipe(getNamespacedkey(plugin, "comp"), com);

        coms.shape("zez", "ebe", "ccc");
        coms.setIngredient('b', Material.QUARTZ);
        coms.setIngredient('e', Material.REDSTONE_TORCH);
        coms.setIngredient('c', Material.COBBLESTONE);

        ItemStack re = new ItemStack(Material.REPEATER);
        ShapedRecipe res = new ShapedRecipe(getNamespacedkey(plugin, "repeat"), re);

        res.shape("zzz", "ebe", "ccc");
        res.setIngredient('b', Material.REDSTONE);
        res.setIngredient('e', Material.REDSTONE_TORCH);
        res.setIngredient('c', Material.COBBLESTONE);

        ShapedRecipe grav = new ShapedRecipe(getNamespacedkey(plugin, "grav"), new ItemStack(Material.GRAVEL, 4));

        grav.shape("abc", "bac", "ccc");
        grav.setIngredient('a', Material.COBBLESTONE);
        grav.setIngredient('b', Material.FLINT);

        ShapedRecipe sand = new ShapedRecipe(getNamespacedkey(plugin, "sand"), new ItemStack(Material.SAND, 4));

        sand.shape("abc", "bac", "ccc");
        sand.setIngredient('a', Material.GRAVEL);
        sand.setIngredient('b', Material.FLINT);

        ShapedRecipe red = new ShapedRecipe(getNamespacedkey(plugin, "red"), new ItemStack(Material.REDSTONE, 8));

        red.shape("aaa", "aba", "aaa");
        red.setIngredient('a', Material.STONE);
        red.setIngredient('b', Material.RED_DYE);

        ShapedRecipe grass = new ShapedRecipe(getNamespacedkey(plugin, "grass"), new ItemStack(Material.GRASS_BLOCK, 4));

        grass.shape("abz", "cdz", "zzz");
        grass.setIngredient('a', Material.WHEAT_SEEDS);
        grass.setIngredient('b', Material.BONE_MEAL);
        grass.setIngredient('c', Material.SAND);
        grass.setIngredient('d', Material.COBBLESTONE);

        ShapedRecipe saver = new ShapedRecipe(getNamespacedkey(plugin, "saver"), new customItems().getSaver());

        saver.shape("zaz", "aba", "zaz");
        saver.setIngredient('a', Material.GOLDEN_APPLE);
        saver.setIngredient('b', Material.ENDER_PEARL);

        ShapedRecipe headcut = new ShapedRecipe(getNamespacedkey(plugin, "headcut"), new customItems().getHeadcutter());

        headcut.shape("aaz", "abz", "zcz");
        headcut.setIngredient('a', Material.REDSTONE_BLOCK);
        headcut.setIngredient('b', Material.GOLDEN_AXE);
        headcut.setIngredient('c', Material.STICK);

        ShapedRecipe cacao = new ShapedRecipe(getNamespacedkey(plugin, "cacao"), new ItemStack(Material.COCOA_BEANS, 4));

        cacao.shape("aba", "bcb", "aba");
        cacao.setIngredient('a', Material.SUGAR);
        cacao.setIngredient('b', Material.JUNGLE_LOG);
        cacao.setIngredient('c', Material.VINE);

        ShapedRecipe golder1 = new ShapedRecipe(getNamespacedkey(plugin, "golder1"), new customItems().getGolder1());

        golder1.shape("aab", "aab", "zzb");
        golder1.setIngredient('a', Material.IRON_INGOT);
        golder1.setIngredient('b', Material.STICK);

        ShapedRecipe golder2 = new ShapedRecipe(getNamespacedkey(plugin, "golder2"), new customItems().getGolder2());

        golder2.shape("aab", "aab", "zzb");
        golder2.setIngredient('a', Material.GOLD_INGOT);
        golder2.setIngredient('b', Material.STICK);

        ShapedRecipe gmush = new ShapedRecipe(getNamespacedkey(plugin, "gmush"), new ItemStack(Material.BROWN_MUSHROOM));

        gmush.shape("aaa", "aba", "aaa");
        gmush.setIngredient('a', Material.ROTTEN_FLESH);
        gmush.setIngredient('b', Material.WHEAT_SEEDS);

        ShapedRecipe rmush = new ShapedRecipe(getNamespacedkey(plugin, "rmush"), new ItemStack(Material.RED_MUSHROOM));

        rmush.shape("aba", "bcb", "aba");
        rmush.setIngredient('a', Material.ROTTEN_FLESH);
        rmush.setIngredient('b', Material.RED_DYE);
        rmush.setIngredient('c', Material.WHEAT_SEEDS);

        ShapedRecipe nack = new ShapedRecipe(getNamespacedkey(plugin, "nack"), new ItemStack(Material.NETHERRACK,8));

        nack.shape("aaa", "aba", "aaa");
        nack.setIngredient('a', Material.COBBLESTONE);
        nack.setIngredient('b', Material.LAVA_BUCKET);

        ShapedRecipe myce = new ShapedRecipe(getNamespacedkey(plugin, "myce"), new ItemStack(Material.MYCELIUM,6));

        myce.shape("aaa", "bbb", "ccc");
        myce.setIngredient('a', Material.RED_MUSHROOM);
        myce.setIngredient('b', Material.BROWN_MUSHROOM);
        myce.setIngredient('c', Material.GRASS_BLOCK);

        ShapedRecipe nametag = new ShapedRecipe(getNamespacedkey(plugin, "nametag"), new ItemStack(Material.NAME_TAG));

        nametag.shape("zaa", "bca", "dbz");
        nametag.setIngredient('a', Material.STRING);
        nametag.setIngredient('b', Material.PAPER);
        nametag.setIngredient('c', Material.SLIME_BALL);
        nametag.setIngredient('d', Material.BLACK_DYE);

        ShapedRecipe sponges = new ShapedRecipe(getNamespacedkey(plugin, "sponges"), new ItemStack(Material.SPONGE));

        sponges.shape("aaa", "aba", "aaa");
        sponges.setIngredient('a', Material.SHEARS);
        sponges.setIngredient('b', Material.YELLOW_WOOL);

        ShapedRecipe wart = new ShapedRecipe(getNamespacedkey(plugin, "wart"), new ItemStack(Material.NETHER_WART));

        wart.shape("aaa", "aba", "aaa");
        wart.setIngredient('a', Material.RED_MUSHROOM);
        wart.setIngredient('b', Material.WHEAT_SEEDS);

        ShapedRecipe coalblock = new ShapedRecipe(getNamespacedkey(plugin, "coalblock"), new ItemStack(Material.COAL_BLOCK));

        coalblock.shape("aaa", "aaa", "aaa");
        coalblock.setIngredient('a', Material.CHARCOAL);

        //getCompressedRecipe(Material.GRAVEL), getCompressedRecipe(Material.SAND), getDecompressedRecipe(Material.GRAVEL),

        crafts = Arrays.asList(
            coms, res, grav, sand, red, grass, saver, headcut, cacao, golder1, golder2, gmush, rmush, nack, myce, nametag, sponges, wart, coalblock,
            spawnVEgg(),
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
                harderSpawnRecipe(Material.ROTTEN_FLESH, Material.GOLD_BLOCK, Material.GOLD_NUGGET, "pigmanegg", Material.ZOMBIE_PIGMAN_SPAWN_EGG)
        );
    }

    private static NamespacedKey getNamespacedkey(JavaPlugin plugin, String name){
        return new NamespacedKey(plugin, name);
    }

    public static ShapedRecipe spawnVEgg() {

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
}
