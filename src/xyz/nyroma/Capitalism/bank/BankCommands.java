package xyz.nyroma.Capitalism.bank;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.nyroma.main.NotFoundException;
import xyz.nyroma.main.speedy;

import java.util.Arrays;
import java.util.List;

public class BankCommands implements CommandExecutor {

    public List<String> getCommands(){
        return Arrays.asList("bank", "sbank");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command comm, String arg, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            String cmd = comm.getName();
            if(cmd.equals(getCommands().get(0))){
                Bank bank = BankCache.get(p.getName());
                try {
                    switch (args[0]) {
                        case "send":
                            if(args.length == 3){
                                    if(speedy.getPlayerByName(args[1]).isPresent()){
                                        Player toSend = speedy.getPlayerByName(args[1]).get();
                                        Bank b = BankCache.get(toSend.getName());

                                        try {
                                            int amount = Integer.parseInt(args[2]);
                                            if(bank.remove(amount)){
                                                p.sendMessage(ChatColor.GREEN + Integer.toString(amount) + " Nyr ont été retirés de votre compte.");
                                                b.add(amount);
                                                toSend.sendMessage(ChatColor.GREEN + "Vous avez reçu " + amount + " Nyr de la part de " + p.getName() + " !");
                                                p.sendMessage(ChatColor.GREEN + toSend.getName() + " a bien reçu les " + amount + " Nyr !");
                                            } else {
                                                p.sendMessage(ChatColor.RED + "Vous n'avez pas le montant nécessaire dans votre banque !");
                                            }
                                        } catch(NumberFormatException e){
                                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /bank send <pseudo> <montant>");
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "Arguments invalides ! Ce joueur n'est pas connecté.");
                                    }
                            } else {
                                p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /bank send <pseudo> <montant>");
                            }
                            break;
                        case "get":
                            p.sendMessage(ChatColor.GREEN + "Montant de votre banque : " + bank.getAmount() + " Nyr");
                            break;
                        case "bill":
                            try {
                                p.getInventory().addItem(bank.getBill(Float.parseFloat(args[1])));
                            } catch(BankException e){
                                p.sendMessage(ChatColor.RED + e.getMessage());
                            } catch(NumberFormatException e){
                                p.sendMessage(ChatColor.RED + "Argument invalide ! Syntaxe : /bank bill <montant>");
                            }
                            break;
                        case "getall":
                            p.sendMessage(ChatColor.GREEN + "Voici les montants des banques existantes :");
                            for(Bank b : BankCache.getBanks()){
                                p.sendMessage(ChatColor.GREEN + "-> " + b.getPlayer() + " : " + b.getAmount() + " Nyr");
                            }
                            break;
                        default :
                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /bank <send:bill:get:getall>");
                            break;
                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /bank <send:bill:get:getall>");
                }
            }
            else if(cmd.equals(getCommands().get(1)) && p.isOp()){
                try {
                    String pseudo = args[1];
                    switch (args[0]) {
                        case "add":
                            int amount = Integer.parseInt(args[2]);
                            BankCache.get(pseudo).add(amount);
                            p.sendMessage(ChatColor.GREEN + args[2] + " Nyr ont été ajoutés au compte de " + pseudo);
                            break;
                        case "remove":
                            int a = Integer.parseInt(args[2]);
                            if(BankCache.get(pseudo).remove(a)){
                                p.sendMessage(ChatColor.GREEN + args[2] + " Nyr ont été supprimés du compte de " + pseudo);
                            } else {
                                p.sendMessage(ChatColor.GREEN + pseudo + " n'a pas assez de money pour retirer ce montant.");
                            }
                            break;
                        case "get":
                            p.sendMessage(ChatColor.GREEN + "Montant de la banque : " + BankCache.get(pseudo).getAmount() + " Nyr");
                            break;
                        case "reset":
                            if(pseudo.equals("all")){
                                for(Bank bank : BankCache.getBanks()){
                                    bank.remove(bank.getAmount());
                                }
                                p.sendMessage(ChatColor.GREEN + "Toutes les banques ont perdu leurs réserves.");
                            } else {
                                BankCache.banks.remove(BankCache.get(pseudo));
                                p.sendMessage(ChatColor.GREEN + "La banque de " + pseudo + " a été réinitialisée.");
                            }
                            break;
                        default:
                            p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /sbank <add:remove:get>");
                            break;
                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    p.sendMessage(ChatColor.RED + "Arguments invalides ! Syntaxe : /sbank <add:remove:get>");
                }
            }
        }
        return false;
    }
}
