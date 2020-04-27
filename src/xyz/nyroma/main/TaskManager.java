package xyz.nyroma.main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.nyroma.betterItems.BetterArmorManager;
import xyz.nyroma.craftsCenter.ChangedRecipes;
import xyz.nyroma.towny.City;
import xyz.nyroma.towny.CityManager;
import xyz.nyroma.towny.TownyException;

import java.util.Enumeration;
import java.util.Hashtable;

public class TaskManager {
    private JavaPlugin plugin;
    private Server server;
    private CityManager cm = new CityManager();

    public TaskManager(JavaPlugin plugin, Server server){
        this.plugin = plugin;
        this.server = server;
    }

    public void build(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player p : server.getOnlinePlayers()){
                    applyEffects(p);
                    checkClaims(p);
                    checkEnemy(p);
                }
            }
        }.runTaskTimer(plugin, 5, 5);
    }

    public void applyEffects(Player p){
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i.getType().equals(Material.GOLDEN_AXE) && i.getItemMeta().getEnchants().containsValue(10)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
        }


        Hashtable<ItemStack, PotionEffect> armor = new BetterArmorManager().build();
        Enumeration<ItemStack> it = armor.keys();

        try {
            while (it.hasMoreElements()) {
                ItemStack item = it.nextElement();
                PlayerInventory inv = p.getInventory();
                if (hasLore(item)) {
                    for (ItemStack its : inv.getArmorContents()) {
                        if (hasLore(its)) {
                            if (item.getItemMeta().getLore().equals(its.getItemMeta().getLore())) {
                                p.addPotionEffect(armor.get(item));
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException ee) {
            System.out.println("Fuck");
        }

        if(p.getInventory().contains(ChangedRecipes.getSatur())){
            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100, 5));
        }

        try {
            ItemStack im = p.getInventory().getItemInOffHand();
            if(im.getType().equals(Material.FEATHER) && im.getItemMeta().hasEnchants()){
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100,5));
            }
        } catch(NullPointerException ignored){
        }
    }
    public void checkClaims(Player p){
        Location loc = p.getLocation();
        try {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "- Claim par " + cm.getClaimer(loc).getName() + " -"));
        } catch (TownyException e1) {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN + "- Territoire libre -"));
        }
    }

    private void checkEnemy(Player p){
        try {
            City city = cm.getClaimer(p.getLocation());
            if((city.getRelationsManager().getEnemies().contains(cm.getCityOfMember(p)) || city.getRelationsManager().getEvil()) && !city.getMembersManager().isMember(p.getName())){
                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100,5));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100,5));
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100,5));
            }
        } catch (TownyException ignored) {
        }
    }

    private boolean hasLore(ItemStack item) {
        try {
            if (item.hasItemMeta()) {
                return item.getItemMeta().hasLore();
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }

}
