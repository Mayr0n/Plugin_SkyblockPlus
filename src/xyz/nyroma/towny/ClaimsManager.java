package xyz.nyroma.towny;

import com.google.common.collect.HashMultimap;
import org.bukkit.Chunk;
import org.bukkit.Location;
import xyz.nyroma.main.Manager;

import java.io.Serializable;
import java.util.Hashtable;

public class ClaimsManager implements Serializable {
    public static Hashtable<String, HashMultimap> citiesClaims = new Hashtable<>();
    private City city;
    private HashMultimap<Integer, Integer> cityClaims = HashMultimap.create();
    private int max = 5;

    public ClaimsManager(City city){
        if(!citiesClaims.keySet().contains(city.getName())){
            citiesClaims.put(city.getName(), cityClaims);
        }
        this.city = city;
    }

    public boolean addClaim(Location loc){
        if(this.cityClaims.values().size() < this.max){
            Chunk c = loc.getChunk();
            this.cityClaims.put(c.getX(), c.getZ());
            citiesClaims.put(this.city.getName(), cityClaims);
            return true;
        }
        return false;
    }

    public void setMax(int max){
        this.max = max;
    }

    public boolean removeClaim(Location loc){
        Chunk c = loc.getChunk();
        if(this.cityClaims.containsKey(c.getX())){
            for(int z : this.cityClaims.get(c.getX())){
                if(z == c.getZ()){
                    boolean result = this.cityClaims.remove(c.getX(), c.getZ());
                    citiesClaims.put(this.city.getName(), cityClaims);
                    return result;
                }
            }
        }
        return false;
    }

    public HashMultimap<Integer, Integer> getClaims(){
        return this.cityClaims;
    }
}
