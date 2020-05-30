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
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).get().toString());
                            break;
                        case "FARMER":
                            JobManager.setJob(p.getName(), Job.FARMER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).get().toString());
                            break;
                        case "TRADER":
                            JobManager.setJob(p.getName(), Job.TRADER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).get().toString());
                            break;
                        case "LUMBER":
                            JobManager.setJob(p.getName(), Job.LUMBER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).get().toString());
                            break;
                        case "FISHER":
                            JobManager.setJob(p.getName(), Job.FISHER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).get().toString());
                            break;
                        case "MINER":
                            JobManager.setJob(p.getName(), Job.MINER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).get().toString());
                            break;
                        case "ENCHANTER":
                            JobManager.setJob(p.getName(), Job.ENCHANTER);
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).get().toString());
                            break;
                        case "get":
                            if(JobManager.getJob(p.getName()).isPresent()){
                                p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            } else {
                                p.sendMessage(ChatColor.RED + "Vous n'avez pas de métier.");
                            }
                            break;
                        case "getall":
                            for(Player play : p.getServer().getOnlinePlayers()){
                                if(JobManager.getJob(play.getName()).isPresent()){
                                    p.sendMessage(ChatColor.GREEN + play.getName() + " est " + JobManager.getJob(play.getName()).get().toString());
                                } else {
                                    p.sendMessage(ChatColor.GREEN + play.getName() + " n'a pas de métier.");
                                }
                            }
                            break;
                        default:
                            p.sendMessage(ChatColor.GREEN + "Vous êtes " + JobManager.getJob(p.getName()).toString());
                            break;
                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    p.sendMessage(ChatColor.RED + "Argument invalide ! Syntaxe : /job <HUNTER:FARMER:TRADER:LUMBER:FISHER:ENCHANTER:get:getall>");
                }
            }
        }
        return false;
    }
}
