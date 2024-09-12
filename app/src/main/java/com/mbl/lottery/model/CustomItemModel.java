package com.mbl.lottery.model;

public class CustomItemModel {
    int id;
    String name;
    boolean isActivity;
    public CustomItemModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CustomItemModel(String name, boolean isActivity) {
        this.name = name;
        this.isActivity = isActivity;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public void setActivity(boolean activity) {
        isActivity = activity;
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
