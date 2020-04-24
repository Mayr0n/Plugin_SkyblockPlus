package xyz.nyroma.commands;

import xyz.nyroma.tpPack.tpEtCooldowns;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;

import static xyz.nyroma.main.speedy.sendErrorMessage;

public class commands {

    public boolean invsee(Player p, String[] args){
        if(args[0] != null){
            Player pl = null;
            for(Player y : Bukkit.getServer().getOnlinePlayers()){
                if(args[0].equals(y.getName())){
                    pl = y;
                }
            }
            if(pl == null){
                sendErrorMessage(p, "Il n'y a aucun joueur avec ce nom !");
                return false;
            } else {
                p.openInventory(pl.getInventory());
                return true;
            }
        } else {
            sendErrorMessage(p,"Il faut spécifier un nom ! Syntaxe : /invsee <nomJoueur>");
            return false;
        }
    }
    public boolean tpaProcess(Player p, String[] args, tpEtCooldowns tpc){
        if(args[0] == null){
            sendErrorMessage(p, "A qui veut-tu te téléporter ? Syntaxe : /tpa <nomJoueur>");
            return false;
        } else {
            Player d = null;
            for(Player play : p.getServer().getOnlinePlayers()){
                if(play.getName().equals(args[0])){
                    d = play;
                }
            }
            if(d == null){
                sendErrorMessage(p, "Vous devez être dans le même serveur, et tous les deux connectés !");
                return false;
            } else {
                final Player df = d;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        tpc.tpa(df,p);
                    }
                }.run();
                System.out.println("Tpa lancé");
                return true;
            }
        }
    }
    public boolean resetCooldowns(Player p, tpEtCooldowns tpc){
        ArrayList<Player> players = new ArrayList<>();
        Iterator it = p.getServer().getOnlinePlayers().iterator();
        while(it.hasNext()){
            players.add((Player) it.next());
        }
        tpc.resetAll(players);
        p.sendMessage(ChatColor.RED + "Les cooldowns ont été resets.");
        return true;
    }
    public boolean punish(Player p, String[] args, String type){
        if(args[0] != null && !args[0].equals("Imperayser") && isStaff(p)){
            for(Player play : p.getServer().getOnlinePlayers()){
                if(play.getName().equals(args[0])){
                    switch (type){
                        case "ban":
                            Bukkit.banIP(play.getAddress().toString());
                            break;
                        case "kick":
                            p.kickPlayer(getArgs(args));
                            break;
                    }
                    Bukkit.broadcastMessage(ChatColor.RED + play.getName() + "a été " + type + " par " + p.getName() + ", Raison : " + getArgs(args));
                    return true;
                }
            }
            return true;
        } else {
            p.sendMessage(ChatColor.RED + "Il faut préciser le nom d'un joueur !");
            return false;
        }
    }
    public boolean switchPvp(Player p, String[] args){
        if (!isStaff(p)) {
            sendErrorMessage(p, "Seul le staff a accès à cette commande !");
            return false;
        } else {
            switch (args[0]) {
                case "on":
                    p.getWorld().setPVP(true);
                    Bukkit.broadcastMessage(ChatColor.RED + "Le pvp a été activé sur le serveur " + p.getWorld().getName());
                    return true;
                case "off":
                    p.getWorld().setPVP(false);
                    Bukkit.broadcastMessage(ChatColor.RED + "Le pvp a été désactivé sur le serveur " + p.getWorld().getName());
                    return true;
                default:
                    sendErrorMessage(p, "Erreur ! Syntaxe : /pvp <on:off>");
                    return false;
            }
        }
    }


    private boolean isStaff(Player p){
        return p.getName().equals("Marsou_") || p.getName().equals("Attiyas") || p.getName().equals("Ampres") || p.getName().equals("Imperayser");
    }
    private String getArgs(String[] args){
        StringBuilder argString = new StringBuilder(" ");
        if(args.length > 1){
            for(int i = 1 ; i < args.length ; i++){
                argString.append(args[i]).append(" ");
            }
        }
        return argString.toString();
    }

}
