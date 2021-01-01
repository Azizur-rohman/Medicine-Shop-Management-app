package com.example.medicineshopmanagement;

public class Update_user {
    int quantity;

    public Update_user(int quantity) {
        this.quantity = quantity;
    }

    public Update_user(String quantity) {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
