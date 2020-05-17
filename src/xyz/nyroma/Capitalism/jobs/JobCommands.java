package xyz.nyroma.Capitalism.jobs;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class JobCommands implements CommandExecutor {

    public static List<String> getCommands(){
        return Arrays.asList("job");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comm, String arg, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            String cmd = comm.getName();
            if(cmd.equals(getCommands().get(0))){
                try {
                    switch(args[0]){
                        case "HUNTER":
                            JobManager.setJob(p.getName(), Job.HUNTER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            break;
                        case "FARMER":
                            JobManager.setJob(p.getName(), Job.FARMER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            break;
                        case "TRADER":
                            JobManager.setJob(p.getName(), Job.TRADER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            break;
                        case "LUMBER":
                            JobManager.setJob(p.getName(), Job.LUMBER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            break;
                        case "FISHER":
                            JobManager.setJob(p.getName(), Job.FISHER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            break;
                        case "MINER":
                            JobManager.setJob(p.getName(), Job.MINER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            break;
                        case "get":
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            break;
                        case "getall":
                            for(Player play : p.getServer().getOnlinePlayers()){
                                p.sendMessage(ChatColor.GREEN + play.getName() + " est " + JobManager.getJob(play.getName()).toString());
                            }
                            break;
                        default:
                            p.sendMessage(ChatColor.RED + "Argument invalide ! Syntaxe : /job <HUNTER:FARMER:TRADER:LUMBER:FISHER:get:getall>");
                            break;
                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    p.sendMessage(ChatColor.RED + "Argument invalide ! Syntaxe : /job <HUNTER:FARMER:TRADER:LUMBER:FISHER:get:getall>");
                } catch(JobException e){
                    p.sendMessage(ChatColor.GREEN + "Vous n'avez pas de métier.");
                }
            }
        }
        return false;
    }
}
