package xyz.nyroma.betterItems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Hashtable;

public class BetterArmorManager {
    private Hashtable<ItemStack, PotionEffect> armor = new Hashtable<>();

    public BetterArmorManager(){
        armor.put(getNHelmet(), new PotionEffect(PotionEffectType.NIGHT_VISION, 100,1));
        armor.put(getFChestplate(), new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 5));
        armor.put(getFLeggings(), new PotionEffect(PotionEffectType.SPEED, 100, 1));
        armor.put(getJBoots(), new PotionEffect(PotionEffectType.JUMP, 100, 1));
    }

    public Hashtable<ItemStack, PotionEffect> build(){
        return this.armor;
    }


    private ItemStack getFChestplate(){
        ItemStack it = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta im = it.getItemMeta();
        im.setLore(Arrays.asList("Slowww falling ! Vous ne risquez plus de prendre des dégâts de chute maintenant !"));
        im.setDisplayName("Winged chestplate");
        it.setItemMeta(im);
        return it;
    }
    private ItemStack getFLeggings(){
        ItemStack it = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta im = it.getItemMeta();
        im.setLore(Arrays.asList("I'm speeeed ! Utile pour se déplacer plus rapidement !"));
        im.setDisplayName("Jogging Adidas");
        it.setItemMeta(im);
        return it;
    }
    private ItemStack getJBoots(){
        ItemStack it = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta im = it.getItemMeta();
        im.setLore(Arrays.asList("Boing ! Utile pour sauter plus de blocks !"));
        im.setDisplayName("Jumper boots");
        it.setItemMeta(im);
        return it;
    }
    private ItemStack getNHelmet(){
        ItemStack it = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta im = it.getItemMeta();
        im.setLore(Arrays.asList("Voilà de quoi toujours bien voir !"));
        im.setDisplayName("Night glasses");
        it.setItemMeta(im);
        return it;
    }

}
