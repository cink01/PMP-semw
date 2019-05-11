package com.example.seznamnakup;

import java.util.ArrayList;
import java.util.List;

public class Singleton {
    final List<Zbozi> zbozis = new ArrayList<>();
    private static Singleton instance;
    private Singleton() {}
    static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
