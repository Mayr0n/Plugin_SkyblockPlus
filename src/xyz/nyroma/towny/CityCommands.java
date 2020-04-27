package xyz.nyroma.towny;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.nyroma.main.speedy;

import java.util.Arrays;
import java.util.List;

public class CityCommands implements CommandExecutor {

    private List<String> commands = Arrays.asList("city", "ct", "scity");
    private CityManager cm = new CityManager();

    public List<String> getCommands() {
        return commands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String cmd = command.getName();

            if (cmd.equals(commands.get(0))) {
                if (args.length > 0) {
                    switch (args[0]) {
                        case "create":
                            try {
                                String name = args[1];
                                if (name.length() <= 20) {
                                    try {
                                        new City(name, "DiscUniverse", p.getName());
                                        p.sendMessage(ChatColor.GREEN + "Votre ville " + name + " a été créée !");
                                        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 100, 1);
                                    } catch (TownyException e) {
                                        p.sendMessage(ChatColor.RED + e.getMessage());
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Le nom doit faire moins de 20 caractères.");
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city create <nom>");
                            }
                            break;
                        case "members":
                            try {
                                switch (args[1]) {
                                    case "add":
                                        String name = args[2];
                                        Player toAdd = speedy.getPlayerByName(p.getServer(), name);
                                        if (toAdd != null) {
                                            try {
                                                if (isMemberAndOwner(p)) {
                                                    City city = cm.getOwnersCity(p);
                                                    if (city.sendInvit(p, toAdd)) {
                                                        p.sendMessage(ChatColor.GREEN + "Une invitation lui a été envoyée !");
                                                    } else {
                                                        p.sendMessage(ChatColor.DARK_RED + "Une erreur est survenue. Contacte Imperayser en citant \"CityCommands:51\"");
                                                    }
                                                }
                                            } catch (TownyException e) {
                                                p.sendMessage(ChatColor.RED + e.getMessage());
                                            }
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Ce joueur n'est pas connecté...");
                                        }
                                        break;
                                    case "remove":
                                        String n = args[2];
                                        try {
                                            if (isMemberAndOwner(p)) {
                                                City city = cm.getOwnersCity(p);
                                                if (city.getMembersManager().removeMember(n)) {
                                                    p.sendMessage(ChatColor.GREEN + n + " a été renvoyé de la ville");
                                                } else {
                                                    p.sendMessage(ChatColor.DARK_RED + "Le joueur n'a pas pu être retiré.");
                                                }
                                            }
                                        } catch (TownyException e) {
                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                        }
                                        break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city add <pseudo>");
                            }
                            break;
                        case "claim":
                            try {
                                String action = args[1];
                                switch (action) {
                                    case "add":
                                        if (isMemberAndOwner(p)) {
                                            try {
                                                City city = cm.getOwnersCity(p);
                                                if (city.getClaimsManager().addClaim(p.getLocation())) {
                                                    p.sendMessage(ChatColor.GREEN + "Le chunk a bien été claim !");
                                                } else {
                                                    p.sendMessage(ChatColor.DARK_RED + "Une erreur est survenue ! As-tu bien exactement ou moins de 5 claims ? Ton chunk n'est-il pas déjà claim ?");
                                                }
                                            } catch (TownyException e) {
                                                p.sendMessage(ChatColor.RED + e.getMessage());
                                            }
                                        }
                                        break;
                                    case "remove":
                                        try {
                                            if (isMemberAndOwner(p)) {
                                                City city = cm.getOwnersCity(p);
                                                if (city.getClaimsManager().removeClaim(p.getLocation())) {
                                                    p.sendMessage(ChatColor.GREEN + "Le chunk a bien été unclaim !");
                                                } else {
                                                    p.sendMessage(ChatColor.DARK_RED + "Une erreur est survenue ! Est-ce que ce chunk t'appartient ? Ton chunk est-il déjà claim ?");
                                                }
                                            }
                                        } catch (TownyException e) {
                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                        }
                                        break;
                                    case "list":
                                        try {
                                            if (isMemberAndOwner(p)) {
                                                City city = cm.getOwnersCity(p);
                                                p.sendMessage(ChatColor.DARK_GREEN + "--------- Votre ville a claim ces chunks : ---------");
                                                for (Integer X : city.getClaimsManager().getClaims().keySet()) {
                                                    p.sendMessage(ChatColor.GREEN + "x = " + X + ", Z = " + city.getClaimsManager().getClaims().get(X));
                                                }
                                                p.sendMessage(ChatColor.DARK_GREEN + "---------------------------------------------------");
                                            }
                                        } catch (TownyException e) {
                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                        }
                                        break;
                                    default:
                                        p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city claim <add:remove:list>");
                                        break;
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city claim <add:remove>");
                            }
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
                                    } catch (TownyException e) {
                                        p.sendMessage(ChatColor.RED + e.getMessage());
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Ce joueur n'est pas connecté...");
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city owner <pseudo>");
                            }
                            break;
                        case "list":
                            p.sendMessage(ChatColor.DARK_GREEN + "- Voici toutes les villes existantes et leurs owners ! -");
                            for (City city : CitiesCache.getCities()) {
                                p.sendMessage(ChatColor.GOLD + city.getName() + ", gérée par " + city.getOwner());
                            }
                            p.sendMessage(ChatColor.DARK_GREEN + "---------------------------------------------");
                            break;
                        case "remove":
                            try {
                                City city = cm.getOwnersCity(p);
                                if (CitiesCache.remove(city)) {
                                    p.sendMessage(ChatColor.GREEN + "Votre ville " + city.getName() + " a été supprimée.");
                                } else {
                                    p.sendMessage(ChatColor.RED + "Votre ville " + city.getName() + " n'a pas pu être supprimée.");
                                }
                            } catch (TownyException e) {
                                p.sendMessage(ChatColor.RED + e.getMessage());
                            }
                            break;
                        case "info":
                            try {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 1; i < args.length; i++) {
                                    sb.append(args[i]).append(" ");
                                }
                                String s = sb.toString();
                                int l = s.length() - 1;
                                String name = s.substring(0, l);
                                City c = CitiesCache.get(name);
                                p.sendMessage(ChatColor.DARK_GREEN + "-------- " + c.getName() + " --------");
                                p.sendMessage(ChatColor.GREEN + "Owner : " + c.getOwner());
                                p.sendMessage(ChatColor.GREEN + "ID : " + c.getID());
                                p.sendMessage(ChatColor.GREEN + "Réserve : " + c.getMoneyManager().getAmount() + " Nyr");
                                p.sendMessage(ChatColor.GREEN + "Royaume : " + c.getRoyaume());
                                p.sendMessage(ChatColor.GREEN + "Membres : ");
                                for (String st : c.getMembersManager().getMembers()) {
                                    p.sendMessage(ChatColor.GREEN + "- " + st);
                                }
                                p.sendMessage(ChatColor.GREEN + "Alliés : ");
                                if(c.getRelationsManager().getNice()){
                                    p.sendMessage(ChatColor.GREEN + "- Tout le monde");
                                } else {
                                    for (City city : c.getRelationsManager().getAllies()) {
                                        p.sendMessage(ChatColor.GREEN + "- " + city.getName());
                                    }
                                }
                                p.sendMessage(ChatColor.GREEN + "Ennemis : ");
                                if(c.getRelationsManager().getEvil()){
                                    p.sendMessage(ChatColor.GREEN + "- Tout le monde");
                                } else {
                                    for (City city : c.getRelationsManager().getEnemies()) {
                                        p.sendMessage(ChatColor.GREEN + "- " + city.getName());
                                    }
                                }
                                p.sendMessage(ChatColor.GREEN + "Claims : ");
                                for (int x : c.getClaimsManager().getClaims().keySet()) {
                                    p.sendMessage(ChatColor.GREEN + "-> " + x + " " + c.getClaimsManager().getClaims().get(x));
                                }
                                System.out.println(c.getClaimsManager().getClaims().toString());
                                p.sendMessage(ChatColor.DARK_GREEN + "-----------------------");
                            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city info <nom>");
                            } catch (TownyException e) {
                                p.sendMessage(ChatColor.RED + e.getMessage());
                            }
                            break;
                        case "rename":
                            if (args.length > 1) {
                                try {
                                    City city = cm.getCityOfMember(p);
                                    if (city.getOwner().equals(p.getName())) {
                                        StringBuilder sb = new StringBuilder();
                                        for (int i = 1; i < args.length; i++) {
                                            sb.append(args[i]).append(" ");
                                        }
                                        String s = sb.toString();
                                        int l = s.length() - 1;
                                        String name = s.substring(0, l);
                                        if (!CitiesCache.contains(name)) {
                                            if (name.length() <= 20) {
                                                city.rename(name);
                                                p.sendMessage(ChatColor.GREEN + "Votre ville a été renommée.");
                                            } else {
                                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Le nom doit faire moins de 20 caractères.");
                                            }
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Une ville a déjà ce nom.");
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "Tu n'es pas l'owner de cette ville.");
                                    }
                                } catch (TownyException e) {
                                    p.sendMessage(ChatColor.RED + e.getMessage());
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "Il faut un autre nom de ville !");
                            }
                            break;
                        case "ally":
                            if(args.length == 3){
                                switch(args[1]){
                                    case "add":
                                        try {
                                            City city = cm.getCityOfMember(p);
                                            if(args[2].equals("all") && CitiesCache.contains("all")){
                                                if (city.getOwner().equals(p.getName())) {
                                                    city.getRelationsManager().setNice(true);
                                                    p.sendMessage(ChatColor.GREEN + "Votre ville est alliée avec tout le monde !");
                                                } else {
                                                    p.sendMessage(ChatColor.RED + "Tu n'est pas l'owner de ta ville !");
                                                }
                                            } else {
                                                City ally = CitiesCache.get(args[2]);
                                                if (city.getOwner().equals(p.getName())) {
                                                    city.getRelationsManager().setNice(false);
                                                    if (city.getRelationsManager().addAlly(ally)) {
                                                        p.sendMessage(ChatColor.GREEN + ally.getName() + " est devenu alliée de votre ville !");
                                                    } else {
                                                        p.sendMessage(ChatColor.RED + "Une erreur est survenue...");
                                                    }
                                                } else {
                                                    p.sendMessage(ChatColor.RED + "Tu n'est pas l'owner de ta ville !");
                                                }
                                            }
                                        } catch (TownyException e) {
                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                        }
                                        break;
                                    case "remove":
                                        try {
                                            City city = cm.getCityOfMember(p);
                                            City ally = CitiesCache.get(args[2]);
                                            if(city.getOwner().equals(p.getName())){
                                                if(city.getRelationsManager().removeAlly(ally)){
                                                    p.sendMessage(ChatColor.GREEN +  ally.getName() + " n'est plus alliée avec votre ville !");
                                                } else {
                                                    p.sendMessage(ChatColor.RED +  "Cette ville n'est pas alliée !");
                                                }
                                            } else {
                                                p.sendMessage(ChatColor.RED + "Tu n'est pas l'owner de ta ville !");
                                            }
                                        } catch (TownyException e) {
                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                        }
                                        break;
                                    default:
                                        p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city ally <add:remove:list> <nom>");
                                        break;
                                }
                            } else if(args.length == 2) {
                                if(args[1].equals("list")){
                                    try {
                                        City city = cm.getCityOfMember(p);
                                        p.sendMessage(ChatColor.GREEN + "Voici les alliés de votre ville : ");
                                        for(City c : city.getRelationsManager().getAllies()){
                                            p.sendMessage(ChatColor.GREEN + "-> " + c.getName());
                                        }
                                    } catch (TownyException e) {
                                        p.sendMessage(ChatColor.RED + e.getMessage());
                                    }
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city ally <add:remove:list> <nom>");
                            }
                            break;
                        case "enemy":
                            if(args.length == 3){
                                switch(args[1]){
                                    case "add":
                                        try {
                                            City city = cm.getCityOfMember(p);
                                            if(args[2].equals("all")){
                                                if (city.getOwner().equals(p.getName())) {
                                                    city.getRelationsManager().setEvil(true);
                                                    p.sendMessage(ChatColor.GREEN + "Votre ville est ennemie avec tout le monde !");
                                                } else {
                                                    p.sendMessage(ChatColor.RED + "Tu n'est pas l'owner de ta ville !");
                                                }
                                            } else {
                                                City enemy = CitiesCache.get(args[2]);
                                                if (city.getOwner().equals(p.getName())) {
                                                    city.getRelationsManager().setEvil(false);
                                                    if (city.getRelationsManager().addEnemy(enemy)) {
                                                        p.sendMessage(ChatColor.GREEN + enemy.getName() + " est devenu ennemie de votre ville !");
                                                    } else {
                                                        p.sendMessage(ChatColor.RED + "Une erreur est survenue...");
                                                    }
                                                } else {
                                                    p.sendMessage(ChatColor.RED + "Tu n'est pas l'owner de ta ville !");
                                                }
                                            }
                                        } catch (TownyException e) {
                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                        }
                                        break;
                                    case "remove":
                                        try {
                                            City city = cm.getCityOfMember(p);
                                            City enemy = CitiesCache.get(args[2]);
                                            if(city.getOwner().equals(p.getName())){
                                                if(city.getRelationsManager().removeEnemy(enemy)){
                                                    p.sendMessage(ChatColor.GREEN +  enemy.getName() + " n'est plus ennemie avec votre ville !");
                                                } else {
                                                    p.sendMessage(ChatColor.RED +  "Cette ville n'est pas ennemie !");
                                                }
                                            } else {
                                                p.sendMessage(ChatColor.RED + "Tu n'est pas l'owner de ta ville !");
                                            }
                                        } catch (TownyException e) {
                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                        }
                                        break;
                                    default:
                                        p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city enemy <add:remove:list> <nom>");
                                        break;
                                }
                            } else if(args.length == 2) {
                                if(args[1].equals("list")){
                                    try {
                                        City city = cm.getCityOfMember(p);
                                        p.sendMessage(ChatColor.GREEN + "Voici les ennemis de votre ville : ");
                                        for(City c : city.getRelationsManager().getEnemies()){
                                            p.sendMessage(ChatColor.GREEN + "-> " + c.getName());
                                        }
                                    } catch (TownyException e) {
                                        p.sendMessage(ChatColor.RED + e.getMessage());
                                    }
                                }
                            } else {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city enemy <add:remove:list> <nom>");
                            }
                            break;
                        default:
                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city <create:members:claim:owner:list:remove:info:rename:ally:enemy>");
                            break;
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city <create:members:claim:owner:list:remove:info:rename:ally:enemy>");
                }
            } else if (cmd.equals(commands.get(2))) {
                if (p.isOp()) {
                    if (args.length > 0) {
                        switch (args[0]) {
                            case "create":
                                try {
                                    String name = args[1];
                                    if (name.length() <= 20) {
                                        try {
                                            new City(name, "DiscUniverse", args[2]);
                                        } catch (TownyException e) {
                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "Arguments invalides ! Le nom doit faire moins de 20 caractères.");
                                    }
                                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /scity create <nom> <pseudo> (le joueur doit être connecté)");
                                }
                                break;
                            default:
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /scity <create>");
                                break;
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Tu n'es pas op.");
                }
            }
        }
        return false;
    }

    private boolean isMemberAndOwner(Player p) {
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
