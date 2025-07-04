package br.com.tvtracker.api;

import br.com.tvtracker.model.Serie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class TVMazeAPI {

    private static final String BASE_URL = "https://api.tvmaze.com";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Serie> buscarSeriesPorNome(String nome) {
        List<Serie> series = new ArrayList<>();

        try {
            String url = BASE_URL + "/search/shows?q=" + URI.create(nome).toASCIIString().replace(" ", "%20");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = mapper.readTree(response.body());

                for (JsonNode node : root) {
                    JsonNode show = node.get("show");
                    Serie serie = parseSerie(show);
                    if (serie != null) {
                        series.add(serie);
                    }
                }
            } else {
                System.out.println("Erro na API: " + response.statusCode());
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar séries: " + e.getMessage());
        }

        return series;
    }

    public Serie buscarSeriePorId(int id) {
        try {
            String url = BASE_URL + "/shows/" + id;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode show = mapper.readTree(response.body());
                return parseSerie(show);
            } else {
                System.out.println("Série não encontrada (ID " + id + ")");
            }

        } catch (Exception e) {
            System.out.println("Erro ao buscar série por ID: " + e.getMessage());
        }

        return null;
    }

    private Serie parseSerie(JsonNode show) {
        try {
            int id = show.get("id").asInt();
            String nome = show.get("name").asText();
            String idioma = show.has("language") && !show.get("language").isNull() ? show.get("language").asText() : "Desconhecido";
            List<String> generos = new ArrayList<>();
            show.get("genres").forEach(g -> generos.add(g.asText()));

            double nota = show.has("rating") && show.get("rating").has("average") && !show.get("rating").get("average").isNull()
                    ? show.get("rating").get("average").asDouble() : 0.0;

            String status = show.has("status") ? show.get("status").asText() : "Desconhecido";

            String estreia = show.has("premiered") && !show.get("premiered").isNull()
                    ? show.get("premiered").asText() : "Indefinida";

            String fim = show.has("ended") && !show.get("ended").isNull()
                    ? show.get("ended").asText() : null;

            String emissora = show.has("network") && show.get("network") != null && show.get("network").has("name")
                    ? show.get("network").get("name").asText()
                    : (show.has("webChannel") && show.get("webChannel") != null && show.get("webChannel").has("name")
                        ? show.get("webChannel").get("name").asText()
                        : "Desconhecida");

            return new Serie(id, nome, idioma, generos, nota, status, estreia, fim, emissora);

        } catch (Exception e) {
            System.out.println("Erro ao interpretar série: " + e.getMessage());
            return null;
        }
    }
}