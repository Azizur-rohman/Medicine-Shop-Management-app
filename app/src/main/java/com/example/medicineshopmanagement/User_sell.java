package com.example.medicineshopmanagement;

public class User_sell {

    String name;
    int sellingquantity;
    int sellingTotalprice;

    public User_sell() {
    }

    public User_sell(String name, int sellingquantity, int sellingTotalprice) {
        this.name = name;
        this.sellingquantity = sellingquantity;
        this.sellingTotalprice = sellingTotalprice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellingquantity() {
        return sellingquantity;
    }

    public void setSellingquantity(int sellingquantity) {
        this.sellingquantity = sellingquantity;
    }

    public int getSellingTotalprice() {
        return sellingTotalprice;
    }

    public void setSellingTotalprice(int sellingTotalprice) {
        this.sellingTotalprice = sellingTotalprice;
    }
}
