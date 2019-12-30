package fr.sky.tpPack;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static fr.sky.main.speedy.getUUID;

public class tpEtCooldowns implements Listener {
    private HashMap<String, Boolean> cooldowns = new HashMap<>();
    private JavaPlugin plugin;
    private boolean active = false;
    private Inventory inventory;

    public tpEtCooldowns(JavaPlugin plugin){
        this.plugin = plugin;
    }
    private void enable(){
        this.active = true;
    }
    private void disable(){
        this.active = false;
    }

    private Inventory getGui(Player p){
        Inventory inv = Bukkit.createInventory(p, 9, "Tpa");
        inv.setItem(2, getAccept(p));
        inv.setItem(6, getDecline(p));

        return inv;
    }

    public void tpa(Player cible, Player teleport){
        if(cooldowns.containsKey(getUUID(teleport))){
            if(cooldowns.get(getUUID(teleport))) {
                cooldowns.replace(getUUID(teleport), true, false);
                cible.openInventory(getGui(teleport));
                teleport.sendMessage(ChatColor.GREEN + "Une demande de tp lui a été envoyée !");
                System.out.println("Demande de tpa lancée");
                this.enable();
                System.out.println("avant runnable");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        cooldowns.replace(getUUID(teleport), false, true);
                        System.out.println("dans runnable");
                    }
                }.runTaskLater(this.plugin, 2 * 60 * 20L);
                System.out.println("après runnable");
            } else {
                teleport.sendMessage(ChatColor.RED + "Le cooldown de 2 minutes n'est pas fini !");
            }
        } else {
            System.out.println("ok je vois");
        }
    }

    public void addSomeone(Player p){
        if(cooldowns.containsKey(getUUID(p))){
            cooldowns.remove(getUUID(p));
            cooldowns.put(getUUID(p), true);
        } else {
            cooldowns.put(getUUID(p), true);
        }
    }

    public ItemStack getAccept(Player p){
        ItemStack accept = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta accm = accept.getItemMeta();
        accm.setDisplayName("§4ACCEPT");
        accm.setLore(Arrays.asList(p.getName() + " souhaite se téléporter..."));
        accm.setLocalizedName(p.getName());
        accept.setItemMeta(accm);
        return accept;
    }

    public ItemStack getDecline(Player p){
        ItemStack decline = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta declm = decline.getItemMeta();
        declm.setDisplayName("§4DECLINE");
        declm.setLore(Arrays.asList(p.getName() + " souhaite se téléporter..."));
        decline.setItemMeta(declm);
        return decline;
    }

    public void resetAll(ArrayList<Player> players){
        cooldowns.clear();
        for(Player p : players){
            cooldowns.put(getUUID(p), true);
        }
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent e){
        this.addSomeone(e.getPlayer());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if (e.getInventory().getSize() == 9 && e.getInventory().getType().equals(InventoryType.CHEST) && active) {
            e.getPlayer().openInventory(e.getInventory());
            System.out.println("ferme...");
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getSize() == 9 && e.getInventory().getType().equals(InventoryType.CHEST) && active) {
            e.setCancelled(true);
            Player cible = (Player) e.getView().getPlayer();
            if(e.getSlot() == 2){
                this.disable();
                ItemStack i = e.getCurrentItem();
                String pseudo = i.getItemMeta().getLocalizedName();
                try {
                    Player tp = cible.getServer().getPlayer(pseudo);
                    tp.teleport(cible.getLocation());
                    cible.closeInventory();
                    System.out.println("accepté, bon");
                } catch(NullPointerException ee){
                    cible.sendMessage(ChatColor.RED + "Le joueur doit être connecté / être sur le même serveur que toi !");
                    System.out.println("accepté, mauvais");
                }
                System.out.println("accepté");
            } else if(e.getSlot() == 6){
                this.disable();
                cible.closeInventory();
                System.out.println("Refusé");
            }
        }
    }
}
