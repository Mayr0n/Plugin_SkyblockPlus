package xyz.nyroma.Capitalism.bourse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ShopCommands implements CommandExecutor, InventoryHolder {
    public List<String> getCommands() {
        return Arrays.asList("shop");
    }

    @Override
    public Inventory getInventory() {
        Inventory inv = Bukkit.createInventory(this, 54, "Shop");
        try {
            for (Shopstand ss : ShopstandCache.stands) {
                for (Marchandise m : ss.getVentes().keySet()) {
                    ItemStack is = new ItemStack(Material.valueOf(m.getMaterial()));
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName(is.getType().toString());
                    im.setLore(Arrays.asList(
                            ChatColor.DARK_AQUA + "Vendeur : ",
                            ChatColor.AQUA + ss.getPlayer(),
                            ChatColor.DARK_AQUA + "Prix de vente : ",
                            ChatColor.AQUA + String.valueOf(ss.getVentes().get(m)) + " Nyr",
                            ChatColor.DARK_AQUA + "ID de transaction : ",
                            ChatColor.AQUA + String.valueOf(m.getID())
                    ));
                    if(m.getEnchants().size() > 0){
                        int i = 0;
                        for(String ench : m.getEnchants()){
                            System.out.println(ench);
                            im.addEnchant(Enchantment.getByName(ench), m.getLevels().get(i), true);
                            i++;
                        }
                    }
                    is.setItemMeta(im);
                    is.setAmount(m.getAmount());
                    inv.addItem(is);
                    System.out.println("??");
                }
            }
        } catch(NullPointerException ignored){
        }
        return inv;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comm, String arg, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            String cmd = comm.getName();
            if (cmd.equals(getCommands().get(0))) {
                if(args.length == 0){
                    p.openInventory(getInventory());
                } else {
                    try {
                        switch (args[0]) {
                            case "add":
                                Shopstand ss;
                                try {
                                    ss = ShopstandCache.get(p.getName());
                                } catch (ShopException e) {
                                    ss = new Shopstand(p.getName());
                                }
                                    ItemStack it = p.getInventory().getItemInMainHand();
                                    float price = Float.parseFloat(args[1]);
                                    if (price < 500000 && price > 0) {
                                        long ID = ss.addVente(it, price);
                                        if(ID != 0) {
                                            p.getInventory().setItemInMainHand(null);
                                            p.sendMessage(ChatColor.GREEN + it.getItemMeta().getDisplayName() + " mis en vente pour " + price + " Nyr.");
                                            p.sendMessage(ChatColor.DARK_GREEN + "ID de la transaction : " + ID);
                                        } else {
                                            p.sendMessage(ChatColor.RED + "Vous ne pouvez pas vendre plus de " + ss.getMax() + " items.");
                                        }
                                    } else {
                                        p.sendMessage(ChatColor.RED + "Le prix doit être contenu entre 0 et 500 000 Nyr.");
                                    }
                                break;
                            case "remove":
                                if (args.length == 2) {
                                    long ID = Long.parseLong(args[1]);
                                    Shopstand shs;
                                    try {
                                        shs = ShopstandCache.get(p.getName());
                                    } catch (ShopException e) {
                                        shs = new Shopstand(p.getName());
                                    }
                                    if(shs.removeVente(ID)){
                                        p.sendMessage(ChatColor.RED + "Transaction supprimée du shop !");
                                    } else {
                                        p.sendMessage(ChatColor.RED + "L'ID est invalide.");
                                    }
                                } else {
                                    p.sendMessage(ChatColor.RED + "Arguments invalides. Syntaxe : /shop remove <MATERIAL>");
                                }
                                break;
                            case "list":
                                for(Shopstand sts : ShopstandCache.stands){
                                    try {
                                        p.sendMessage(ChatColor.GREEN + sts.getPlayer() + ", " + sts.getVentes().keySet().size() + "/" + sts.getMax() + " ventes.");
                                    } catch(NullPointerException e){
                                        p.sendMessage(ChatColor.RED + "Il n'y a pas de shop.");
                                    }
                                }
                                break;
                            case "help":
                                p.sendMessage(ChatColor.DARK_GREEN + "Pour vendre, avec l'item en main : /shop add <montant>");
                                p.sendMessage(ChatColor.DARK_GREEN + "Pour retirer de la vente : /shop remove <MATERIAL> ; exemple : /shop remove SPRUCE_LOG");
                                p.sendMessage(ChatColor.DARK_GREEN + "Pour visiter le shop global : /shop");
                                p.sendMessage(ChatColor.DARK_GREEN + "Pour visiter la liste des vendeurs : /shop list");
                                break;
                            default:
                                p.sendMessage(ChatColor.RED + "Arguments invalides. Syntaxe : /shop <add:remove> <price:material>");
                                break;
                        }
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        p.sendMessage(ChatColor.RED + "Arguments invalides. Syntaxe : /shop <add:remove> <price:material>");
                    }
                }
            }
        }
        return false;
    }
}
