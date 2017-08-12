package com.abdul.wadud.costmonitor;

/**
 * Created by Techsoft - 001 on 5/11/2017.
 */

public class List_All {
    private int id;
    private String name;
    private int amount;
    private String date;

    public List_All (int id, String name, int amount, String date){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
