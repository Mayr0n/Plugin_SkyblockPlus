package fr.sky.commands;

import fr.sky.claims.ClaimsManager;
import fr.sky.homes.homeManager;
import fr.sky.logsCenter.logsMain;
import fr.sky.tpPack.tpEtCooldowns;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class cmdManager implements CommandExecutor {
    private tpEtCooldowns tpc;
    private homeManager hm;
    private JavaPlugin plugin;
    private commands commands = new commands();

    public cmdManager(JavaPlugin plugin, tpEtCooldowns tpc){
        this.plugin = plugin;
        this.tpc = tpc;
        hm = new homeManager(plugin);
    }

    public List<String> getCommands(){
        return Arrays.asList(
                "pvp", "sethome", "delhome", "home", "spawn", "invsee", "tpa",
                "rc", "lt", "skick", "sban", "claim", "stuff");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String command = cmd.getName();
            List<String> cmds = getCommands();

            if (command.equals(cmds.get(0))) {
                commands.switchPvp(p, args);
            }
            else if(command.equals(cmds.get(1))){
                hm.sethome(p, args);
            }
            else if(command.equals(cmds.get(2))){
                hm.delhome(p, args);
            }
            else if(command.equals(cmds.get(3))){
                hm.tpHome(p, args);
            }
            else if(command.equals(cmds.get(4))){
                p.teleport(new Location(Bukkit.getWorld("world"), 0, 65, 0));
            }
            else if(command.equals(cmds.get(5)) && p.getName().equals("Imperayser")){
                commands.invsee(p, args);
            } else if(command.equals(cmds.get(6))){
                commands.tpaProcess(p, args, tpc);
            } else if(command.equals(cmds.get(7)) && p.isOp()){
                commands.resetCooldowns(p, tpc);
            } else if(command.equals(cmds.get(8)) && p.isOp()){
                p.getInventory().addItem(logsMain.getLookTool());
            } else if(command.equalsIgnoreCase(cmds.get(8))){
                commands.punish(p, args, "ban");
            } else if(command.equalsIgnoreCase(cmds.get(9))){
                commands.punish(p, args, "kick");
            } else if(command.equalsIgnoreCase(cmds.get(11))){
                ClaimsManager cm = new ClaimsManager(p);
                switch(args[0]){
                    case "add":
                        if(cm.add(p.getLocation())){
                            p.sendMessage(ChatColor.AQUA + "Le chunk a bien été claim !");
                        } else {
                            p.sendMessage(ChatColor.RED + "Le chunk n'a pu être claim. Vérifies si ce chunk n'appartient pas déjà à quelqu'un, ou alors si tu as dépassé ta limite de 5 chunks claims.");
                        }
                        break;
                    case "remove":
                        if(cm.remove(p.getLocation())){
                            p.sendMessage(ChatColor.AQUA + "Le chunk n'est plus claim !");
                        } else {
                            p.sendMessage(ChatColor.RED + "Le chunk n'a pu être unclaim. Es-tu sûr.e que ce chunk t'appartient ?");
                        }
                        break;
                    case "list":
                        cm.list();
                        break;
                    default: p.sendMessage(ChatColor.RED + "Erreur syntaxe ! Syntaxe : " + ChatColor.DARK_GREEN + "/claim <add:remove:list>");
                }
            } else if(command.equalsIgnoreCase(cmds.get(12)) && p.isOp()){
                giveStuff(p);
            }
        }
        return false;
    }

    public static void giveStuff(Player p){
        p.getInventory().clear();

        ItemStack[] armor = {
                getUnbreakable(new ItemStack(Material.IRON_BOOTS)), getUnbreakable(new ItemStack(Material.IRON_LEGGINGS)),
                getUnbreakable(new ItemStack(Material.DIAMOND_CHESTPLATE)), getUnbreakable(new ItemStack(Material.IRON_HELMET))
        };

        ItemStack bow = getUnbreakable(new ItemStack(Material.BOW));
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        ItemStack[] bonusStuff = {
                bow, getUnbreakable(new ItemStack(Material.DIAMOND_AXE)),
                new ItemStack(Material.LAVA_BUCKET),
                new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.GOLDEN_APPLE, 5),
                new ItemStack(Material.COOKED_BEEF, 64), new ItemStack(Material.ARROW)
        };

        p.getInventory().setArmorContents(armor);
        p.getInventory().setItemInMainHand(getUnbreakable(new ItemStack(Material.DIAMOND_SWORD)));
        p.getInventory().setItemInOffHand(getUnbreakable(new ItemStack(Material.SHIELD)));
        for (ItemStack item : bonusStuff) {
            p.getInventory().addItem(item);
        }
        for (int i = 0; i <= 6; i++) {
            p.getInventory().addItem(new ItemStack(Material.OAK_LEAVES, 64));
        }
        p.getInventory().addItem(new ItemStack(Material.TNT, 32));
        p.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
        p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 4));
        p.setGameMode(GameMode.SURVIVAL);
    }

    public static ItemStack getUnbreakable(ItemStack item){
        ItemMeta m = item.getItemMeta();
        m.setUnbreakable(true);
        item.setItemMeta(m);
        return item;
    }
}
