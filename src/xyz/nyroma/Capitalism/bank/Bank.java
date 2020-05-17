package xyz.nyroma.Capitalism.bank;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.Arrays;

public class Bank implements Serializable {
    private String player;
    private float amount = 0;

    public Bank(String pseudo){
        this.player = pseudo;
        BankCache.add(this);
    }

    public void add(float amount){
        this.amount += amount;
    }

    public boolean remove(float amount){
        if(this.amount - amount < 0){
            return false;
        } else {
            this.amount -= amount;
            return true;
        }
    }

    public float getAmount() {
        return amount;
    }

    public String getPlayer() {
        return player;
    }

    public ItemStack getBill(float amount) throws BankException {
        if(this.amount - amount > 0) {
            ItemStack b = new ItemStack(Material.PAPER);
            ItemMeta im = b.getItemMeta();
            im.addEnchant(Enchantment.LOYALTY, 5, true);
            im.setLore(Arrays.asList("Montant du billet :", amount + " Nyr", "Propriétaire :", this.player));
            b.setItemMeta(im);
            this.remove(amount);
            return b;
        } else {
            throw new BankException("Vous n'avez pas l'argent nécessaire.");
        }
    }
}
