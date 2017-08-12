package com.abdul.wadud.costmonitor;

/**
 * Created by Techsoft-003 on 3/16/2017.
 */

public class TabHistory_Week_List {
    private int id;
    private String name;
    private int amount;
    private String date;
    private String note;

    public TabHistory_Week_List(int id, String name, int amount, String date, String note) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
