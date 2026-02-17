package com.mycompany.controlenotas.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.controlenotas.util.LocalDateAdapter;
import java.net.HttpURLConnection;
import java.net.http.*;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;

public class ApiClient {
    private static final String BASE_URL = "https://controle-notas-api-production.up.railway.app";
    public static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    public static String post(String path, Object body) throws Exception {
        String json = gson.toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400) {
            throw new RuntimeException(response.body());
        }

        return response.body();
    }


    public static String get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400) {
            throw new RuntimeException(response.body());
        }

        return response.body();
    }
    
    public static String delete(String path) throws Exception {
            URL url = new URL(BASE_URL + path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode >= 400) {
                throw new RuntimeException("Erro DELETE: " + responseCode);
            }
            return "";
    }
    
    public static String put(String path, Object body) throws Exception {

        String json = gson.toJson(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400) {
            throw new RuntimeException(response.body());
        }

        return response.body();
    }

    public static Gson getGson() {
        return gson;
    }
}
