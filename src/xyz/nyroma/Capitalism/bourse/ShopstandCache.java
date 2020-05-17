package xyz.nyroma.Capitalism.bourse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.nyroma.main.speedy;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopstandCache {
    public static List<Shopstand> stands = new ArrayList<>();

    public static void setup(JavaPlugin plugin) {
        speedy.testFolderExist(new File("data/shops/"));
        speedy.testFolderExist(new File("data/shops/stands/"));

        System.out.println("Chargement des shops officiels...");
        stands.addAll(ShopstandCache.getStandsFromFile());

        System.out.println("Shops officiels chargés !");

        new BukkitRunnable() {
            @Override
            public void run() {
                serializeAll();
            }
        }.runTaskTimer(plugin, 10 * 60 * 20L, 10 * 60 * 20L);
    }

    public static boolean add(Shopstand ss) {
        return stands.add(ss);
    }

    public static boolean remove(Shopstand ss) {
        stands.remove(ss);
        return new File("data/shops/stands/" + ss.getPlayer() + ".json").delete();
    }

    public static Shopstand get(String name) throws ShopException {
        for (Shopstand ss : stands) {
            try {
                if (ss.getPlayer().equals(name)) {
                    return ss;
                }
            } catch (NullPointerException e) {
                return new Shopstand(name);
            }
        }
        throw new ShopException("Il n'y a pas de stand pour cet item.");
    }

    public static boolean hasID(long id) {
        try {
            for (Shopstand ss : stands) {
                for (Marchandise m : ss.getVentes().keySet()) {
                    if (m.getID() == id) {
                        return true;
                    }
                }
            }
            return false;
        } catch(NullPointerException e){
            return false;
        }
    }

    public static void serializeAll() {
        System.out.println("Enregistrement des stands...");
        for (Shopstand ss : stands) {
            try {
                serializeStand(ss);
                backupStand(ss);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Stands enregistrés !");
    }

    private static void backupStand(Shopstand ss) throws IOException {
        writeInJson(new File("data/shops/stands/" + ss.getPlayer() + "_backup.json"), ss);
    }

    public static void serializeStand(Shopstand ss) throws IOException {
        writeInJson(new File("data/shops/stands/" + ss.getPlayer() + ".json"), ss);
    }

    private static void writeInJson(File file, Shopstand ss) throws IOException {
        speedy.testFileExist(file);

        GsonBuilder builder = new GsonBuilder();
        Gson g = builder.setPrettyPrinting().create();
        String json = g.toJson(ss);
        FileWriter fw = new FileWriter(file);
        fw.write(json);
        fw.close();
    }

    public static List<Shopstand> getStandsFromFile() {
        File folder = new File("data/shops/stands/");
        List<Shopstand> stands = new ArrayList<>();
        try {
            for (File file : folder.listFiles()) {
                try {
                    if (file.getName().substring(file.getName().length() - 5).equals(".json")) {
                        if (!file.getName().contains("backup")) {
                            try {
                                Shopstand ss = getStandFromFile(file);
                                ShopstandCache.stands.add(ss);
                            } catch (ShopException e) {
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
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Mauvaise extension, suppresion du fichier \"" + file.getName() + "\"...");
                    if (file.delete()) {
                        System.out.println("Fichier supprimé.");
                    } else {
                        System.out.println("Le fichier n'a pas pu être supprimé.");
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Il n'existe aucun shop officiel sur le serveur.");
        }
        return stands;
    }

    public static Shopstand getStandFromFile(File file) throws ShopException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        try {
            return gson.fromJson(new FileReader(file), Shopstand.class);
        } catch (IOException e) {
            throw new ShopException("ah");
        }
    }


}
