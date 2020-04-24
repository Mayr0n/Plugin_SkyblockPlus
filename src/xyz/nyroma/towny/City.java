package xyz.nyroma.towny;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static xyz.nyroma.towny.CityManager.cities;

public class City implements Serializable {
    private String royaume;
    private String name;
    private String owner;
    private ClaimsManager claimsManager;
    private MembersManager membersManager;
    private MoneyManager moneyManager;


    public City(String name, String royaume, Player owner) throws TownyException, AlreadyOwnerException {
        try {
            if (new CityManager().isAlreadyOwner(owner.getName())) {
                throw new AlreadyOwnerException();
            }
        } catch(NotExistException ignored){
        }
            this.name = name;
            this.royaume = royaume;
            this.owner = owner.getName();
            this.claimsManager = new ClaimsManager(this);
            this.membersManager = new MembersManager(this);
            this.membersManager.addMember(owner.getName());
            this.moneyManager = new MoneyManager(this);
            cities.add(this);

            try {
                new CityManager().serializeCity(this);
            } catch (IOException e) {
                e.printStackTrace();
                throw new TownyException();
            }

        System.out.println("Ville " + this.name + " créée !");
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

    public boolean rename(String name, Player p) {
        if (isOwner(p)) {
            this.name = name;
            return true;
        } else {
            return false;
        }
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
                super.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
