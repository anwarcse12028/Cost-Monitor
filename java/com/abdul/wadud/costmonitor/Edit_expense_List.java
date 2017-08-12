package com.abdul.wadud.costmonitor;

/**
 * Created by Techsoft - 001 on 4/22/2017.
 */

public class Edit_expense_List {
    private int id;
    private String name;
    private String amount;
    private String date;
    private String note;

    Edit_expense_List(int id, String name, String amount, String date, String note){
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
