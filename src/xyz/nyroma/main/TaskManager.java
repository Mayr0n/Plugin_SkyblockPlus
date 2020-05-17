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
import xyz.nyroma.Capitalism.ScoreboardManager;
import xyz.nyroma.betterItems.BetterArmorManager;
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

        ScoreboardManager sm = new ScoreboardManager(server);
        sm.build();

        new BukkitRunnable() {
            @Override
            public void run() {
                sm.refresh();
            }
        }.runTaskTimer(plugin, 20, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                sm.setScoreboard(server);
            }
        }.runTaskTimer(plugin, 60*20, 60*20);

        new BukkitRunnable() {
            @Override
            public void run() {
                cm.applyTaxes(server);
            }
        }.runTaskTimer(plugin, 12*3600*20L, 12*3600*20L);


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
        if(cm.getClaimer(loc).isPresent()){
            City city = cm.getClaimer(loc).get();
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "- Claim par " + city.getName() + " -"));
        } else {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN + "- Territoire libre -"));
        }
    }

    private void checkEnemy(Player p){
        if(cm.getClaimer(p.getLocation()).isPresent()){
            City city = cm.getClaimer(p.getLocation()).get();
            if(cm.getCityOfMember(p).isPresent()){
                City ciOfM = cm.getCityOfMember(p).get();
                if((city.getRelationsManager().getEnemies().contains(ciOfM) || city.getRelationsManager().getEvil()) && !city.getMembersManager().isMember(p.getName())){
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100,5));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100,5));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100,5));
                }
            }
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
