package xyz.nyroma.towny;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RelationsManager implements Serializable {
    private ArrayList<City> allies = new ArrayList<>();
    private ArrayList<City> enemies = new ArrayList<>();
    private boolean allAllies = false;
    private boolean allEnemies = false;


    public boolean addAlly(City city){
        this.setNice(false);
        this.setEvil(false);
        getEnemies().remove(city);
        return getAllies().add(city);
    }

    public boolean getNice(){
        return this.allAllies;
    }

    public boolean getEvil(){
        return this.allEnemies;
    }

    public void setNice(boolean b){
        if(b){
            getEnemies().clear();
            getAllies().clear();
            this.allEnemies = false;
        }
        this.allAllies = b;
    }

    public void setEvil(boolean b){
        if(b){
            getEnemies().clear();
            getAllies().clear();
            this.allAllies = false;
        }
        this.allEnemies = b;
    }

    public boolean removeAlly(City city){
        if(getAllies().contains(city)){
            return getAllies().remove(city);
        } else {
            return false;
        }
    }

    public ArrayList<City> getAllies(){
        return this.allies;
    }

    public boolean addEnemy(City city){
        this.setNice(false);
        this.setEvil(false);
        getAllies().remove(city);
        return getEnemies().add(city);
    }

    public boolean removeEnemy(City city){
        if(getEnemies().contains(city)){
            return getEnemies().remove(city);
        } else {
            return false;
        }
    }

    public ArrayList<City> getEnemies(){
        return this.enemies;
    }

}
