package com.example.medicineshopmanagement;

public class User {


    String name;
    String category;
    int Quantity;
    String orginalprice;

    public User() {
    }

    public User(String name, String category, int quantity, String orginalprice) {
        this.name = name;
        this.category = category;
        Quantity = quantity;
        this.orginalprice = orginalprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getOrginalprice() {
        return orginalprice;
    }

    public void setOrginalprice(String orginalprice) {
        this.orginalprice = orginalprice;
    }
}
