package com.example.seznamnakup;

import java.io.Serializable;

public class Zbozi implements Serializable
{

String nazev;
float cena;
float pocet;
float celkem;
boolean koupeno;

    public Zbozi(String nazev, float cena, float pocet) {
        this.nazev = nazev;
        this.cena = cena;
        this.pocet = pocet;
        this.celkem=CalcCelkem();
        this.koupeno=false;
    }

    public float CalcCelkem()
    {
        return this.cena*this.pocet;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
        this.celkem = CalcCelkem();
    }

    public float getPocet() {
        return pocet;
    }

    public void setPocet(float pocet) {
        this.pocet = pocet;
        this.celkem = CalcCelkem();
    }

    public float getCelkem() {return celkem;}

    public void setCelkem(float celkem) {this.celkem = celkem; }

    public boolean isKoupeno() {return koupeno;}

    public void setKoupeno(boolean koupeno) {this.koupeno = koupeno; }

    public void Koupit(){
        this.koupeno=this.koupeno!=true;
    }

    @Override
    public String toString() {
        String k;
        if(koupeno!=true)
            k="nekoupeno";
        else
            k="Koupeno";

        return "nazev = " + nazev + "\n" +
                "cena = " + cena +" Kč\n"+
                "pocet = " + pocet + " ks"/*+"\n" +k*/ ;
    }
}