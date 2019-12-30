package fr.sky.claims;

import fr.sky.main.speedy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClaimsManager {
    private File folder = new File("data/claims/");
    private File file = new File("data/claims/claims.txt");
    private Player p;

    public ClaimsManager(Player p){
        this.p = p;
    }

    public boolean add(Location loc){
        List<String> claims = speedy.getFileContent(this.file);
        int nbClaims = 0;
        for(String c : claims){
            if(c.contains(this.p.getName())){
                nbClaims++;
            }
        }
        if(nbClaims < 4 && !speedy.isClaimed(loc) && !loc.getWorld().getName().equals("nether")){
            StringBuilder sb = new StringBuilder();
            sb.append(loc.getChunk().getX()).append(" ").append(loc.getChunk().getZ()).append(" ").append(this.p.getName()).append("\n");
            speedy.writeInFile(file, sb.toString(), false);
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(Location loc){
        List<String> claims = speedy.getFileContent(file);
        try {
            if(speedy.isClaimed(loc)) {
                this.file.delete();
                this.file.createNewFile();
                boolean modified = false;
                for (String c : claims) {
                    if (!(c.contains(Integer.toString(loc.getChunk().getX())) && c.contains(Integer.toString(loc.getChunk().getZ())) && c.contains(this.p.getName()))){
                        speedy.writeInFile(file, c + "\n", false);
                    } else {
                        modified = true;
                    }
                }
                return modified;
            } else {
                return false;
            }
        } catch(IOException e){
            return false;
        }
    }

    public void list(){
        List<String> claims = speedy.getFileContent(file);
        p.sendMessage(ChatColor.RED + "Tu as claims ces chunks :");
        for(String c : claims){
            if(c.contains(this.p.getName())) {
                this.p.sendMessage(ChatColor.RED + c + "\n");
            }
        }
    }
}
