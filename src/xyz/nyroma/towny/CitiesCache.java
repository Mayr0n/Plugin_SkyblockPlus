package xyz.nyroma.towny;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.nyroma.main.speedy;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CitiesCache {
    private static List<City> cities = new ArrayList<>();
    private static List<Long> ids = new ArrayList<>();

    public static boolean contains(String name) {
        try {
            CitiesCache.get(name);
            return true;
        } catch (TownyException e) {
            return false;
        }
    }

    public static boolean hasID(long id) {
        return ids.contains(id);
    }

    public static void setup(JavaPlugin plugin) {
        speedy.testFolderExist(new File("data/towny/"));
        speedy.testFolderExist(new File("data/towny/" + "cities/"));

        System.out.println("Chargement des villes...");

        try {
            cities.addAll(CitiesCache.getCitiesFromFile().keySet());
        } catch (TownyException e) {
            System.out.println("Il n'existe aucune ville sur le serveur.");
        }

        System.out.println("??");

        try {
            City city = CitiesCache.get("l'Etat");
            city.getMoneyManager().setTaxes(0);
            city.getClaimsManager().setMax(99999);
            city.getClaimsManager().addClaim(new Location(Bukkit.getWorld("world"), 1, 0, 1));
            city.getClaimsManager().addClaim(new Location(Bukkit.getWorld("world"), -1, 0, -1));
            city.getClaimsManager().addClaim(new Location(Bukkit.getWorld("world"), 1, 0, -1));
            city.getClaimsManager().addClaim(new Location(Bukkit.getWorld("world"), -1, 0, 1));
            System.out.println("1");
            city.getRelationsManager().setNice(false);
            System.out.println("2");
            city.getRelationsManager().setEvil(false);
            System.out.println("3");
            city.getRelationsManager().addAlly(CitiesCache.get("Warriors"));
            System.out.println("4");
        } catch (TownyException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Villes chargées !");

        new BukkitRunnable() {
            @Override
            public void run() {
                serializeAll();
            }
        }.runTaskTimer(plugin, 10 * 60 * 20L, 10 * 60 * 20L);
    }

    public static City get(String name) throws TownyException {
        for (City city : cities) {
            if (city.getName().equals(name)) return city;
        }
        throw new TownyException("Cette ville n'existe pas !");
    }

    public static boolean remove(City city) {
        return cities.remove(city);
    }

    public static List<City> getCities() {
        return cities;
    }

    public static void add(City city) {
        cities.add(city);
    }

    public static void addID(long id) {
        ids.add(id);
    }

    public static void serializeAll() {
        System.out.println("Enregistrement des villes...");
        for (City city : cities) {
            try {
                serializeCity(city);
                backupCity(city);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Villes enregistrées !");
    }

    private static void backupCity(City city) throws IOException {
        writeInJson(new File("data/towny/" + "cities/" + city.getID() + "_backup.json"), city);
    }

    public static void serializeCity(City city) throws IOException {
        writeInJson(new File("data/towny/" + "cities/" + city.getID() + ".json"), city);
    }

    private static void writeInJson(File file, City city) throws IOException {
        speedy.testFileExist(file);

        GsonBuilder builder = new GsonBuilder();
        Gson g = builder.setPrettyPrinting().create();
        String json = g.toJson(city);
        FileWriter fw = new FileWriter(file);
        fw.write(json);
        fw.close();
    }

    public static Hashtable<City, String> getCitiesFromFile() throws TownyException {
        File citiesFolder = new File("data/towny/" + "cities/");
        Hashtable<City, String> cities = new Hashtable<>();
        try {
            for (File file : citiesFolder.listFiles()) {
                try {
                    if (file.getName().substring(file.getName().length() - 5).equals(".json")) {
                        if (!file.getName().contains("backup")) {
                            try {
                                City city = getCityFromFile(file);
                                CitiesCache.cities.add(city);
                                System.out.println("Ville \"" + city.getName() + "\"ajoutée.");
                            } catch (TownyException e) {
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
            throw new TownyException("Il n'existe aucune ville sur le serveur.");
        }
        return cities;
    }

    public static City getCityFromFile(File file) throws TownyException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        try {
            return gson.fromJson(new FileReader(file), City.class);
        } catch (IOException e) {
            throw new TownyException("ah");
        }
    }
}
