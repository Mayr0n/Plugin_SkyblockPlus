package fr.sky.main;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class listeners implements Listener {

    private int sap = 0;
    private int seeds = 0;
    private int saplings = 0;
    private int water = 0;
    private int flint = 0;

    public listeners() {
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Location loc = b.getLocation();
        int r = new Random().nextInt(10) + 1;
        if (b.getType().equals(Material.SUGAR_CANE)) {
            if (r == 6) {
                loc.getWorld().dropItem(loc, new ItemStack(Material.BAMBOO));
            }
        } else if (b.getType().equals(Material.SAND)) {
            if (r == 6) {
                switch (new Random().nextInt(2)) {
                    case 0:
                        loc.getWorld().dropItem(loc, new ItemStack(Material.SUGAR_CANE));
                        break;
                    case 1:
                        loc.getWorld().dropItem(loc, new ItemStack(Material.CACTUS));
                        break;
                }
            }
            Location loc2 = loc.add(0,1,0);
            if(loc2.getBlock().getType().equals(Material.WATER) && p.getInventory().getItemInMainHand().isSimilar(new customItems().getGolder1())){
                int i = new Random().nextInt(10);
                if (i == 4) {
                    loc.getWorld().dropItem(loc, new ItemStack(Material.GOLD_NUGGET));
                    loc.getWorld().dropItem(loc, new ItemStack(Material.GRAVEL));
                }
                e.setDropItems(false);
            } else if(loc2.getBlock().getType().equals(Material.WATER) && p.getInventory().getItemInMainHand().isSimilar(new customItems().getGolder2())){
                int i = new Random().nextInt(2);
                if (i == 0) {
                    loc.getWorld().dropItem(loc, new ItemStack(Material.GOLD_NUGGET));
                    loc.getWorld().dropItem(loc, new ItemStack(Material.GRAVEL));
                }
                e.setDropItems(false);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i.getType().equals(Material.GOLDEN_AXE) && i.getItemMeta().getEnchants().containsValue(10)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 2));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        System.out.println("mort : " + e.getDeathMessage());
        Player p = e.getEntity();
        Location loc = p.getLocation();
        p.sendMessage(ChatColor.RED + "Tu es mort en x = " + (int) loc.getX() + ", y = " + (int) loc.getY() + ", z = " + (int) loc.getZ() + "\n Tu as 5 minutes pour récupérer ton stuff !");
        if (e.getDeathMessage().contains("blown up by Creeper")) {
            e.setDeathMessage(ChatColor.DARK_GREEN + "Aw maaaan ! A Creeper blew up " + p.getName());
        } else if (e.getDeathMessage().contains("shot by Skeleton") && !p.getName().equals("Attiyas")) {
            e.setDeathMessage(ChatColor.DARK_GREEN + "Is it Attiyas ? Oh no, it's " + p.getName() + " who died because of a skeleton, f in the chat everyone");
        } else if (e.getDeathMessage().contains("fell out of the world")) {
            if (p.getInventory().getItemInOffHand().isSimilar(new customItems().getSaver())) {
                e.setDeathMessage(ChatColor.GREEN + p.getName() + " a été sauvé par son saver !");
                e.setKeepInventory(true);
                e.setKeepLevel(true);
                p.getInventory().setItemInOffHand(null);
            } else {
                e.setKeepInventory(false);
                e.setKeepLevel(false);
                e.setDeathMessage(ChatColor.DARK_GREEN + "Oops, " + p.getName() + " est tombé, f in the chat everyone");
            }
        } else {
            e.setDeathMessage(ChatColor.DARK_GREEN + e.getDeathMessage());
        }
    }

    @EventHandler
    public void onKilled(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player p = (Player) e.getDamager();
            Player d = (Player) e.getEntity();
            ItemStack i = p.getInventory().getItemInMainHand();
            if (i.getType().equals(Material.GOLDEN_AXE) && i.getItemMeta().getEnchants().containsValue(10) && e.getDamage() >= d.getHealth()) {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                Bukkit.getUnsafe().modifyItemStack(head, "{SkullOwner:\"" + d.getName() + "\"}");
                d.getLocation().getWorld().dropItem(d.getLocation(), head);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        System.out.println(p.getName() + " a rejoint ! IP : " + p.getAddress().getAddress().toString());
        ChatColor color;
        if (p.isOp()) {
            color = ChatColor.RED;
        } else {
            color = ChatColor.AQUA;
        }
        e.setJoinMessage(color + p.getName() + ChatColor.GREEN + " est connecté !");
    }

    @EventHandler
    public void onDisconect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        System.out.println(p.getName() + " est parti ! IP : " + p.getAddress().getAddress().toString());
        ChatColor color;
        if (p.isOp()) {
            color = ChatColor.RED;
        } else {
            color = ChatColor.AQUA;
        }
        e.setQuitMessage(color + p.getName() + ChatColor.DARK_GREEN + " s'est déconnecté !");
    }

    @EventHandler
    public void pssssBoom(EntityExplodeEvent e) {
        Entity ent = e.getEntity();
        if (ent.getType().equals(EntityType.CREEPER)) {
            e.blockList().removeAll(e.blockList());
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        Entity ent = e.getEntity();
        EntityType type = ent.getType();
        Location loc = ent.getLocation();
        if ((type.equals(EntityType.SKELETON) || type.equals(EntityType.GHAST)) && loc.getBlock().getBiome().equals(Biome.NETHER)) {
            if (new Random().nextInt(10) == 1) {
                ent.remove();
                loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
            }
        } else if(type.equals(EntityType.MAGMA_CUBE)){
            if(new Random().nextInt(5) == 1){
                ent.remove();
                loc.getWorld().spawnEntity(loc, EntityType.BLAZE);
            }
        } else if(type.equals(EntityType.PIG_ZOMBIE)){
            int i = new Random().nextInt(20);
            if(i == 7){
                ent.remove();
                loc.getWorld().spawnEntity(loc, EntityType.BLAZE);
            } else if(i == 17){
                ent.remove();
                loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        Block b = loc.getBlock();
        Block bl = loc.add(0, 1, 0).getBlock();
        BlockData b2 = bl.getBlockData();
        if (b.getType().equals(Material.GRASS)) {
            sap++;
            if (sap == 30) {
                sap = 0;
                Random r = new Random();
                if (r.nextInt(2) == 0) {
                    Material sapling = Material.OAK_SAPLING;
                    switch (r.nextInt(6)) {
                        case 0:
                            sapling = Material.ACACIA_SAPLING;
                            break;
                        case 1:
                            sapling = Material.SPRUCE_SAPLING;
                            break;
                        case 2:
                            sapling = Material.BIRCH_SAPLING;
                            break;
                        case 3:
                            sapling = Material.JUNGLE_SAPLING;
                            break;
                        case 4:
                            sapling = Material.DARK_OAK_SAPLING;
                            break;
                        case 5:
                            sapling = Material.OAK_SAPLING;
                            break;
                    }
                    loc.getWorld().dropItem(loc, new ItemStack(sapling));
                } else {
                    loc.getWorld().dropItem(loc, new ItemStack(Material.WHEAT_SEEDS));
                }
            }
            int r = new Random().nextInt(100);
            if (r == 82) {
                loc.getWorld().dropItem(loc, new ItemStack(Material.PUMPKIN_SEEDS));
            } else if (r == 24) {
                loc.getWorld().dropItem(loc, new ItemStack(Material.MELON_SEEDS));
            }
        } else if (b2 instanceof Ageable) {
            seeds++;
            if (seeds == 30) {
                seeds = 0;
                Ageable age = (Ageable) b2;
                int m = age.getAge();
                if (m < age.getMaximumAge()) {
                    age.setAge(m + 1);
                }
                bl.setBlockData(age);
                loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 100);
            }
        } else if (b.getBlockData() instanceof Sapling) {
            saplings++;
            if (saplings == 50) {
                b.getLocation().getWorld().generateTree(b.getLocation(), TreeType.BIG_TREE);
                loc.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, loc, 100);
            }
        }
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            World w = new WorldCreator("nether").createWorld();

            Location to = e.getTo();
            switch(e.getFrom().getWorld().getName()){
                case "world":
                    to.setWorld(w);
                    break;
                case "nether":
                    to.setWorld(new WorldCreator("world").createWorld());
                    break;
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            Block b = e.getClickedBlock();
            Location loc = b.getLocation().add(0, 1, 0);
            try {
                if (b.getType().equals(Material.CAULDRON)) {
                    water++;
                    if (water == 50) {
                        water = 0;
                        p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.WATER_BUCKET));
                    }
                } else if ((b.getType().equals(Material.GRASS_BLOCK) || b.getType().equals(Material.DIRT)) && p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                    flint++;
                    if (flint == 3) {
                        flint = 0;
                        p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.FLINT));
                    }
                } else if (b.getType().equals(Material.MAGMA_BLOCK) && p.getInventory().getItemInMainHand().getType().equals(Material.BUCKET)) {
                    p.getInventory().setItemInMainHand(null);
                    loc.getWorld().dropItem(loc, new ItemStack(Material.LAVA_BUCKET));
                }
            } catch (NullPointerException ignored) {
            }
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        Entity ent = e.getEntity();
        EntityType type = ent.getType();
        Location loc = ent.getLocation();
        if (type.equals(EntityType.ZOMBIE) || type.equals(EntityType.SKELETON)) {
            int r = new Random().nextInt(20);
            if (r == 2) {
                loc.getWorld().dropItem(loc, new ItemStack(Material.IRON_INGOT));
            }
        } else if (type.equals(EntityType.CREEPER)) {
            int r = new Random().nextInt(100);
            if (r == 2) {
                loc.getWorld().dropItem(loc, new ItemStack(Material.GOLD_INGOT));
            }
        } else if (type.equals(EntityType.ENDERMAN)) {
            int r = new Random().nextInt(10);
            if (r == 2) {
                loc.getWorld().dropItem(loc, new ItemStack(Material.DIAMOND));
            }
        }
    }
}
