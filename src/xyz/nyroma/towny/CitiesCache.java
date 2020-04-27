package xyz.nyroma.towny;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.nyroma.main.speedy;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CitiesCache {
    private static List<City> cities = new ArrayList<>();
    private static List<Long> ids = new ArrayList<>();

    public static boolean contains(String name) {
        for (City city : cities) {
            if (city.getName().equals(name)) return true;
        }
        return false;
    }

    public static boolean hasID(long id){
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

        try {
            CitiesCache.get("l'Etat");
        } catch (TownyException e) {
            try {
                new City("l'Etat", "State", "Xénée");
            } catch (TownyException e1) {
                e1.printStackTrace();
            }
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

    public static boolean remove(City city){
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Villes enregistrées !");
    }

    public static void serializeCity(City city) throws IOException {
        File cityFile = new File("data/towny/" + "cities/" + city.getID() + ".txt");
        if (!cityFile.exists()) {
            cityFile.createNewFile();
        }
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(cityFile)));
        oos.writeObject(city);
        oos.close();
    }

    public static Hashtable<City, String> getCitiesFromFile() throws TownyException {
        File citiesFolder = new File("data/towny/" + "cities/");
        Hashtable<City, String> cities = new Hashtable<>();
        try {
            for (File file : citiesFolder.listFiles()) {
                try {
                    City city = getCityFromFile(file);
                    cities.put(city, city.getOwner());
                } catch (TownyException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            throw new TownyException("Cette ville n'existe pas !");
        }
        return cities;
    }

    public static City getCityFromFile(File file) throws TownyException {
        try {
            ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            Object obj = oos.readObject();
            oos.close();
            if (obj.getClass().equals(xyz.nyroma.towny.City.class)) {

                return (City) obj;
            } else {
                throw new TownyException("Cette ville n'existe pas !");
            }
        } catch (IOException e) {
            file.delete();
            throw new TownyException("Ce fichier ne dirige pas vers une ville existante. Suppression...");
        } catch (ClassNotFoundException e) {
            throw new TownyException("Oh.");
        }
    }

    public void deserializeAll() {
        cities.addAll(getCities());
    }
}
