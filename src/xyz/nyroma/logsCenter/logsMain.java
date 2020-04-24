package xyz.nyroma.logsCenter;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class logsMain {

    public static ItemStack getLookTool(){
        ItemStack b = new ItemStack(Material.BEDROCK);
        ItemMeta meta = b.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 10, true);
        meta.setLore(Arrays.asList("Pour observer les logs des blocks :O"));
        meta.setLocalizedName("LookTool");
        meta.setDisplayName("LookTool");
        meta.setUnbreakable(true);
        b.setItemMeta(meta);
        return b;
    }


}
