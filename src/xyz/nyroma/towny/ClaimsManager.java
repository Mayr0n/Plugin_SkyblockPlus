package xyz.nyroma.towny;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class ClaimsManager implements Serializable {
    private Hashtable<String, Hashtable<Integer, ArrayList<Integer>>> claims = new Hashtable<>(); //{world, {x=[z1,z2]}}
    private int max = 5;

    public ClaimsManager(){

    }

    public int getAmount(){
        int amount = 0;
        for(String world : claims.keySet()){
            for(ArrayList<Integer> y : this.claims.get(world).values()){
                amount+= y.size();
            }
        }
        System.out.println("Nombre de claims : " + amount);
        return amount;
    }

    public int getMax() {
        return max;
    }

    public boolean addClaim(Location loc) {
        if(getAmount() < this.getMax()){
            Chunk c = loc.getChunk();
            String world = c.getWorld().getName();
            int X = c.getX();
            int Z = c.getZ();
            if(contains(loc)){
                return false;
            } else {
                ArrayList<Integer> z = new ArrayList<>();
                z.add(Z);
                if(getClaims().containsKey(world)){
                    if(getClaims().get(world).containsKey(X)){
                        return getClaims().get(world).get(X).add(Z);
                    } else {
                        getClaims().get(world).put(X, z);
                        return true;
                    }
                } else {
                    Hashtable<Integer, ArrayList<Integer>> hash = new Hashtable<>();
                    hash.put(X, z);
                    getClaims().put(world, hash);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(Location loc){
        Chunk c = loc.getChunk();
        String world = c.getWorld().getName();
        int X = c.getX();
        int Z = c.getZ();
        if(getClaims().keySet().contains(world)){
            if(getClaims().get(world).keySet().contains(c.getX())){
                return getClaims().get(world).get(X).contains(Z);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void setMax(int max){
        this.max = max;
    }

    public boolean removeClaim(Location loc){
        Chunk c = loc.getChunk();
        String world = c.getWorld().getName();
        int X = c.getX();
        int Z = c.getZ();
        if(getClaims().keySet().contains(world)){
            if(getClaims().get(world).keySet().contains(X)){
                if(getClaims().get(world).get(X).contains(Z)){
                    return getClaims().get(world).get(X).remove(Integer.valueOf(Z));
                }
            }
        }
        return false;
    }

    public Hashtable<String, Hashtable<Integer, ArrayList<Integer>>> getClaims(){
        return this.claims;
    }
}
