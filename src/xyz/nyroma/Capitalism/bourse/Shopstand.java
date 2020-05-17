package xyz.nyroma.Capitalism.bourse;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Hashtable;

public class Shopstand {

    private String player;
    private Hashtable<Marchandise, Float> ventes = new Hashtable<>();
    private int max = 5;

    public Shopstand(String pseudo){
        this.player = pseudo;

        ShopstandCache.add(this);
    }

    public int getMax() {
        return max;
    }

    public String getPlayer() {
        return player;
    }

    public Hashtable<Marchandise, Float> getVentes() {
        return ventes;
    }

    public long addVente(ItemStack item, float price) {
        if(ventes.keySet().size() < this.max){
            Marchandise m = new Marchandise(item);
            this.ventes.put(m, price);
            return m.getID();
        } else {
            return 0;
        }
    }

    public boolean removeVente(long ID) {
        for(Marchandise m : getVentes().keySet()){
            if(m.getID() == ID){
                ventes.remove(m);
                return true;
            }
        }
        return false;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
