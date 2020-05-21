package xyz.nyroma.Capitalism.jobs;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftItem;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import xyz.nyroma.Capitalism.bank.Bank;
import xyz.nyroma.Capitalism.bank.BankCache;

import java.util.List;

public class JobListeners implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if(BankCache.get(p.getName()).remove(50)) p.sendMessage(ChatColor.RED + "Vous avez perdu 50 Nyr lors de votre mort.");
    }

    @EventHandler
    public void onTrade(InventoryClickEvent e){
        if(e.getInventory().getType().equals(InventoryType.MERCHANT)){
            Inventory inv  = e.getInventory();
            List<HumanEntity> players = inv.getViewers();
            for(HumanEntity p : players){
                try {
                    if(JobManager.getJob(p.getName()).equals(Job.TRADER)){
                        Bank bank = BankCache.get(p.getName());
                        bank.add(0.5f);
                    }
                } catch(JobException ignored){
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            Player p = (Player) e.getDamager();
            LivingEntity ent = (LivingEntity) e.getEntity();
            try {
                if(e.getDamage() >= ent.getHealth() && JobManager.getJob(p.getName()).equals(Job.HUNTER)){
                    Bank bank = BankCache.get(p.getName());
                        switch (ent.getType()) {
                            case WITHER:
                            case ELDER_GUARDIAN:
                                bank.add(200);
                                break;
                            case SHULKER:
                                bank.add(0.5f);
                                break;
                            case WITHER_SKELETON:
                            case GHAST:
                                bank.add(0.2f);
                                break;
                            case ENDERMAN:
                                bank.add(0.05f);
                                break;
                            case BLAZE:
                            case WITCH:
                            case GUARDIAN:
                            case MAGMA_CUBE:
                            case PHANTOM:
                                bank.add(0.1f);
                                break;
                            case CREEPER:
                            case SKELETON:
                            case ZOMBIE:
                            case PIG_ZOMBIE:
                            case SPIDER:
                                bank.add(0.01f);
                                break;
                        }
                }
            } catch (JobException ignored) {
            }
        }
    }

    @EventHandler
    public void onDragonKilled(EntityDeathEvent e){
        if(e.getEntity().getType().equals(EntityType.ENDER_DRAGON)){
            Entity ent = e.getEntity();
            for(Entity en : ent.getNearbyEntities(20,20,20)){
                if(en instanceof Player){
                    Player p = (Player) en;
                    try {
                        if(JobManager.getJob(p.getName()).equals(Job.HUNTER)){
                            BankCache.get(p.getName()).add(100);
                        }
                    } catch (JobException ignored) {
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFarm(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();
        if (b.getType().equals(Material.WHEAT_SEEDS) ||
                b.getType().equals(Material.BEETROOT_SEEDS) ||
                b.getType().equals(Material.MELON_SEEDS) ||
                b.getType().equals(Material.PUMPKIN_SEEDS) ||
                b.getType().equals(Material.CARROTS) ||
                b.getType().equals(Material.POTATOES)
        ) {
            Player p = e.getPlayer();
            try {
                if (JobManager.getJob(p.getName()).equals(Job.FARMER)) {
                    Bank bank = BankCache.get(p.getName());
                    bank.add(0.05f);
                }
            } catch (JobException ignored) {
            }
        }
    }

    @EventHandler
    public void onFarm(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType().equals(Material.WHEAT) ||
                b.getType().equals(Material.BEETROOTS) ||
                b.getType().equals(Material.MELON) ||
                b.getType().equals(Material.PUMPKIN) ||
                b.getType().equals(Material.CARROTS) ||
                b.getType().equals(Material.POTATOES)
        ) {
            Player p = e.getPlayer();
            try {
                if (JobManager.getJob(p.getName()).equals(Job.FARMER)) {
                    Bank bank = BankCache.get(p.getName());
                    bank.add(0.125f);
                }
            } catch (JobException ignored) {
            }
        }
    }

    @EventHandler
    public void onFarm(PlayerInteractEvent e){
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            Block b = e.getClickedBlock();
            if (b.getType().equals(Material.WHEAT) ||
                    b.getType().equals(Material.BEETROOTS) ||
                    b.getType().equals(Material.MELON) ||
                    b.getType().equals(Material.PUMPKIN) ||
                    b.getType().equals(Material.CARROTS) ||
                    b.getType().equals(Material.POTATOES)
            ) {
                Player p = e.getPlayer();
                try {
                    if ((p.getInventory().getItemInOffHand().getType().equals(Material.BONE_MEAL) || p.getInventory().getItemInMainHand().getType().equals(Material.BONE_MEAL))
                            && JobManager.getJob(p.getName()).equals(Job.FARMER)) {
                        Bank bank = BankCache.get(p.getName());
                        bank.add(0.1f);
                    }
                } catch (JobException ignored) {
                }
            }
        }
    }

    @EventHandler
    public void onLumber(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType().equals(Material.JUNGLE_LOG) ||
                b.getType().equals(Material.ACACIA_LOG) ||
                b.getType().equals(Material.BIRCH_LOG) ||
                b.getType().equals(Material.DARK_OAK_LOG) ||
                b.getType().equals(Material.OAK_LOG) ||
                b.getType().equals(Material.SPRUCE_LOG)
        ) {
            Player p = e.getPlayer();
            try {
                if (JobManager.getJob(p.getName()).equals(Job.LUMBER)) {
                    Bank bank = BankCache.get(p.getName());
                    bank.add(0.25f);
                }
            } catch (JobException ignored) {
            }
        }
    }

    @EventHandler
    public void onLumber(BlockPlaceEvent e) {
        Block b = e.getBlockPlaced();
        if (b.getType().equals(Material.JUNGLE_SAPLING) ||
                b.getType().equals(Material.ACACIA_SAPLING) ||
                b.getType().equals(Material.BIRCH_SAPLING) ||
                b.getType().equals(Material.DARK_OAK_SAPLING) ||
                b.getType().equals(Material.OAK_SAPLING) ||
                b.getType().equals(Material.SPRUCE_SAPLING)
        ) {
            Player p = e.getPlayer();
            try {
                if (JobManager.getJob(p.getName()).equals(Job.LUMBER)) {
                    Bank bank = BankCache.get(p.getName());
                    bank.add(0.05f);
                }
            } catch (JobException ignored) {
            }
        }
    }

    @EventHandler
    public void onFishing(PlayerFishEvent e) {
        Player p = e.getPlayer();
        if(e.getCaught() != null) {
            if(e.getCaught() instanceof CraftItem) {
                try {
                    if (JobManager.getJob(p.getName()).equals(Job.FISHER)) {
                        Bank bank = BankCache.get(p.getName());
                        bank.add(0.25f);
                    }
                } catch (JobException ignored) {
                }
            }
        }
    }

    @EventHandler
    public void onMine(BlockBreakEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();
        try {
            if(JobManager.getJob(p.getName()).equals(Job.MINER)){
                Bank bank = BankCache.get(p.getName());
                switch(b.getType()){
                    case OBSIDIAN:
                        bank.add(0.5f);
                        break;
                    case END_STONE:
                        bank.add(0.1f);
                        break;
                    case COBBLESTONE:
                    case STONE:
                    case STONE_BRICKS:
                    case NETHER_QUARTZ_ORE:
                        bank.add(0.05f);
                        break;
                    case NETHERRACK:
                        bank.add(0.001f);
                        break;
                }
            }
        } catch (JobException ignored) {
        }
    }

}
