package xyz.nyroma.Capitalism.jobs;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.nyroma.main.speedy;

import java.io.*;
import java.util.Hashtable;

public class JobManager {

    private static Hashtable<String, Job> jobs = new Hashtable<>();
    private static File file = new File("data/towny/" + "jobs.txt");
    public static void setup(JavaPlugin plugin){
        speedy.testFolderExist(new File("data/towny/"));
        try {
            speedy.testFileExist(file);
        } catch (FileNotFoundException e) {
            System.out.println("ERREUR SETUP JOBMANAGER");
        }

        System.out.println("Chargement des jobs...");

        try {
            jobs = getJobsFromFile();
        } catch (JobException e) {
            jobs = new Hashtable<>();
        }

        System.out.println("Jobs chargés !");

        new BukkitRunnable() {
            @Override
            public void run() {
                JobManager.serializeAll();
            }
        }.runTaskTimer(plugin, 10 * 60 * 20L, 10 * 60 * 20L);
    }
    public static void serializeAll(){
        System.out.println("Enregistrement des jobs...");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            oos.writeObject(jobs);
            oos.close();
            System.out.println("Jobs enregistrés !");
        } catch(IOException e){
            System.out.println("Le fichier des jobs n'est pas créé.");
        }
    }
    private static Hashtable<String, Job> getJobsFromFile() throws JobException {
        try {
            ObjectInputStream oos = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            Object obj = oos.readObject();
            oos.close();
            if (obj.getClass().equals(Hashtable.class)) {
                return (Hashtable<String, Job>) obj;
            } else {
                throw new JobException("Il n'y a pas de jobs enregistrés.");
            }
        } catch (IOException | ClassNotFoundException e) {
            file.delete();
            throw new JobException("Ce fichier ne dirige pas vers une banque existante. Suppression...");
        }
    }
    public static Job getJob(String pseudo) throws JobException {
        if(jobs.containsKey(pseudo)){
            return jobs.get(pseudo);
        } else {
            throw new JobException("Ce joueur n'a pas de métier.");
        }
    }
    public static void setJob(String pseudo, Job job){
        jobs.put(pseudo, job);
    }

}
