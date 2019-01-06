package com.example.welcome.a3;

public class ListItems {

    private int id;
    private String sku;
    private String name;
    private String mrp;

    public ListItems(int id,String sku,String name,String mrp)
    {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.mrp = mrp;
    }

    public int getid() {
        return id;
    }

    public String getsku() {
        return sku;
    }

    public String getname() {
        return name;
    }

    public String getmrp() {
        return mrp;
    }

}
