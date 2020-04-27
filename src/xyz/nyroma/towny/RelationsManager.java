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
        this.enemies.remove(city);
        return this.allies.add(city);
    }

    public boolean getNice(){
        return this.allAllies;
    }

    public boolean getEvil(){
        return this.allEnemies;
    }

    public void setNice(boolean b){
        if(allEnemies && b){
            this.allEnemies = false;
        }
        if(b){
            this.allies.clear();
        }
        this.allAllies = b;
    }

    public void setEvil(boolean b){
        if(allAllies && b){
            this.allAllies = false;
        }
        if(b){
            this.enemies.clear();
        }
        this.allEnemies = b;
    }

    public boolean removeAlly(City city){
        if(allies.contains(city)){
            return this.allies.remove(city);
        } else {
            return false;
        }
    }

    public ArrayList<City> getAllies(){
        return this.allies;
    }

    public boolean addEnemy(City city){
        this.setEvil(false);
        this.allies.remove(city);
        return this.enemies.add(city);
    }

    public boolean removeEnemy(City city){
        if(enemies.contains(city)){
            return this.enemies.remove(city);
        } else {
            return false;
        }
    }

    public ArrayList<City> getEnemies(){
        return this.allies;
    }

}
