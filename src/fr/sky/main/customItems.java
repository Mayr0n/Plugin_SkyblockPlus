package fr.sky.main;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class customItems {

    public ItemStack getSaver() {
        ItemStack c = new ItemStack(Material.BELL);
        ItemMeta cm = c.getItemMeta();
        cm.setDisplayName("Saver");
        cm.addEnchant(Enchantment.DURABILITY, 10, true);
        c.setItemMeta(cm);
        return c;
    }

    public ItemStack getHeadcutter() {
        ItemStack c = new ItemStack(Material.GOLDEN_AXE);
        ItemMeta cm = c.getItemMeta();
        cm.setDisplayName("Headcutter");
        cm.addEnchant(Enchantment.DURABILITY, 10, true);
        cm.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier("generic.attackSpeed", -3.5, AttributeModifier.Operation.ADD_NUMBER));
        c.setItemMeta(cm);
        return c;
    }

    public ItemStack getGolder1(){
        ItemStack g = new ItemStack(Material.IRON_HOE);
        ItemMeta gm = g.getItemMeta();
        gm.setDisplayName("Cheap golder");
        gm.addEnchant(Enchantment.DURABILITY, 10, true);
        g.setItemMeta(gm);
        return g;
    }

    public ItemStack getGolder2(){
        ItemStack g = new ItemStack(Material.GOLDEN_HOE);
        ItemMeta gm = g.getItemMeta();
        gm.setDisplayName("Better golder");
        gm.addEnchant(Enchantment.DURABILITY, 10, true);
        g.setItemMeta(gm);
        return g;
    }

}
