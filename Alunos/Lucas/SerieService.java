package com.serie;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class SerieService {
    private static final String API_URL = "https://api.tvmaze.com/search/shows?q=";

    public List<Serie> buscarSeriePorNome(String nome) {
        List<Serie> series = new ArrayList<>();

        try {
            String url = API_URL + URLEncoder.encode(nome, StandardCharsets.UTF_8);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create(url))
                                             .GET()
                                             .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Erro ao acessar a API. Código de status: " + response.statusCode());
                return series;
            }

            JsonArray resultados = JsonParser.parseString(response.body()).getAsJsonArray();

            for (JsonElement elemento : resultados) {
                JsonObject show = elemento.getAsJsonObject().getAsJsonObject("show");
                Serie serie = new Serie(show);
                series.add(serie);
            }

        } catch (java.net.UnknownHostException e) {
            System.out.println("Erro de conexão com a internet. Verifique sua rede.");
        } catch (IllegalArgumentException e) {
            System.out.println("Nome inválido para busca: " + nome);
        } catch (Exception e) {
            System.out.println("Erro inesperado ao buscar séries: " + e.getMessage());
        }

        return series;
    }
}
