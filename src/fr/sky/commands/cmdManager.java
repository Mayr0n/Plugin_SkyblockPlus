package fr.sky.commands;

import fr.sky.claims.ClaimsManager;
import fr.sky.homes.homeManager;
import fr.sky.logsCenter.logsMain;
import fr.sky.tpPack.tpEtCooldowns;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
                "rc", "lt", "skick", "sban", "claim", "spawnmob", "god");
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
                p.teleport(new Location(Bukkit.getWorld("world"), 7, 21, 11));
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
                for(int i = 0; i < Integer.parseInt(args[0]) ; i++){
                    p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                }
            } else if(command.equalsIgnoreCase(cmds.get(13)) && p.isOp()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 50));
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 5));
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 50));
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 50));

                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SPAWN, 10, 10);
                Bukkit.broadcastMessage(ChatColor.DARK_GREEN +  p.getName() + " became a" + ChatColor.BLACK + "god");
            }
        }
        return false;
    }
}
