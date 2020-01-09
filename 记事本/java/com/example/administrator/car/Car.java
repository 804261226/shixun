package com.example.administrator.car;

public class Car {
    int id;
    String title;
    String price;
    int icon;
    public Car(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car(int id, String title, String price, int icon) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.icon = icon;
    }

    public Car(String title, String price, int icon) {
        this.title = title;
        this.price = price;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
