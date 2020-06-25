package xyz.nyroma.Capitalism.bank;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Hashtable;

public class Bank implements Serializable {
    private String player;
    private float amount = 0;
    private Hashtable<Transaction, Float> transactions = new Hashtable<>();

    public Bank(String pseudo){
        this.player = pseudo;
        BankCache.add(this);
    }

    public void add(float amount, Transaction type){
        this.amount += amount;
        if(this.transactions == null){
            this.transactions = new Hashtable<>();
        }
        this.transactions.put(type, amount);
    }

    public boolean remove(float amount, Transaction type){
        if(this.amount - amount < 0){
            return false;
        } else {
            this.amount -= amount;
            if(this.transactions == null){
                this.transactions = new Hashtable<>();
            }
            this.transactions.put(type, amount);
            return true;
        }
    }

    public void changePlayer(String pseudo){
        this.player = pseudo;
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
            this.remove(amount, Transaction.PLAYER_REMOVE);
            return b;
        } else {
            throw new BankException("Vous n'avez pas l'argent nécessaire.");
        }
    }
}
