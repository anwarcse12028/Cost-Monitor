package com.abdul.wadud.costmonitor;

/**
 * Created by Techsoft-003 on 3/28/2017.
 */

public class ListView_List {
    private int id;
    private String name;

    public ListView_List(int id, String name){
        this.name = name;
        this.id = id;
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
}
