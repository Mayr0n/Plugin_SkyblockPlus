package xyz.nyroma.towny;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.nyroma.main.speedy;

import java.util.List;

public class CityListeners implements Listener {
    private CityManager cm = new CityManager();

    public CityListeners(){

    }

    @EventHandler
    public void ifMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        try {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "- Claim par " + cm.getClaimer(loc) + " -"));
        } catch (NotClaimedException e1) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN + "- Territoire libre -"));
        }
    }

    @EventHandler
    public void hasInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        try {
            if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) && !p.isOp()) {
                Location loc = e.getClickedBlock().getLocation();
                if(!canInteract(loc, p)){
                    try {
                        p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + cm.getClaimer(loc) + ", tu ne peux interagir avec ce block !");
                        e.setCancelled(true);
                    } catch (NotClaimedException e1) {
                        e1.printStackTrace();
                        p.sendMessage(ChatColor.RED + e1.getMessage());
                    }
                }
            } else if(e.getAction().equals(Action.RIGHT_CLICK_AIR)){
                ItemStack is = p.getInventory().getItemInMainHand();
                if(is.getType().equals(Material.PAPER)){
                    if(is.hasItemMeta()){
                        try {
                            ItemMeta im = is.getItemMeta();
                            List<String> lore = im.getLore();
                            City city = cm.getCity(lore.get(1));
                            city.getMembersManager().addMember(lore.get(3));
                            for(String pseudo : city.getMembersManager().getMembers()){
                                try {
                                    Player play = speedy.getPlayerByName(p.getServer(), pseudo);
                                    play.sendMessage(ChatColor.GREEN + p.getName() + " a rejoint la ville " + city.getName() + " !");
                                } catch(NullPointerException ee){
                                    Bukkit.broadcastMessage(ChatColor.BLACK + "I'm gonna kill somebody");
                                }
                            }
                            p.getInventory().setItemInMainHand(null);
                        } catch(NotExistException ee){
                            p.sendMessage(ChatColor.RED + "Cette ville n'existe plus !");
                            p.getInventory().setItemInMainHand(null);
                        }
                    }
                }
            }
        } catch(NullPointerException ignored){
        }
    }

    private boolean canInteract(Location loc, Player p){
        if(!p.isOp()){
            try {
                String name = cm.getClaimer(loc);
                try {
                    return name.equals(cm.getCityOfAMember(p).getName());
                } catch (NotExistException e) {
                    return false;
                }
            } catch (NotClaimedException e) {
                return true;
            }
        } else {
            return true;
        }
    }

    @EventHandler
    public void ifHit(EntityDamageByEntityEvent e){
        Entity ent = e.getEntity();
        Location loc = ent.getLocation();
        if(e.getDamager() instanceof Player && !(ent instanceof Creature)){
            Player p = (Player) e.getDamager();
            if(!canInteract(loc, p)){
                try {
                    p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + cm.getClaimer(loc) + ", tu ne peux pas taper ce mob !");
                    e.setCancelled(true);
                } catch (NotClaimedException e1) {
                    e1.printStackTrace();
                    p.sendMessage(ChatColor.RED + e1.getMessage());
                }
            }
        }
    }

}
