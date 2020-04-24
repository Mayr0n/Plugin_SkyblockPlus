package xyz.nyroma.towny;

import com.google.common.collect.HashMultimap;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CityManager {
    private File folder = new File("data/towny/");
    private File citiesFolder = new File("data/towny/" + "cities/");
    private File claimsFile = new File("data/towny/" + "claims.txt");

    public static List<City> cities = new ArrayList<>();

    public CityManager() {
        if (!this.folder.exists()) {
            try {
                Files.createDirectory(folder.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!this.citiesFolder.exists()) {
            try {
                Files.createDirectory(this.citiesFolder.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!this.claimsFile.exists()) {
            try {
                Files.createFile(this.claimsFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void create(String name, String royaume, Player sender) {
        try {
            new City(name, royaume, sender);
            sender.sendMessage(ChatColor.GREEN + "Votre ville " + name + " a été créée !");
            sender.playSound(sender.getLocation(), Sound.EVENT_RAID_HORN, 100, 1);
        } catch (AlreadyOwnerException | TownyException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }
    }
    public boolean isAlreadyOwner(String name) throws NotExistException {
        for(City city : getCities().keySet()){
            if(city.getOwner().equals(name)){
                return true;
            }
        }
        return false;
    }
    public void removeCity(City city) {
        File cityFile = new File("data/towny/" + "cities/" + city.getName() + ".txt");
        if (cityFile.exists()) {
            cityFile.delete();
        }
    }
    public void serializeCity(City city) throws IOException {
        File cityFile = new File("data/towny/" + "cities/" + city.getName() + ".txt");
        if (!cityFile.exists()) {
            cityFile.createNewFile();
        }
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(cityFile)));
        oos.writeObject(city);
        oos.close();
    }
    public Hashtable<City, String> getCities() throws NotExistException {
        Hashtable<City, String> cities = new Hashtable<>();
        try {
            for (File file : citiesFolder.listFiles()) {
                City city = null;
                try {
                    city = getCity(file);
                } catch (NotExistException | TownyException e) {
                    e.printStackTrace();
                }
                cities.put(city, city.getOwner());
            }
        } catch (NullPointerException e) {
            throw new NotExistException();
        }
        return cities;
    }
    public City getCity(String name) throws NotExistException {
        try {
            return getCity(new File("data/towny/cities/" + name + ".txt"));
        } catch (TownyException e) {
            throw new NotExistException();
        }
    }
    public boolean isMemberOfACity(Player p) throws NotExistException {
        for (City city : getCities().keySet()) {
            for (String member : city.getMembersManager().getMembers()) {
                if (member.equals(p.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    public City getCityOfAMember(Player p) throws NotExistException {
        for (City city : getCities().keySet()) {
            if (city.getMembersManager().isMember(p.getName())) {
                return city;
            }
        }
        throw new NotExistException();
    }
    public boolean isAOwner(Player p) {
        try {
            return getOwnersCity(p) != null;
        } catch (NotExistException e) {
            return false;
        }
    }
    public City getOwnersCity(Player p) throws NotExistException {
        Hashtable<City, String> cities = getCities();
        if (cities.values().contains(p.getName())) {
            for (City city : cities.keySet()) {
                if (cities.get(city).equals(p.getName())) {
                    return city;
                }
            }
            throw new NotExistException();
        } else {
            throw new NotExistException();
        }
    }
    public City getCity(File file) throws TownyException, NotExistException {
        try {
            ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            Object obj = oos.readObject();
            oos.close();
            if (obj.getClass().equals(xyz.nyroma.towny.City.class)) {
                return (City) obj;
            } else {
                throw new TownyException();
            }
        } catch (IOException e) {
            throw new NotExistException();
        } catch (ClassNotFoundException e) {
            throw new TownyException();
        }
    }
    public String getClaimer(Location loc) throws NotClaimedException {
        Chunk c = loc.getChunk();
        for (HashMultimap<Integer, Integer> claim : ClaimsManager.citiesClaims.values()) {
            if (claim.containsKey(c.getX())) {
                if (claim.get(c.getX()).contains(c.getZ())) {
                    for (String name : ClaimsManager.citiesClaims.keySet()) {
                        if (ClaimsManager.citiesClaims.get(name).equals(claim)) {
                            return name;
                        }
                    }
                }
            }
        }
        throw new NotClaimedException();
    }
    public void serializeCities(){
        try {
            for(City city : getCities().keySet()){
                try {
                    serializeCity(city);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NotExistException e) {
            System.out.println("Il n'y a aucune ville existante.");
        }
    }
    public void deserializeCities(){
        try {
            for(City city : getCities().keySet()){
                cities.add(city);
                ClaimsManager.citiesClaims.put(city.getName(), city.getClaimsManager().getClaims());
            }
        } catch(NotExistException e){
            System.out.println("Il n'y a aucune ville existante.");
        }
    }
}
