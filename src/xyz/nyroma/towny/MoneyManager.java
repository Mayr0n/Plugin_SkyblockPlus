package xyz.nyroma.towny;

import java.io.Serializable;

public class MoneyManager implements Serializable {
    private double amount = 500;
    private int taxes = 0;

    public void setTaxes(int taxes){
        this.taxes = taxes;
    }

    public int getTaxes(){
        return this.taxes;
    }

    public void removeMoney(int amount){
        this.amount -= amount;
    }

    public void addMoney(int amount){
        this.amount += amount;
    }

    public double getAmount(){
        return this.amount;
    }
}
