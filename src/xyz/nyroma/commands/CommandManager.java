package xyz.nyroma.commands;

import xyz.nyroma.homes.HomeManager;
import xyz.nyroma.logsCenter.logsMain;
import xyz.nyroma.main.BotlinkManager;
import xyz.nyroma.tpPack.tpEtCooldowns;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor {
    private tpEtCooldowns tpc;
    private HomeManager hm;
    private commands commands = new commands();

    public CommandManager(tpEtCooldowns tpc){
        this.tpc = tpc;
        hm = new HomeManager();
    }

    public List<String> getCommands(){
        return Arrays.asList(
                "pvp", "spawn", "invsee", "tpa",
                "rc", "lt", "skick", "sban", "stuff", "sendisc");
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
                Location loc = new Location(Bukkit.getWorld("world"), 0, 65, 0);
                if(!loc.getChunk().isLoaded()){
                    loc.getChunk().load();
                }
                p.teleport(loc);
            }
            else if(command.equals(cmds.get(2)) && p.getName().equals("Imperayser")){
                commands.invsee(p, args);
            } else if(command.equals(cmds.get(3))){
                commands.tpaProcess(p, args, tpc);
            } else if(command.equals(cmds.get(4)) && p.isOp()){
                commands.resetCooldowns(p, tpc);
            } else if(command.equals(cmds.get(5)) && p.isOp()){
                p.getInventory().addItem(logsMain.getLookTool());
            } else if(command.equalsIgnoreCase(cmds.get(6))){
                commands.punish(p, args, "ban");
            } else if(command.equalsIgnoreCase(cmds.get(7))){
                commands.punish(p, args, "kick");
            } else if(command.equalsIgnoreCase(cmds.get(8)) && p.isOp()){
                giveStuff(p);
            } else if(command.equalsIgnoreCase(cmds.get(9))){
                StringBuilder sb = new StringBuilder();
                for(String s : args){
                    sb.append(s).append(" ");
                }
                if(BotlinkManager.isActivated) {
                    new BotlinkManager().sendMess(sb.toString());
                } else {
                    p.sendMessage(ChatColor.RED + "La connexion entre discord et minecraft n'est pas établie.");
                }
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
