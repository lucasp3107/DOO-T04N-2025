package com.minhas_series_tv.service; // Pacote atualizado

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets; // Pacote atualizado
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.minhas_series_tv.model.Serie;

public class TVMazeApiService {
    private static final String BASE_URL = "https://api.tvmaze.com";
    private Gson gson;

    public TVMazeApiService() {
        this.gson = new Gson();
    }

    public List<Serie> buscarSeriesPorNome(String query) throws Exception {
        List<Serie> seriesEncontradas = new ArrayList<>();
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
        String urlString = BASE_URL + "/search/shows?q=" + encodedQuery;

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "MinhasSeriesTvApp/1.0"); // User-Agent atualizado

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JsonArray jsonArray = JsonParser.parseString(response.toString()).getAsJsonArray();

                for (JsonElement element : jsonArray) {
                    JsonObject showObject = element.getAsJsonObject().getAsJsonObject("show");
                    if (showObject != null) {
                        Serie serie = gson.fromJson(showObject, Serie.class);
                        seriesEncontradas.add(serie);
                    }
                }
            } else {
                String errorMsg = "Erro ao buscar séries. Código de resposta HTTP: " + responseCode;
                System.err.println(errorMsg);
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    if (!errorResponse.isEmpty()) {
                        System.err.println("Mensagem da API: " + errorResponse.toString());
                    }
                } catch (Exception e) { /* Ignorar */ }
                throw new Exception(errorMsg);
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) { /* Ignorar */ }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return seriesEncontradas;
    }

    public Serie buscarSeriePorId(int id) throws Exception {
        String urlString = BASE_URL + "/shows/" + id;

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "MinhasSeriesTvApp/1.0"); // User-Agent atualizado

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                return gson.fromJson(response.toString(), Serie.class);
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("Série com ID " + id + " não encontrada na API.");
                return null;
            } else {
                String errorMsg = "Erro ao buscar série por ID " + id + ". Código de resposta HTTP: " + responseCode;
                System.err.println(errorMsg);
                 try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorResponse.append(errorLine);
                    }
                    if (!errorResponse.isEmpty()) {
                        System.err.println("Mensagem da API: " + errorResponse.toString());
                    }
                } catch (Exception e) { /* Ignorar */ }
                throw new Exception(errorMsg);
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) { /* Ignorar */ }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}