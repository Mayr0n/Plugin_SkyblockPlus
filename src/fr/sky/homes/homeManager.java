package fr.sky.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;

import static fr.sky.main.speedy.*;

public class homeManager {
    private int nbHomesMax = 5;
    private JavaPlugin plugin;

    public homeManager(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void sethome(Player p, String[] args){
        File pFile = getSethomeFile(p);
        try {
            if (!getSethomeFolder().exists()) {
                getSethomeFolder().mkdir();
            }
            if (!pFile.exists()) {
                pFile.createNewFile();
            }

            BufferedReader reader = new BufferedReader(new FileReader(pFile));
            ArrayList<String> savedHomes = getAllLines(reader);
            BufferedWriter bw = new BufferedWriter(new FileWriter(pFile, true));
            try {
                String name = args[0];
                Location loc = p.getLocation();

                if (savedHomes.size() >= this.nbHomesMax) {
                    p.sendMessage(ChatColor.RED + "Tu as déjà " + nbHomesMax + " homes de créés, tu ne peux pas en avoir plus ! \n" +
                            " Utilise la commande /delhome pour supprimer un de tes anciens homes !");
                } else if (args.length != 1) {
                    p.sendMessage(ChatColor.RED + "Ton nom de home doit faire un seul mot ! Syntaxe : /sethome <nom>");
                } else if (savedHomes.size() == 0) {
                    bw.write(name + " : " + p.getWorld().getName() + " " + (float) loc.getX() + " " + (float) loc.getY() + " " + (float) loc.getZ());
                    bw.write("\n");
                    p.sendMessage(ChatColor.DARK_GREEN + "Ton home \"" + name + "\" a été créé !");
                } else {
                    Boolean already = false;
                    for (String home : savedHomes) {
                        if (home.split(" ")[0].equals(name)) {
                            already = true;
                        }
                    }
                    if (already) {
                        p.sendMessage(ChatColor.RED + "Tu as déjà un home avec ce nom !");
                    } else {
                        bw.write(name + " : " + p.getWorld().getName() + " " + (float) loc.getX() + " " + (float) loc.getY() + " " + (float) loc.getZ());
                        bw.write("\n");
                        p.sendMessage(ChatColor.DARK_GREEN + "Ton home \"" + name + "\" a été créé !");
                    }
                }
            } catch(ArrayIndexOutOfBoundsException e){
                p.sendMessage(ChatColor.RED + "Il faut donner un nom à ton home !");
            }
            bw.close();
        } catch (IOException ignored) {
        }
    }
    public void delhome(Player p, String[] args){
        File pFile = getSethomeFile(p);
        if (!getSethomeFolder().exists() || !pFile.exists()) {
            p.sendMessage(ChatColor.RED + "Tu n'a pas de home créé !");
        } else {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Il faut que tu indiques un nom de home à supprimer !");
            } else {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(pFile));
                    StringBuilder keepHomes = new StringBuilder();
                    ArrayList<String> savedHomes = getAllLines(reader);
                    BufferedWriter bw = new BufferedWriter(new FileWriter(pFile));
                    String name = args[0];
                    Boolean deleted = false;

                    if (savedHomes.size() == 0) {
                        p.sendMessage(ChatColor.RED + "Tu n'a aucun home d'enregistré !");
                    } else {
                        for (String home : savedHomes) {
                            String nom = home.split(" ")[0];
                            if (!nom.equals(name)) {
                                keepHomes.append(home).append("\n");
                            } else {
                                deleted = true;
                            }
                        }
                        bw.write(keepHomes.toString());
                        if (deleted) {
                            p.sendMessage(ChatColor.DARK_GREEN + "Le home \"" + name + "\" a été supprimé");
                        } else {
                            p.sendMessage(ChatColor.RED + "Il n'y a rien à supprimer pour le nom \"" + name + "\"...");
                        }
                    }
                    bw.close();
                } catch (IOException e) {
                    p.sendMessage(ChatColor.RED + "Une erreur est survenue... Envoie un mp à Imperayser (May#8071) pour savoir ce qui se passe !");
                }
            }
        }
    }
    public void tpHome(Player p, String[] args){
        File pFile = getSethomeFile(p);
        if (!getSethomeFolder().exists() || !pFile.exists()) {
            p.sendMessage(ChatColor.RED + "Tu n'a aucun home d'enregistré ! Syntaxe pour créer un home : /sethome <nom>");
        } else if (args.length != 1) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(pFile));
                StringBuilder homesAvailables = new StringBuilder();
                homesAvailables.append("Homes : ");
                for(String home : getAllLines(reader)){
                    homesAvailables.append(home.split(" ")[0]).append(", ");
                }
                p.sendMessage(homesAvailables.toString().substring(0,homesAvailables.length()-2));
            } catch(IOException ignored){
            }
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(pFile));
                Boolean teleported = false;
                for (String home : getAllLines(reader)) {
                    String[] mots = home.split(" ");
                    String nom = mots[0];
                    if (nom.equals(args[0])) {
                        String monde = mots[2];
                        float x = Float.parseFloat(mots[3]);
                        float y = Float.parseFloat(mots[4]);
                        float z = Float.parseFloat(mots[5]);
                        Location loc = new Location(Bukkit.getWorld(monde), x, y, z);
                        if(!loc.getChunk().isLoaded()){
                            loc.getChunk().load();
                        }
                        p.teleport(loc);
                        p.sendMessage(ChatColor.DARK_GREEN + "Tu as été téléporté à ton home \"" + nom + "\" !");
                        teleported = true;
                    }
                }
                if (!teleported) {
                    p.sendMessage(ChatColor.RED + "Aucun home n'a été trouvé pour ce nom...");
                }
                reader.close();
            } catch (IOException e) {
                p.sendMessage(ChatColor.RED + "Une erreur est survenue... Envoie un mp à Imperayser (May#8071) pour savoir ce qui se passe !");
            }
        }
    }

}
