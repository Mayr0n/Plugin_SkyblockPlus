package xyz.nyroma.Capitalism.bourse;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Marchandise {
    private int amount;
    private List<String> enchants = new ArrayList<>();
    private List<Integer> levels = new ArrayList<>();
    private long ID;
    private String material;

    public Marchandise(ItemStack is) {
        this.amount = is.getAmount();
        this.material = is.getType().toString();
        ItemMeta im = is.getItemMeta();
        if(im.hasEnchants()){
            for(Enchantment ench : im.getEnchants().keySet()){
                if(ench != null) {
                    this.enchants.add(ench.getName());
                    this.levels.add(im.getEnchants().get(ench));
                }
            }
        }
        long i = new Random().nextLong();
        while(ShopstandCache.hasID(i)){
            i = new Random().nextLong();
        }
        this.ID = i;
    }

    public int getAmount() {
        return amount;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public List<String> getEnchants() {
        return enchants;
    }

    public long getID() {
        return ID;
    }

    public String getMaterial() {
        return material;
    }
}
