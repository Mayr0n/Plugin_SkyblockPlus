package xyz.nyroma.towny;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.nyroma.main.speedy;

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
                    try {
                        p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + cm.getClaimer(loc).getName() + ", tu ne peux interagir avec ce block !");
                        e.setCancelled(true);
                    } catch (TownyException ignored) {
                    }
                }
            } else if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                ItemStack is = p.getInventory().getItemInMainHand();
                if (is.getType().equals(Material.PAPER)) {
                    if (is.hasItemMeta()) {
                        try {
                            ItemMeta im = is.getItemMeta();
                            List<String> lore = im.getLore();
                            City city = CitiesCache.get(lore.get(1));
                            city.getMembersManager().addMember(lore.get(3));
                            for (String pseudo : city.getMembersManager().getMembers()) {
                                try {
                                    Player play = speedy.getPlayerByName(p.getServer(), pseudo);
                                    play.sendMessage(ChatColor.GREEN + p.getName() + " a rejoint la ville " + city.getName() + " !");
                                } catch (NullPointerException ee) {
                                    Bukkit.broadcastMessage(ChatColor.BLACK + "I'm gonna kill somebody");
                                }
                            }
                            p.getInventory().setItemInMainHand(null);
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
            try {
                return !cm.getClaimer(loc).getRelationsManager().getAllies().contains(cm.getCityOfMember(p)) ||
                        !cm.getClaimer(loc).getRelationsManager().getNice() || !cm.getClaimer(loc).getMembersManager().isMember(p.getName());
            } catch (TownyException e) {
                return true;
            }
        } else {
            return false;
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
                    try {
                        p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + cm.getClaimer(loc).getName() + ", tu ne peux pas taper ce mob !");
                        e.setCancelled(true);
                    } catch (TownyException ignored) {
                    }
                }
            }
            if (ent instanceof Villager) {
                if (cantInteract(ent.getLocation(), p)) {
                    try {
                        String message = ChatColor.BLACK + p.getName() + " a frappé un PNJ dans la ville " + cm.getClaimer(ent.getLocation()).getName() + " !";
                        Bukkit.broadcastMessage(message);
                        System.out.println(message);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 50));
                        e.setCancelled(true);
                    } catch (TownyException ignored) {
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Villager) {
            try {
                new CityManager().getClaimer(e.getEntity().getLocation());
                if(!e.getCause().equals(EntityDamageEvent.DamageCause.FIRE)){
                    e.setCancelled(true);
                }
            } catch (TownyException ignored) {
            }
        }
    }

    @EventHandler
    public void openInv(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            Inventory inv = e.getInventory();
            if (inv.getType().equals(InventoryType.MERCHANT) && cantInteract(p.getLocation(), p)) {
                try {
                    p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + cm.getClaimer(p.getLocation()).getName() + ", tu ne peux pas interagir avec ce PNJ !");
                    e.setCancelled(true);
                } catch (TownyException ignored) {
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        if (cantInteract(p.getLocation(), p)) {
            try {
                p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + cm.getClaimer(p.getLocation()).getName() + ", tu ne peux pas interagir avec cette entité !");
                e.setCancelled(true);
            } catch (TownyException ignored) {
            }
        }
    }
}
