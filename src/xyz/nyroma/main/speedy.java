package xyz.nyroma.main;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class speedy {

    public static void testFileExist(File file) throws FileNotFoundException{
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        }
    }
    public static void testFolderExist(File file){
        if(!file.exists()){
            file.mkdir();
        }
    }
    public static Player getPlayerByName(Server server, String name) throws NullPointerException {
        for(Player p : server.getOnlinePlayers()){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }
    public static ArrayList<String> getAllLines(BufferedReader reader) {
        Hashtable<String, String> aL = new Hashtable<>();
        ArrayList<String> allLines = new ArrayList<>();
        try {
            String line = reader.readLine();
            int counter = 1;
            if (line != null) {
                aL.put(Integer.toString(counter), line);
            }
            while ((line = reader.readLine()) != null) {
                counter++;
                aL.put(Integer.toString(counter), line);
            }
            Enumeration<String> e = aL.elements();
            while (e.hasMoreElements()) {
                allLines.add(e.nextElement());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }
    public static void sendErrorMessage(Player p, String message) {
        p.sendMessage(ChatColor.RED + message);
    }
    public static String getUUID(Player p){
        return p.getUniqueId().toString();
    }
    public static List<String> getFileContent(File file){
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line = reader.readLine();
            while(line != null){
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return lines;
    }
    public static void writeInFile(File file, String txt, boolean erase){
        try {
            testFileExist(file);
            OutputStreamWriter osw;
            if (erase) {
                osw = new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8);
            } else {
                osw = new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8);
            }
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(txt);
            bw.close();
            osw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static float[] getCleanCoos(Location loc){
        return new float[]{(float) loc.getX(), (float) loc.getY(), (float) loc.getZ()};
    }
    public static void spawnFirework(Entity e){
        Firework fw = (Firework) e.getWorld().spawnEntity(e.getLocation(), EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.setPower(5);
        fwm.addEffect(FireworkEffect.builder().trail(true).withColor(Color.GREEN).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
    }
    public static String getDate(String type){
        String date = new GregorianCalendar().getTime().toString();
        switch(type){
            case "hours":
                date = date.substring(11,18);
                break;
            case "days":
                date = date.substring(0,9);
                break;
            case "year":
                date = date.substring(24,27);
                break;
        }
        return date;
    }
}
