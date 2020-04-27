package xyz.nyroma.towny;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.List;

public class CityManager {

    public CityManager() {

    }

    public boolean isAlreadyOwner(String name) {
        for(City city : CitiesCache.getCities()){
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
        CitiesCache.remove(city);
    }

    public boolean isMemberOfACity(Player p) {
        for (City city : CitiesCache.getCities()) {
            for (String member : city.getMembersManager().getMembers()) {
                if (member.equals(p.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    public City getCityOfMember(Player p) throws TownyException {
        for (City city : CitiesCache.getCities()) {
            if (city.getMembersManager().isMember(p.getName())) {
                return city;
            }
        }
        throw new TownyException("Tu n'appartiens à aucune ville !");
    }
    public boolean isAOwner(Player p) {
        try {
            return getOwnersCity(p) != null;
        } catch (TownyException e) {
            return false;
        }
    }
    public City getOwnersCity(Player p) throws TownyException {
        List<City> cities = CitiesCache.getCities();
        for(City city : cities){
            if(city.getOwner().equals(p.getName())){
                return city;
            }
        }
        throw new TownyException("Il n'existe aucune ville sur le serveur !");
    }

    public City getClaimer(Location loc) throws TownyException {
        Chunk c = loc.getChunk();
        for(City city : CitiesCache.getCities()){
            if(city.getClaimsManager().getClaims().containsKey(c.getX())){
                if(city.getClaimsManager().getClaims().get(c.getX()).contains(c.getZ())){
                    return city;
                }
            }
        }
        throw new TownyException("Ce territoire n'est pas claim.");
    }
}
