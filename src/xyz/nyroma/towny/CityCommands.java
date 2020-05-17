package xyz.nyroma.towny;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.nyroma.Capitalism.bank.Bank;
import xyz.nyroma.Capitalism.bank.BankCache;
import xyz.nyroma.main.NotFoundException;
import xyz.nyroma.main.speedy;

import java.util.Arrays;
import java.util.List;

public class CityCommands implements CommandExecutor {

    private List<String> commands = Arrays.asList("city", "ct", "scity", "eclaim");
    private CityManager cm = new CityManager();

    public List<String> getCommands() {
        return commands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String cmd = command.getName();
            Bank bank = BankCache.get(p.getName());

            if (cmd.equals(commands.get(0))) {
                if (args.length > 0) {
                    if (args[0].equals("create")) {
                        if (bank.getAmount() >= 50) {
                            try {
                                String name = args[1];
                                if (name.length() <= 20) {
                                    try {
                                        City c = new City(name, "DiscUniverse", p.getName());
                                        bank.remove(50);
                                        c.getMoneyManager().addMoney(25);
                                        p.sendMessage(ChatColor.GREEN + "Votre ville " + name + " a été créée !");
                                        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 100, 1);
                                        p.sendMessage(ChatColor.GREEN + "N'oubliez pas de remplir la banque de votre ville avec la commande " +
                                                ChatColor.DARK_GREEN + " /city money add <montant>" +
                                                ChatColor.GREEN + ", la taxe actuelle est de " + c.getMoneyManager().getTaxes() + " Nyr débitée toutes les 12h.");
                                    } catch (TownyException e) {
                                        p.sendMessage(ChatColor.RED + e.getMessage());
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Le nom doit faire moins de 20 caractères.");
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city create <nom>");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Vous n'avez pas assez d'argent ! Il faut 50 Nyr pour créer votre ville !");
                        }
                    } else if (args[0].equals("list")) {
                        p.sendMessage(ChatColor.DARK_GREEN + "- Voici toutes les villes existantes et leurs owners ! -");
                        for (City c : CitiesCache.getCities()) {
                            p.sendMessage(ChatColor.GOLD + c.getName() + ", gérée par " + c.getOwner());
                        }
                        p.sendMessage(ChatColor.DARK_GREEN + "---------------------------------------------");
                    } else if (args[0].equals("info")) {
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
                            p.sendMessage(ChatColor.DARK_GREEN + "Owner : " + ChatColor.GREEN + c.getOwner());
                            p.sendMessage(ChatColor.DARK_GREEN + "ID : " + ChatColor.GREEN + c.getID());
                            p.sendMessage(ChatColor.DARK_GREEN + "Réserve : " + ChatColor.GREEN + c.getMoneyManager().getAmount() + " Nyr");
                            p.sendMessage(ChatColor.DARK_GREEN + "Taxes : " + ChatColor.GREEN + c.getMoneyManager().getTaxes() + " Nyr");
                            p.sendMessage(ChatColor.DARK_GREEN + "Royaume : " + ChatColor.GREEN + c.getRoyaume());
                            p.sendMessage(ChatColor.DARK_GREEN + "Membres : ");
                            for (String st : c.getMembersManager().getMembers()) {
                                p.sendMessage(ChatColor.GREEN + "- " + st);
                            }
                            p.sendMessage(ChatColor.DARK_GREEN + "Alliés : ");
                            if (c.getRelationsManager().getNice()) {
                                p.sendMessage(ChatColor.GREEN + "- Tout le monde");
                            } else {
                                for (City city : c.getRelationsManager().getAllies()) {
                                    p.sendMessage(ChatColor.GREEN + "- " + city.getName());
                                }
                            }
                            p.sendMessage(ChatColor.DARK_GREEN + "Ennemis : ");
                            if (c.getRelationsManager().getEvil()) {
                                p.sendMessage(ChatColor.GREEN + "- Tout le monde");
                            } else {
                                for (City city : c.getRelationsManager().getEnemies()) {
                                    p.sendMessage(ChatColor.GREEN + "- " + city.getName());
                                }
                            }
                            p.sendMessage(ChatColor.DARK_GREEN + "Claims : (" + c.getClaimsManager().getAmount() + "/" + c.getClaimsManager().getMax() + ")");
                            for (String world : c.getClaimsManager().getClaims().keySet()) {
                                for (int x : c.getClaimsManager().getClaims().get(world).keySet()) {
                                    for (int z : c.getClaimsManager().getClaims().get(world).get(x)) {
                                        p.sendMessage(ChatColor.GREEN + "-> " + world + " " + x + " " + z);
                                    }
                                }
                            }
                            p.sendMessage(ChatColor.DARK_GREEN + "-----------------------");
                        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city info <nom>");
                        } catch (TownyException e) {
                            p.sendMessage(ChatColor.RED + e.getMessage());
                        }
                    } else {
                        if (cm.getCityOfMember(p).isPresent()) {
                            City city = cm.getCityOfMember(p).get();
                            boolean isOwner = city.getOwner().equals(p.getName());
                            if (!isOwner) {
                                if (args.length == 3) {
                                    if (args[0].equals("money") && args[1].equals("add")) {
                                        try {
                                            int amount = Integer.parseInt(args[2]);
                                            if (bank.remove(amount)) {
                                                city.getMoneyManager().addMoney(amount);
                                                p.sendMessage(ChatColor.GREEN + args[2] + " Nyr ont été placés dans la banque de votre ville.");
                                            } else {
                                                p.sendMessage(ChatColor.RED + "Vous n'avez pas le montant nécessaire dans votre banque personnelle.");
                                            }
                                        } catch (NumberFormatException e) {
                                            p.sendMessage(ChatColor.RED + "Le montant indiqué est invalide. Syntaxe : /city money add <montant>");
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "La seule commande disponible si vous n'êtes pas owner est /city money add <montant> !");
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "La seule commande disponible si vous n'êtes pas owner est /city money add <montant> !");
                                }
                            } else {
                                switch (args[0]) {
                                    case "members":
                                        try {
                                            switch (args[1]) {
                                                case "add":
                                                    String name = args[2];
                                                    try {
                                                        Player toAdd = speedy.getPlayerByName(p.getServer(), name);
                                                        city.sendInvit(toAdd);
                                                        p.sendMessage(ChatColor.GREEN + "Une invitation lui a été envoyée !");
                                                    } catch (NotFoundException e) {
                                                        p.sendMessage(ChatColor.RED + e.getMessage());
                                                    }
                                                    break;
                                                case "remove":
                                                    String n = args[2];
                                                    if (city.getMembersManager().removeMember(n)) {
                                                        p.sendMessage(ChatColor.GREEN + n + " a été renvoyé de la ville");
                                                    } else {
                                                        p.sendMessage(ChatColor.DARK_RED + "Le joueur n'a pas pu être retiré.");
                                                    }
                                                    break;
                                            }
                                        } catch (ArrayIndexOutOfBoundsException e) {
                                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city <add:remove> <pseudo>");
                                        }
                                        break;
                                    case "claim":
                                        try {
                                            String action = args[1];
                                            switch (action) {
                                                case "add":
                                                    if (cm.getClaimer(p.getLocation()).isPresent()) {
                                                        City claimer = cm.getClaimer(p.getLocation()).get();
                                                        p.sendMessage(ChatColor.RED + "Ce territoire est déjà claim par la ville \"" + claimer.getName() + "\" !");
                                                    } else {
                                                        if (city.getClaimsManager().addClaim(p.getLocation())) {
                                                            p.sendMessage(ChatColor.GREEN + "Le chunk a bien été claim !");
                                                        } else {
                                                            p.sendMessage(ChatColor.DARK_RED + "Une erreur est survenue ! As-tu bien exactement ou moins de 5 claims ? Ton chunk n'est-il pas déjà claim ?");
                                                        }
                                                    }
                                                    break;
                                                case "remove":
                                                    if (city.getClaimsManager().removeClaim(p.getLocation())) {
                                                        p.sendMessage(ChatColor.GREEN + "Le chunk a bien été unclaim !");
                                                    } else {
                                                        p.sendMessage(ChatColor.DARK_RED + "Une erreur est survenue ! Est-ce que ce chunk t'appartient ? Ton chunk est-il déjà claim ?");
                                                    }
                                                    break;
                                                case "list": {
                                                    ClaimsManager cm = city.getClaimsManager();
                                                    p.sendMessage(ChatColor.DARK_GREEN + "--------- Votre ville a claim ces chunks : ---------");
                                                    for (String world : cm.getClaims().keySet()) {
                                                        for (int X : cm.getClaims().get(world).keySet()) {
                                                            for (int Z : cm.getClaims().get(world).get(X)) {
                                                                p.sendMessage(ChatColor.GREEN + "world : " + world + "x = " + X + ", Z = " + Z);
                                                            }
                                                        }
                                                    }
                                                    p.sendMessage(ChatColor.DARK_GREEN + "---------------------------------------------------");
                                                }
                                                break;
                                                case "more":
                                                    float newTaxes = city.getMoneyManager().getTaxes() + 1.5f;
                                                    if (city.getMoneyManager().getAmount() >= (newTaxes + 25)) {
                                                        city.getClaimsManager().setMax(city.getClaimsManager().getMax() + 1);
                                                        city.getMoneyManager().setTaxes(newTaxes);
                                                        city.getMoneyManager().removeMoney(25);
                                                        p.sendMessage(ChatColor.GREEN + "Votre ville \"" + city.getName() + "\" comporte désormais " + city.getClaimsManager().getMax() +
                                                                "claims, les taxes sont maintenant de " + city.getMoneyManager().getTaxes() + " Nyr toutes les 12h");
                                                    } else {
                                                        p.sendMessage(ChatColor.RED + "Vous n'avez pas assez d'argent dans la banque de votre ville ! (" + (newTaxes + 25) + "Nyr nécessaires)");
                                                    }
                                                    break;
                                                case "less":
                                                    if (city.getClaimsManager().getMax() > 5) {
                                                        city.getClaimsManager().setMax(city.getClaimsManager().getMax() - 1);
                                                        city.getMoneyManager().setTaxes(city.getMoneyManager().getTaxes() - 0.75f);
                                                        p.sendMessage(ChatColor.GREEN + "Vous avez maintenant " + city.getClaimsManager().getMax() + " claims, donc une taxe de " +
                                                                city.getMoneyManager().getTaxes() + " Nyr toutes les 12h.");
                                                    } else {
                                                        p.sendMessage(ChatColor.RED + "Votre ville ne peut pas avoir moins de 5 claims !");
                                                    }
                                                default:
                                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city claim <add:remove:list:more:less>");
                                                    break;
                                            }
                                        } catch (ArrayIndexOutOfBoundsException e) {
                                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city claim <add:remove:list:more:less>");
                                        }
                                        break;
                                    case "owner":
                                        try {
                                            String name = args[1];
                                            try {
                                                Player newOwner = speedy.getPlayerByName(p.getServer(), name);
                                                if (city.getMembersManager().isMember(newOwner.getName())) {
                                                    city.changeOwner(newOwner);
                                                    p.sendMessage(ChatColor.GREEN + "Le changement de propriété a été réalisé !");
                                                } else {
                                                    p.sendMessage(ChatColor.RED + "Ce joueur n'est même pas dans ta ville !");
                                                }
                                            } catch (NotFoundException e) {
                                                p.sendMessage(ChatColor.RED + e.getMessage());
                                            }
                                        } catch (ArrayIndexOutOfBoundsException e) {
                                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city owner <pseudo>");
                                        }
                                        break;
                                    case "remove":
                                        if (CitiesCache.remove(city)) {
                                            p.sendMessage(ChatColor.GREEN + "Votre ville " + city.getName() + " a été supprimée.");
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Votre ville " + city.getName() + " n'a pas pu être supprimée.");
                                        }
                                        break;
                                    case "rename":
                                        if (args.length > 1) {
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
                                            p.sendMessage(ChatColor.RED + "Il faut un autre nom de ville.");
                                        }
                                        break;
                                    case "ally":
                                        if (args.length >= 3) {
                                            switch (args[1]) {
                                                case "add":
                                                    if (args[2].equals("all") && !CitiesCache.contains("all")) {
                                                        if (city.getOwner().equals(p.getName())) {
                                                            city.getRelationsManager().setNice(true);
                                                            p.sendMessage(ChatColor.GREEN + "Votre ville est alliée avec tout le monde !");
                                                        } else {
                                                            p.sendMessage(ChatColor.RED + "Tu n'est pas l'owner de ta ville !");
                                                        }
                                                    } else {
                                                        try {
                                                            City ally = CitiesCache.get(getArgsLeft(2, args));
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
                                                        } catch (TownyException e) {
                                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                                        }
                                                    }
                                                    break;
                                                case "remove":
                                                    try {
                                                        City ally = CitiesCache.get(getArgsLeft(2, args));
                                                        if (city.getRelationsManager().removeAlly(ally)) {
                                                            p.sendMessage(ChatColor.GREEN + ally.getName() + " n'est plus alliée avec votre ville !");
                                                        } else {
                                                            p.sendMessage(ChatColor.RED + "Cette ville n'est pas alliée !");
                                                        }
                                                    } catch (TownyException e) {
                                                        p.sendMessage(ChatColor.RED + e.getMessage());
                                                    }
                                                    break;
                                                default:
                                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city ally <add:remove:list> <nom>");
                                                    break;
                                            }
                                        } else if (args.length == 2) {
                                            if (args[1].equals("list")) {
                                                p.sendMessage(ChatColor.GREEN + "Voici les alliés de votre ville : ");
                                                for (City c : city.getRelationsManager().getAllies()) {
                                                    p.sendMessage(ChatColor.GREEN + "-> " + c.getName());
                                                }
                                            }
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city ally <add:remove:list> <nom>");
                                        }
                                        break;
                                    case "enemy":
                                        if (args.length >= 3) {
                                            switch (args[1]) {
                                                case "add":
                                                    if (args[2].equals("all")) {
                                                        if (city.getOwner().equals(p.getName())) {
                                                            city.getRelationsManager().setEvil(true);
                                                            p.sendMessage(ChatColor.GREEN + "Votre ville est ennemie avec tout le monde !");
                                                        } else {
                                                            p.sendMessage(ChatColor.RED + "Tu n'est pas l'owner de ta ville !");
                                                        }
                                                    } else {
                                                        try {
                                                            City enemy = CitiesCache.get(getArgsLeft(2, args));
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
                                                        } catch (TownyException e) {
                                                            p.sendMessage(ChatColor.RED + e.getMessage());
                                                        }
                                                    }
                                                    break;
                                                case "remove":
                                                    try {
                                                        City enemy = CitiesCache.get(getArgsLeft(2, args));
                                                        if (city.getRelationsManager().removeEnemy(enemy)) {
                                                            p.sendMessage(ChatColor.GREEN + enemy.getName() + " n'est plus ennemie avec votre ville !");
                                                        } else {
                                                            p.sendMessage(ChatColor.RED + "Cette ville n'est pas ennemie !");
                                                        }
                                                    } catch (TownyException e) {
                                                        p.sendMessage(ChatColor.RED + e.getMessage());
                                                    }
                                                    break;
                                                default:
                                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city enemy <add:remove:list> <nom>");
                                                    break;
                                            }
                                        } else if (args.length == 2) {
                                            if (args[1].equals("list")) {
                                                p.sendMessage(ChatColor.GREEN + "Voici les ennemis de votre ville : ");
                                                for (City c : city.getRelationsManager().getEnemies()) {
                                                    p.sendMessage(ChatColor.GREEN + "-> " + c.getName());
                                                }
                                            }
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city enemy <add:remove:list> <nom>");
                                        }
                                        break;
                                    case "money":
                                        try {
                                            switch (args[1]) {
                                                case "add":
                                                    int amount = Integer.parseInt(args[2]);
                                                    if (bank.remove(amount)) {
                                                        city.getMoneyManager().addMoney(amount);
                                                        p.sendMessage(ChatColor.GREEN + args[2] + " Nyr ont été placés dans la banque de votre ville.");
                                                    } else {
                                                        p.sendMessage(ChatColor.RED + "Vous n'avez pas le montant nécessaire dans votre banque personnelle.");
                                                    }
                                                    break;
                                                case "remove":
                                                    int a = Integer.parseInt(args[2]);
                                                    Bank b = BankCache.get(p.getName());
                                                    if (city.getOwner().equals(p.getName())) {
                                                        if (city.getMoneyManager().removeMoney(a)) {
                                                            b.add(a);
                                                            p.sendMessage(ChatColor.GREEN + args[2] + " Nyr ont été déplacés vers votre banque personnelle.");
                                                        } else {
                                                            p.sendMessage(ChatColor.RED + "Votre ville n'a pas l'argent nécessaire pour retirer cette somme.");
                                                        }
                                                    } else {
                                                        p.sendMessage(ChatColor.RED + "Vous n'êtes pas l'owner de votre ville !");
                                                    }
                                                    break;
                                                case "get":
                                                    p.sendMessage(ChatColor.GREEN + "Banque de votre ville : " + city.getMoneyManager().getAmount() + " Nyr");
                                                    break;
                                                default:
                                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city money <add:remove:get>");
                                                    break;
                                            }
                                        } catch (ArrayIndexOutOfBoundsException e) {
                                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city money <add:remove:get>");
                                        }
                                        break;
                                    default:
                                        p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /city <create:members:claim:owner:list:remove:info:rename:ally:enemy>");
                                        break;
                                }
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "Vous n'appartenez à aucune ville !");
                        }
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
                            case "remove":
                                try {
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 1; i < args.length; i++) {
                                        sb.append(args[i]).append(" ");
                                    }
                                    String s = sb.toString();
                                    int l = s.length() - 1;
                                    String name = s.substring(0, l);
                                    try {
                                        new CityManager().removeCity(CitiesCache.get(name));
                                        p.sendMessage(ChatColor.GREEN + "La ville " + name + " a bien été supprimée.");
                                    } catch (TownyException e) {
                                        p.sendMessage(ChatColor.RED + "La ville " + name + "n'existe pas !");
                                    }
                                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /scity remove <nom>");
                                }
                                break;
                            case "applyTaxes":
                                new CityManager().applyTaxes(p.getServer());
                                break;
                            default:
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /scity <create:remove:applyTaxes>");
                                break;
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Tu n'es pas op.");
                }
            } else if (cmd.equals(commands.get(3))) {
                if (p.isOp()) {
                    try {
                        City state = CitiesCache.get("l'Etat");
                        state.getClaimsManager().addClaim(p.getLocation());
                        state.getMoneyManager().setTaxes(0);
                        p.sendMessage(ChatColor.GREEN + "L'Etat a annexé le chunk.");
                    } catch (TownyException e) {
                        p.sendMessage(ChatColor.RED + e.getMessage());
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Tu n'es pas OP.");
                }
            }
        }
        return false;
    }

    private String getArgsLeft(int beginIndex, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = beginIndex; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        String s = sb.toString();
        int l = s.length() - 1;
        if (l < 0) {
            return "null";
        } else {
            return s.substring(0, l);
        }
    }

}
