package xyz.nyroma.towny;

import org.bukkit.*;
import org.bukkit.entity.Player;
import xyz.nyroma.main.NotFoundException;
import xyz.nyroma.main.speedy;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class CityManager {

    public CityManager() {

    }

    public void applyTaxes(Server server){
        Bukkit.broadcastMessage(ChatColor.GREEN + "Lancement de la tournée des taxes !");
        List<City> cities = CitiesCache.getCities();
        for(City city : cities){
            float taxes = city.getMoneyManager().getTaxes();
            if(city.getMoneyManager().getAmount() >= taxes){
                city.getMoneyManager().removeMoney(taxes);
                city.setFaillite(false);
                for(String pseudo : city.getMembersManager().getMembers()){
                    try {
                        speedy.getPlayerByName(server, pseudo).sendMessage(ChatColor.DARK_GREEN + String.valueOf(taxes) + " Nyr ont été débités de la ville.");
                    } catch (NotFoundException ignored) {
                    }
                }
                System.out.println(city.getName() + " a été débité de " + taxes + " Nyr.");
            } else {
                if(city.getFaillite()){
                    removeCity(city);
                    for (String pseudo : city.getMembersManager().getMembers()) {
                        try {
                            speedy.getPlayerByName(server, pseudo).sendMessage(
                                    ChatColor.DARK_RED + "Votre ville est restée 12h en faillite. Elle a été supprimée.");
                        } catch (NotFoundException ignored) {
                        }
                    }
                    System.out.println(city.getName() + " a été supprimée.");
                } else {
                    city.setFaillite(true);
                    for (String pseudo : city.getMembersManager().getMembers()) {
                        try {
                            speedy.getPlayerByName(server, pseudo).sendMessage(
                                    ChatColor.DARK_RED + "Votre ville est passée en état de faillite. Si les taxes ne sont pas payées dans 12h, elle sera détruite.");
                        } catch (NotFoundException ignored) {
                        }
                    }
                    System.out.println(city.getName() + " est passée en faillite.");
                }
            }
        }
        Bukkit.broadcastMessage(ChatColor.GREEN + "Fin de la tournée des taxes !");
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
        File cityFile = new File("data/towny/" + "cities/" + city.getID() + ".json");
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

    public Optional<City> getCityOfMember(Player p) {
        for (City city : CitiesCache.getCities()) {
            if (city.getMembersManager().isMember(p.getName())) {
                return Optional.of(city);
            }
        }
        return Optional.empty();
    }


    public boolean isAOwner(Player p) {
        return getOwnersCity(p).isPresent();
    }
    public Optional<City> getOwnersCity(Player p) {
        List<City> cities = CitiesCache.getCities();
        for(City city : cities){
            if(city.getOwner().equals(p.getName())){
                return Optional.of(city);
            }
        }
        return Optional.empty();
    }

    public Optional<City> getClaimer(Location loc) {
        for(City city : CitiesCache.getCities()){
            if(city.getClaimsManager().contains(loc)){
                return Optional.of(city);
            }
        }
        return Optional.empty();
    }
}
