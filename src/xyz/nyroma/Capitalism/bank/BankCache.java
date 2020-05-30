package xyz.nyroma.Capitalism.bank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.nyroma.main.speedy;

import java.io.*;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

public class BankCache {
    public static List<Bank> banks = new ArrayList<>();
    public static Hashtable<String, Hashtable<String, Float>> amounts = new Hashtable<>();

    public static void serializeAll(){
        System.out.println("Enregistrement & backup des banques...");
        for (Bank bank : banks) {
            try {
                backupBank(bank);
                serializeBank(bank);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Enregistrement et backup des Banques terminés !");

    }
    public static List<Bank> getBanks() {
        return banks;
    }
    private static void writeInJson(File file, Bank bank) throws IOException {
        speedy.testFileExist(file);

        GsonBuilder builder = new GsonBuilder();
        Gson g = builder.setPrettyPrinting().create();
        String json = g.toJson(bank);
        FileWriter fw = new FileWriter(file);
        fw.write(json);
        fw.close();
    }
    public static void serializeBank(Bank bank) throws IOException {
        if(bank.getPlayer() != null) {
            writeInJson(new File("data/towny/" + "banks/" + bank.getPlayer() + ".json"), bank);
        } else {
            banks.remove(bank);
        }
    }
    public static void backupBank(Bank bank) throws IOException {
        if(bank.getPlayer() != null) {
            writeInJson(new File("data/towny/" + "banks/" + bank.getPlayer() + "_backup.json"), bank);
        } else {
            banks.remove(bank);
        }
    }
    public static void saveGraph(){
        try {
            Hashtable<String, Float> h = new Hashtable<>();
            for (Bank bank : banks) {
                h.put(bank.getPlayer(), bank.getAmount());
            }
            amounts.put(new GregorianCalendar().getTime().toString(), h);
            GsonBuilder builder = new GsonBuilder();
            Gson g = builder.setPrettyPrinting().create();
            String json = g.toJson(amounts);

            FileWriter fw = new FileWriter(new File("data/towny/" + "banks/graph.json"));
            fw.write(json);
            fw.close();
            System.out.println("Graph actualisé !");
        } catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void setup(JavaPlugin plugin){
        speedy.testFolderExist(new File("data/towny/"));
        speedy.testFolderExist(new File("data/towny/" + "banks/"));

        System.out.println("Chargement des banques...");

        try {
            banks.addAll(BankCache.getBanksByFile());
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Banques chargées !");

        new BukkitRunnable() {
            @Override
            public void run() {
                serializeAll();
                saveGraph();
            }
        }.runTaskTimer(plugin, 10 * 60 * 20L, 10 * 60 * 20L);
    }
    public static boolean add(Bank bank){
        return banks.add(bank);
    }
    public static boolean remove(Bank bank){
        if(banks.contains(bank)){
            return banks.remove(bank);
        } else {
            return false;
        }
    }
    public static boolean contains(String pseudo){
        for(Bank b : banks){
            if(b.getPlayer().equals(pseudo)){
                return true;
            }
        }
        return false;
    }
    public static Bank get(String pseudo) {
        for(Bank b : banks){
            if(b.getPlayer() == null){
                banks.remove(b);
            } else {
                if (b.getPlayer().equals(pseudo)) {
                    return b;
                }
            }
        }
        Bank bank = new Bank(pseudo);
        return bank;
    }
    private static List<Bank> getBanksByFile() throws BankException {
        File banksFolder = new File("data/towny/" + "banks/");
        List<Bank> banks = new ArrayList<>();
        try {
            for (File file : banksFolder.listFiles()) {
                try {
                    if (file.getName().substring(file.getName().length() - 5).equals(".json")) {
                        if(!file.getName().contains("backup")) {
                            try {
                                Bank bank = getBankFromFile(file);
                                if(bank.getPlayer() != null) {
                                    if(!bank.getPlayer().equals("null")) {
                                        banks.add(bank);
                                        System.out.println("Banque de " + bank.getPlayer() + " ajoutée.");
                                    } else {
                                        System.out.println("nul.");
                                        if(file.delete()){
                                            System.out.println("Fichier banque supprimé.");
                                        } else {
                                            System.out.println("Le fichier nul n'a pas pu être supprimé.");
                                        }
                                    }
                                } else {
                                    System.out.println("null");
                                }
                            } catch (BankException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    } else {
                        System.out.println("Mauvaise extension, suppresion du fichier \"" + file.getName() + "\"...");
                        if (file.delete()) {
                            System.out.println("Fichier supprimé.");
                        } else {
                            System.out.println("Le fichier n'a pas pu être supprimé.");
                        }
                    }
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Mauvaise extension, suppresion du fichier \"" + file.getName() + "\"...");
                    if (file.delete()) {
                        System.out.println("Fichier supprimé.");
                    } else {
                        System.out.println("Le fichier n'a pas pu être supprimé.");
                    }
                }
            }
        } catch (NullPointerException e) {
            throw new BankException("Il n'y a aucune banque sur le serveur.");
        }
        return banks;
    }
    public static Bank getBankFromFile(File file) throws BankException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        try {
            return gson.fromJson(new FileReader(file), Bank.class);
        } catch (FileNotFoundException e) {
            throw new BankException("ah");
        }
    }
}
