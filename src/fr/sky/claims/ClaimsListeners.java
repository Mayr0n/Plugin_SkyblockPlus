package fr.sky.claims;

import fr.sky.main.speedy;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClaimsListeners implements Listener {
    private File folder = new File("data/claims/");
    private File file = new File("data/claims/claims.txt");

    public ClaimsListeners(){
        speedy.testFolderExist(folder);
        speedy.testFileExist(file);
    }

    @EventHandler
    public void ifMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        if(speedy.isClaimed(loc)){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "- Territoire : " + getClaimer(loc) + " -"));
        } else {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN + "- Territoire libre -"));
        }
    }

    @EventHandler
    public void ifOpen(PlayerInteractEvent e){
        Player p = e.getPlayer();
        try {
            Location loc = e.getClickedBlock().getLocation();
            String name = getClaimer(loc);
            if ((e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) && !p.isOp()) {
                if (speedy.isClaimed(loc) && !name.equalsIgnoreCase(p.getName())) {
                    p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + getClaimer(loc) + ", tu ne peux interagir avec ce block !");
                    e.setCancelled(true);
                }
            }
        } catch(NullPointerException ignored){

        }
    }

    @EventHandler
    public void ifHit(EntityDamageByEntityEvent e){
        Entity ent = e.getEntity();
        Location loc = ent.getLocation();
        if(e.getDamager() instanceof Player && ent instanceof Animals && speedy.isClaimed(loc)){
            Player p = (Player) e.getDamager();
            if(!p.getName().equalsIgnoreCase(getClaimer(loc)) && !p.isOp()) {
                p.sendMessage(ChatColor.RED + "Ce chunk appartient à " + getClaimer(loc) + ", tu ne peux pas taper ce mob !");
                e.setCancelled(true);
            }
        }
    }

    private String getClaimer(Location loc){
        List<String> claims = speedy.getFileContent(this.file);
        String name = "?";
        for(String c : claims){
            String[] words = c.split(" ");
            if(loc.getChunk().getX() == Integer.parseInt(words[0]) && loc.getChunk().getZ() == Integer.parseInt(words[1])){
                name = words[2];
            }
        }
        return name;
    }


}
