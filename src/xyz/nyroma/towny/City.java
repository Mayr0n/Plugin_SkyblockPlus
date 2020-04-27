package xyz.nyroma.towny;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.*;

public class City implements Serializable {
    private String royaume;
    private String name;
    private String owner;
    private ClaimsManager claimsManager;
    private MembersManager membersManager;
    private MoneyManager moneyManager;
    private RelationsManager relationsManager;
    private long id;


    public City(String name, String royaume, String owner) throws TownyException {
            if (new CityManager().isAlreadyOwner(owner)) {
                throw new TownyException("Tu es déjà l'owner d'une ville !");
            }
            if(CitiesCache.contains(name)){
                throw new TownyException("Cette ville existe déjà !");
            }
            if(name.equals("all")){
                throw new TownyException("Tu ne peux pas avoir ce nome de ville.");
            }

            long id = new Random().nextLong();
            while(CitiesCache.hasID(id)){
                id = new Random().nextLong();
            }

            this.id = id;
            this.name = name;
            this.royaume = royaume;
            this.owner = owner;
            this.claimsManager = new ClaimsManager();
            this.membersManager = new MembersManager();
            this.membersManager.addMember(owner);
            this.moneyManager = new MoneyManager();
            this.relationsManager = new RelationsManager();
            CitiesCache.add(this);
            CitiesCache.addID(this.id);

        System.out.println("Ville " + this.name + " créée !");
    }

    public RelationsManager getRelationsManager() {
        return this.relationsManager;
    }

    public String getOwner(){
        return this.owner;
    }

    public String getRoyaume() {
        return this.royaume;
    }

    public String getName() {
        return this.name;
    }

    public long getID(){
        return this.id;
    }

    public ClaimsManager getClaimsManager() {
        return this.claimsManager;
    }

    public MembersManager getMembersManager() {
        return this.membersManager;
    }

    public MoneyManager getMoneyManager() {
        return this.moneyManager;
    }

    public City getCity(){
        return this;
    }

    public void rename(String name) {
        this.name = name;
    }

    public void changeOwner(Player p) {
        this.owner = p.getName();
    }

    public boolean sendInvit(Player sender, Player toAdd) {
        if(isOwner(sender)) {
            ItemStack is = new ItemStack(Material.PAPER);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName("Invitation");
            im.setLore(Arrays.asList("Invitation à rejoindre la ville :", this.name, "pour :", toAdd.getName()));
            im.addEnchant(Enchantment.DURABILITY, 10, true);
            is.setItemMeta(im);

            toAdd.getInventory().addItem(is);
            return true;
        } else {
            return false;
        }
    }

    private boolean isOwner(Player p) {
        return p.getName().equals(this.owner);
    }

    public boolean remove(Player p){
        if(isOwner(p)){
            try {
                new CityManager().removeCity(this);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
