package com.example.Model;

public class Grocery {
    private String name;
    private String quantity;
    private String DateItemAdded;
    private int id;

    public Grocery() {
    }

    public Grocery(String name, String quantity, String dateItemAdded, int id) {
        this.name = name;
        this.quantity = quantity;
        DateItemAdded = dateItemAdded;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateItemAdded() {
        return DateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        DateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
