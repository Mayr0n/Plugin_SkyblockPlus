package xyz.nyroma.towny;

import com.google.common.collect.HashMultimap;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.io.Serializable;

public class ClaimsManager implements Serializable {
    private HashMultimap<Integer, Integer> cityClaims = HashMultimap.create();
    private int max = 5;

    public ClaimsManager(){

    }

    public boolean addClaim(Location loc){
        if(this.cityClaims.values().size() < this.max){
            Chunk c = loc.getChunk();
            this.cityClaims.put(c.getX(), c.getZ());
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
                    return this.cityClaims.remove(c.getX(), c.getZ());
                }
            }
        }
        return false;
    }

    public HashMultimap<Integer, Integer> getClaims(){
        return this.cityClaims;
    }
}
