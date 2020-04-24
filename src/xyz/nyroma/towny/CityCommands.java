package xyz.nyroma.towny;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.nyroma.main.speedy;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Supplier;

public class CityCommands implements CommandExecutor {

    private List<String> commands = Arrays.asList("city", "ct");
    private CityManager cm = new CityManager();

    public List<String> getCommands() {
        return commands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            String cmd = command.getName();

            if(cmd.equals(commands.get(0))){
                if(args.length > 0){
                    switch(args[0]){
                        case "create":
                            try {
                                String name = args[1];
                                cm.create(name, "DiscUniverse", p);
                            } catch (ArrayIndexOutOfBoundsException e){
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city create <nom>");
                            }
                            break;
                        case "add":
                            try {
                                String name = args[1];
                                Player toAdd = speedy.getPlayerByName(p.getServer(), name);
                                if(toAdd != null){
                                    try {
                                        if(isMemberAndOwner(p)){
                                            City city = cm.getOwnersCity(p);
                                            if(city.sendInvit(p, toAdd)){
                                                p.sendMessage(ChatColor.GREEN + "Une invitation lui a été envoyée !");
                                            } else {
                                                p.sendMessage(ChatColor.DARK_RED + "Une erreur est survenue. Contacte Imperayser en citant \"CityCommands:51\"");
                                            }
                                        }
                                    } catch (NotExistException e) {
                                        p.sendMessage(ChatColor.RED + "Il n'existe aucune ville sur le serveur...");
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Ce joueur n'est pas connecté...");
                                }
                            } catch(ArrayIndexOutOfBoundsException e){
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city add <pseudo>");
                            }
                            break;
                        case "claim":
                            try {
                                String action = args[1];
                                switch(action){
                                    case "add":
                                        try {
                                            if(isMemberAndOwner(p)){
                                                try {
                                                    City city = cm.getOwnersCity(p);
                                                    if (city.getClaimsManager().addClaim(p.getLocation())) {
                                                        p.sendMessage(ChatColor.GREEN + "Le chunk a bien été claim !");
                                                    } else {
                                                        p.sendMessage(ChatColor.DARK_RED + "Une erreur est survenue ! As-tu bien exactement ou moins de 5 claims ? Ton chunk n'est-il pas déjà claim ?");
                                                    }
                                                } catch (NotExistException e) {
                                                    p.sendMessage(ChatColor.RED + "Tu n'appartiens à aucune ville !");
                                                }
                                            }
                                        } catch (NotExistException e) {
                                            p.sendMessage(ChatColor.RED + "Il n'existe aucune ville sur le serveur...");
                                        }
                                        break;
                                    case "remove":
                                        try {
                                            if(isMemberAndOwner(p)){
                                                City city = cm.getOwnersCity(p);
                                                if (city.getClaimsManager().removeClaim(p.getLocation())) {
                                                    p.sendMessage(ChatColor.GREEN + "Le chunk a bien été unclaim !");
                                                } else {
                                                    p.sendMessage(ChatColor.DARK_RED + "Une erreur est survenue ! Est-ce que ce chunk t'appartient ? Ton chunk est-il déjà claim ?");
                                                }
                                            }
                                        } catch (NotExistException e) {
                                            p.sendMessage(ChatColor.RED + "Il n'existe aucune ville sur le serveur...");
                                        }
                                        break;
                                    case "list":
                                        try {
                                            if(isMemberAndOwner(p)){
                                                City city = cm.getOwnersCity(p);
                                                p.sendMessage(ChatColor.DARK_GREEN + "--------- Votre ville a claim ces chunks : ---------");
                                                for(Integer X : city.getClaimsManager().getClaims().keySet()){
                                                    p.sendMessage(ChatColor.GREEN + "x = " + X + ", Z = " + city.getClaimsManager().getClaims().get(X));
                                                }
                                                p.sendMessage(ChatColor.DARK_GREEN + "---------------------------------------------------");
                                            }
                                        } catch (NotExistException e) {
                                            p.sendMessage(ChatColor.RED + "Il n'existe aucune ville sur le serveur...");
                                        }
                                        break;
                                    default:
                                        p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city claim <add:remove:list>");
                                        break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e){
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city claim <add:remove>");
                            }
                            break;
                        case "credit":
                            p.sendMessage(ChatColor.AQUA + "Encore en [WIP]");
                            break;
                        case "debit":
                            p.sendMessage(ChatColor.AQUA + "Encore en [WIP]");
                            break;
                        case "owner":
                            try {
                                String name = args[1];
                                Player newOwner = speedy.getPlayerByName(p.getServer(), name);
                                if (newOwner != null) {
                                    try {
                                        if (isMemberAndOwner(p)) {
                                            City city = cm.getOwnersCity(p);
                                            if (city.getMembersManager().isMember(newOwner.getName())) {
                                                city.changeOwner(newOwner);
                                                p.sendMessage(ChatColor.GREEN + "Le changement de propriété a été réalisé !");
                                            } else {
                                                p.sendMessage(ChatColor.RED + "Ce joueur n'est même pas dans ta ville !");
                                            }
                                        }
                                    } catch (NotExistException e) {
                                        p.sendMessage(ChatColor.RED + "Il n'existe aucune ville sur le serveur...");
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Ce joueur n'est pas connecté...");
                                }
                            } catch(ArrayIndexOutOfBoundsException e){
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city owner <pseudo>");
                            }
                            break;
                        case "list":
                            p.sendMessage(ChatColor.DARK_GREEN + "- Voici toutes les villes existantes et leurs owners ! -");
                            try {
                                for (City city : cm.getCities().keySet()) {
                                    p.sendMessage(ChatColor.GOLD + city.getName() + ", gérée par " + city.getOwner());
                                }
                                p.sendMessage(ChatColor.DARK_GREEN + "---------------------------------------------");
                            } catch(NotExistException e){
                                p.sendMessage(ChatColor.RED + "Il n'existe aucune ville sur le serveur...");
                            }
                            break;
                        case "remove":
                            try {
                                City city = cm.getOwnersCity(p);
                                p.sendMessage(ChatColor.GREEN + "Votre ville " + city.getName() + " a été supprimée.");
                            } catch(NotExistException e){
                                p.sendMessage(ChatColor.RED + "Vous n'appartenez à aucune ville !");
                            }
                            break;
                        case "info":
                            try {
                                String name = args[1];
                                City c = cm.getCity(name);
                                p.sendMessage(ChatColor.DARK_GREEN + "-------- " + c.getName() + " --------");
                                p.sendMessage(ChatColor.GREEN + "Owner : " + c.getOwner());
                                p.sendMessage(ChatColor.GREEN + "Réserve : " + c.getMoneyManager().getAmount() + "$");
                                p.sendMessage(ChatColor.GREEN + "Royaume : " + c.getRoyaume());
                                p.sendMessage(ChatColor.GREEN + "Membres : ");
                                for(String s : c.getMembersManager().getMembers()){
                                    p.sendMessage(ChatColor.GREEN + "- " + s);
                                }
                                p.sendMessage(ChatColor.GREEN + "Claims : ");
                                for(int x : c.getClaimsManager().getClaims().keySet()){
                                    p.sendMessage(ChatColor.GREEN + "-> " + x + " " + c.getClaimsManager().getClaims().get(x));
                                }
                                System.out.println(c.getClaimsManager().getClaims().toString());
                                p.sendMessage(ChatColor.DARK_GREEN + "-----------------------");
                            } catch(NullPointerException | ArrayIndexOutOfBoundsException e){
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city info <nom>");
                            } catch (NotExistException e){
                                p.sendMessage(ChatColor.RED + "Cette ville n'existe pas !");
                            }
                            break;
                        default:
                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city <create:add:claim:credit:debit:owner>");
                            break;
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city <create:add:credit:debit:owner>");
                }
            }
        }
        return false;
    }

    private boolean isMemberAndOwner(Player p) throws NotExistException {
        if (cm.isMemberOfACity(p)) {
            if (cm.isAOwner(p)) {
                return true;
            } else {
                p.sendMessage(ChatColor.RED + "Tu n'est pas le propriétaire de ta ville !");
                return false;
            }
        } else {
            p.sendMessage(ChatColor.RED + "Tu n'appartiens à aucune ville...");
            return false;
        }
    }

}
