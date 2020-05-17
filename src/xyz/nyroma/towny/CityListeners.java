package xyz.nyroma.towny;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.nyroma.main.NotFoundException;
import xyz.nyroma.main.speedy;

import java.util.Arrays;
import java.util.List;

public class CityListeners implements Listener {
    private CityManager cm = new CityManager();

    public CityListeners() {

    }

    @EventHandler
    public void hasInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        try {
            if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) && !p.isOp()) {
                Location loc = e.getClickedBlock().getLocation();
                if (cantInteract(loc, p)) {
                    if(cm.getClaimer(loc).isPresent()){
                        City city = cm.getClaimer(loc).get();
                        p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + city.getName() + ", tu ne peux interagir avec ce block !");
                        e.setCancelled(true);
                    }
                }
            } else if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                ItemStack is = p.getInventory().getItemInMainHand();
                if (is.getType().equals(Material.PAPER)) {
                    if (is.hasItemMeta()) {
                        try {
                            ItemMeta im = is.getItemMeta();
                            if (is.containsEnchantment(Enchantment.DURABILITY)) {
                                List<String> lore = im.getLore();
                                City city = CitiesCache.get(lore.get(1));
                                city.getMembersManager().addMember(lore.get(3));
                                for (String pseudo : city.getMembersManager().getMembers()) {
                                    try {
                                        Player play = speedy.getPlayerByName(p.getServer(), pseudo);
                                        play.sendMessage(ChatColor.GREEN + p.getName() + " a rejoint la ville " + city.getName() + " !");
                                    } catch (NotFoundException ee) {
                                        p.sendMessage(ChatColor.RED + "Une erreur est survenue. CityListeners:62 ");
                                    }
                                }
                                p.getInventory().setItemInMainHand(null);
                            }
                        } catch (TownyException ee) {
                            p.sendMessage(ChatColor.RED + "Cette ville n'existe plus !");
                            p.getInventory().setItemInMainHand(null);
                        }
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    private boolean cantInteract(Location loc, Player p) {
        if (!p.isOp()) {
            if(cm.getClaimer(loc).isPresent()){
                City city = cm.getClaimer(loc).get();
                List<Boolean> booleans = Arrays.asList(
                        city.getRelationsManager().getAllies().contains(cm.getCityOfMember(p).orElse(null)),
                        city.getRelationsManager().getNice(),
                        city.getMembersManager().getMembers().contains(p.getName())
                );
                return !booleans.contains(true);
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @EventHandler
    public void tntExplode(EntityExplodeEvent e) {
        Entity ent = e.getEntity();
        if (ent instanceof TNTPrimed) {
            if(cm.getClaimer(ent.getLocation()).isPresent()){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPiston(BlockPistonExtendEvent e) {
            for (Block b : e.getBlocks()) {
                if (b.getType().equals(Material.SLIME_BLOCK) || b.getType().equals(Material.HONEY_BLOCK)) {
                    if(cm.getClaimer(b.getLocation()).isPresent()){
                        e.setCancelled(true);
                        return;
                    }
                }
            }
    }

    @EventHandler
    public void ifHit(EntityDamageByEntityEvent e) {
        Entity ent = e.getEntity();
        Location loc = ent.getLocation();
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (!(ent instanceof Creature)) {
                if (cantInteract(ent.getLocation(), p)) {
                    if(cm.getClaimer(loc).isPresent()){
                        City city = cm.getClaimer(loc).get();
                        p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + city + ", tu ne peux pas taper ce mob !");
                        e.setCancelled(true);
                    }
                }
            }
            if (ent instanceof Villager) {
                if (cantInteract(ent.getLocation(), p)) {
                    if(cm.getClaimer(loc).isPresent()){
                        City city = cm.getClaimer(loc).get();
                        String message = ChatColor.BLACK + p.getName() + " a frappé un PNJ dans la ville " + city.getName() + " !";
                        Bukkit.broadcastMessage(message);
                        System.out.println(message);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 50));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Villager) {
            if(cm.getClaimer(e.getEntity().getLocation()).isPresent()){
                if (!e.getCause().equals(EntityDamageEvent.DamageCause.FIRE)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void openInv(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            Inventory inv = e.getInventory();
            if (inv.getType().equals(InventoryType.MERCHANT) && cantInteract(p.getLocation(), p)) {
                if(cm.getClaimer(p.getLocation()).isPresent()){
                    City city = cm.getClaimer(p.getLocation()).get();
                    p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + city.getName() + ", tu ne peux pas interagir avec ce PNJ !");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if (cantInteract(p.getLocation(), p)) {
            if(cm.getClaimer(p.getLocation()).isPresent()){
                City city = cm.getClaimer(p.getLocation()).get();
                p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + city.getName() + ", tu ne peux pas interagir avec cette entité !");
                e.setCancelled(true);
            }
        }
    }
}
