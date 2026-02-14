package com.mycompany.controlenotas.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();

    public static Gson get() {
        return gson;
    }
}
