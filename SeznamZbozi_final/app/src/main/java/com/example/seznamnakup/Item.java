package com.example.seznamnakup;

public class Item {

    int id;
    String nazev;

    public Item() {
        this.id = -1;
        this.nazev = "nazev";
    }

    public Item(String nazev) {
        this.id = -1;
        this.nazev = nazev;
    }

    public Item(int id, String nazev) {
        this.id = id;
        this.nazev = nazev;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String toString() {
        return nazev;
    }
}
