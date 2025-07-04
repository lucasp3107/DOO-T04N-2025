package service;

import com.google.gson.*;
import model.Serie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TvMazeService {

    private static final String API_URL = "https://api.tvmaze.com/search/shows?q=";

    public List<Serie> buscarSeries(String nomeSerie) {
        List<Serie> series = new ArrayList<>();
        try {
            String urlString = API_URL + URLEncoder.encode(nomeSerie, StandardCharsets.UTF_8);
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                throw new IOException("Falha na conexão com a API: " + connection.getResponseCode());
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                JsonArray jsonResponse = JsonParser.parseReader(reader).getAsJsonArray();

                for (JsonElement item : jsonResponse) {
                    JsonObject showObject = item.getAsJsonObject().getAsJsonObject("show");

                    int id = showObject.get("id").getAsInt();
                    String nome = showObject.get("name").getAsString();
                    String idioma = showObject.has("language") ? showObject.get("language").getAsString() : "N/A";

                    List<String> generos = new ArrayList<>();
                    if (showObject.has("genres")) {
                        showObject.getAsJsonArray("genres").forEach(g -> generos.add(g.getAsString()));
                    }

                    Double nota = null;
                    if (showObject.has("rating") && !showObject.get("rating").isJsonNull() &&
                        showObject.getAsJsonObject("rating").has("average") && !showObject.getAsJsonObject("rating").get("average").isJsonNull()) {
                        nota = showObject.getAsJsonObject("rating").get("average").getAsDouble();
                    }

                    String status = showObject.has("status") ? showObject.get("status").getAsString() : "N/A";
                    
                    LocalDate dataEstreia = null;
                    if (showObject.has("premiered") && !showObject.get("premiered").isJsonNull()) {
                        try { dataEstreia = LocalDate.parse(showObject.get("premiered").getAsString()); } catch (DateTimeParseException ignored) {}
                    }
                    
                    LocalDate dataTermino = null;
                    if (showObject.has("ended") && !showObject.get("ended").isJsonNull()) {
                        try { dataTermino = LocalDate.parse(showObject.get("ended").getAsString()); } catch (DateTimeParseException ignored) {}
                    }
                    
                    String emissora = "N/A";
                    if (showObject.has("network") && !showObject.get("network").isJsonNull()) {
                        emissora = showObject.getAsJsonObject("network").get("name").getAsString();
                    } else if (showObject.has("webChannel") && !showObject.get("webChannel").isJsonNull()) {
                        emissora = showObject.getAsJsonObject("webChannel").get("name").getAsString();
                    }
                    
                    series.add(new Serie(id, nome, idioma, generos, nota, status, dataEstreia, dataTermino, emissora));
                }
            }
            connection.disconnect();

        } catch (IOException e) {
            throw new RuntimeException("Erro de comunicação: " + e.getMessage(), e);
        } catch (JsonParseException e) {
            throw new RuntimeException("Erro ao processar resposta da API.", e);
        }
        return series;
    }
}
