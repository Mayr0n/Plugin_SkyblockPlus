package xyz.nyroma.main;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.nyroma.Capitalism.ScoreboardManager;
import xyz.nyroma.betterItems.BetterArmor;
import xyz.nyroma.betterItems.BetterArmors;
import xyz.nyroma.towny.City;
import xyz.nyroma.towny.CityManager;

public class TaskManager {
    private JavaPlugin plugin;
    private Server server;
    private CityManager cm = new CityManager();

    public TaskManager(JavaPlugin plugin, Server server) {
        this.plugin = plugin;
        this.server = server;
    }

    public void build() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : server.getOnlinePlayers()) {
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
        }.runTaskTimer(plugin, 60 * 20, 60 * 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                cm.applyTaxes(server);
            }
        }.runTaskTimer(plugin, 12 * 3600 * 20L, 12 * 3600 * 20L);


    }

    public void applyEffects(Player p) {
            ItemStack is = p.getInventory().getItemInMainHand();
            if (is.getType().equals(Material.GOLDEN_AXE) && is.hasItemMeta() && is.getItemMeta() != null
                    && is.getItemMeta().hasEnchants() && is.getItemMeta().getEnchants().size() > 0 && is.getItemMeta().getEnchants().containsValue(10)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 2));
            }

            for (ItemStack item : p.getInventory().getArmorContents()) {
                if(item != null) {
                    if (item.hasItemMeta() && item.getItemMeta() != null) {
                        ItemMeta im = item.getItemMeta();
                        if (im.hasLore() && im.getLore() != null && im.getLore().size() > 0) {
                            BetterArmor[] bt = new BetterArmor[]{new BetterArmor(BetterArmors.NIGHT_HELMET),
                                    new BetterArmor(BetterArmors.WINGED_CHESTPLATE),
                                    new BetterArmor(BetterArmors.SPEEDY_LEGGINGS),
                                    new BetterArmor(BetterArmors.JUMPER_BOOTS)};
                            for (BetterArmor ba : bt) {
                                for (int i = 1; i <= 5; i++) {
                                    ItemStack tool = ba.getItemStack(i);
                                    if (im.getLore().equals(tool.getItemMeta().getLore())) {
                                        if(i < 5) {
                                            p.addPotionEffect(ba.getEffect().createEffect(300, i), true);
                                        } else {
                                            p.addPotionEffect(ba.getEffect().createEffect(300, (int) (i*1.5)), true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            ItemStack im = p.getInventory().getItemInOffHand();
            if (im.getType().equals(Material.FEATHER) && im.hasItemMeta() && im.getItemMeta() != null && im.getItemMeta().hasEnchants()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 5));
            }
    }

    public void checkClaims(Player p) {
        Location loc = p.getLocation();
        if (cm.getClaimer(loc).isPresent()) {
            City city = cm.getClaimer(loc).get();
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "- Claim par " + city.getName() + " -"));
        } else {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_GREEN + "- Territoire libre -"));
        }
    }

    private void checkEnemy(Player p) {
        if (cm.getClaimer(p.getLocation()).isPresent()) {
            City city = cm.getClaimer(p.getLocation()).get();
            if (cm.getCityOfMember(p).isPresent()) {
                City ciOfM = cm.getCityOfMember(p).get();
                if ((city.getRelationsManager().getEnemies().contains(ciOfM) || city.getRelationsManager().getEvil()) && !city.getMembersManager().isMember(p.getName())) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 5));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 5));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100, 5));
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
