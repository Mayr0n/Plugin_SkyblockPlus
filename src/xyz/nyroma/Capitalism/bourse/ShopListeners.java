package xyz.nyroma.Capitalism.bourse;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.nyroma.Capitalism.bank.Bank;
import xyz.nyroma.Capitalism.bank.BankCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShopListeners implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Inventory inv = e.getClickedInventory();
        try {
            List<Player> players = new ArrayList<>();
            for(HumanEntity hu : inv.getViewers()){
                if(hu instanceof Player){
                    players.add((Player) hu);
                }
            }
            ItemStack is = e.getCurrentItem();
            for(Player p : players) {
                if (inv.getHolder() instanceof ShopCommands) {
                    e.setCancelled(true);
                    p.closeInventory();
                    if(!is.getType().equals(Material.AIR)){
                        p.openInventory(new TradeManager(is).getInventory());
                    }
                }
                if (inv.getHolder() instanceof TradeManager) {
                    e.setCancelled(true);
                    ItemMeta im = is.getItemMeta();
                    ItemStack it = is;
                    im.setLore(Arrays.asList(""));
                    it.setItemMeta(im);
                    float price = Float.parseFloat(im.getLore().get(3).substring(2).split(" ")[0]);
                    Bank buyer = BankCache.get(p.getName());
                    Bank seller = BankCache.get(im.getLore().get(1).substring(2));
                    if(buyer.remove(price)){
                        seller.add(price);
                        p.getInventory().addItem(it);
                        p.sendMessage(ChatColor.GREEN + String.valueOf(price) + " Nyr ont été débités de votre compte.");
                        p.closeInventory();
                        try {
                            ShopstandCache.get(seller.getPlayer()).removeVente(Long.parseLong(im.getLore().get(5)));
                        } catch (ShopException e1) {
                            System.out.println("Une erreur est survenue.");
                            e1.printStackTrace();
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Vous n'avez pas assez d'argent !");
                    }
                }
            }
        } catch(NullPointerException ignored){
        }
    }

}
